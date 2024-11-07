package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.data.repositories.interfaces.AuthorRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorRepositoryImpl implements AuthorRepository {
    @Override
    public void save(Author entity) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(entity);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            transaction.commit();
            Connection.closeSession();
        }
    }

    @Override
    public void saveAll(List<Author> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (Author entity : entities) {
                session.save(entity);
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            transaction.commit();
            Connection.closeSession();
        }
    }

    @Override
    public Optional<Author> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Author> result = null;
        try {
            String jpql = "SELECT a FROM Author a WHERE a.id = ?" + id;
            result = Optional.ofNullable(session.createQuery(jpql, Author.class).getSingleResult());
            // log.info("Get all list");
        } catch (Exception ex) {
            // log.error("Get Task error: " + ex.getMessage());
        } finally {
            transaction.commit();
            Connection.closeSession();
        }
        return result;
    }

    @Override
    public Optional<List<Author>> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Author> list = new ArrayList<>();
        try {
            String jpql = "SELECT a FROM Author a";
            list.addAll(session.createQuery(jpql, Author.class).getResultList());
            // log.info("Get all list");
        } catch (Exception ex) {
            // log.error("Get Task error: " + ex.getMessage());
        } finally {
            transaction.commit();
            Connection.closeSession();
        }
        return Optional.of(list);
    }

    @Override
    public Optional<Author> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Author> result = null;
        try {
            result = findById(id);
            if (result.isPresent())
                session.delete(result.get());
            else {
                //todo replace with better exception
                throw new RuntimeException();
            }
            // log.info("Get all list");
        } catch (Exception ex) {
            // log.error("Get Task error: " + ex.getMessage());
        } finally {
            transaction.commit();
            Connection.closeSession();
        }
        return result;
    }
}
