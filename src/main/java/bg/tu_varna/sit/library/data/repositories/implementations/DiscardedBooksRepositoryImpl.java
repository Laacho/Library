package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Archived;
import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.data.entities.DiscardedBooks;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.interfaces.DiscardedBooksRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DiscardedBooksRepositoryImpl implements DiscardedBooksRepository {
    @Override
    public void save(DiscardedBooks entity) {
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
    public void saveAll(List<DiscardedBooks> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (DiscardedBooks entity : entities) {
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
    public Optional<DiscardedBooks> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<DiscardedBooks> result = null;
        try {
            String jpql = "SELECT d FROM DiscardedBooks d WHERE a.id = ?" + id;
            result = Optional.ofNullable(session.createQuery(jpql, DiscardedBooks.class).getSingleResult());
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
    public Optional<List<DiscardedBooks>> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<DiscardedBooks> list = new ArrayList<>();
        try {
            String jpql = "SELECT d FROM DiscardedBooks d";
            list.addAll(session.createQuery(jpql, DiscardedBooks.class).getResultList());
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
    public Optional<DiscardedBooks> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<DiscardedBooks> result = null;
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
