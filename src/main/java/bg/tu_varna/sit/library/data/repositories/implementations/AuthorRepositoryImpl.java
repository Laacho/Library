package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.common.annotations.Singleton;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.data.repositories.interfaces.AuthorRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Singleton
public class AuthorRepositoryImpl implements AuthorRepository {
    private static final Logger log = Logger.getLogger(AuthorRepositoryImpl.class);
    private AuthorRepositoryImpl(){};
    @Override
    public Long save(Author entity) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long result = null;
        try {
            result = (Long) session.save(entity);
            transaction.commit();
            log.info("Author saved successfully");
        } catch (Exception ex){
            log.error("Error while saving author ", ex);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public void saveAll(List<Author> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (Author entity : entities) {
                session.save(entity);
            }
            transaction.commit();
            log.info("Authors saved successfully");
        } catch (Exception ex) {
            log.error("Error while saving authors ", ex);
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Author> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Author> result = null;
        try {
            String jpql = "SELECT a FROM Author a WHERE a.id = :id";
            result = Optional.ofNullable(session.createQuery(jpql, Author.class)
                    .setParameter("id", id)
                    .getSingleResult());
            transaction.commit();
            // log.info("Get all list");
        } catch (Exception ex) {
            log.error("Error while fetching author ", ex);
        } finally {
            session.close();
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
            transaction.commit();
            log.info("Authors found");
        } catch (Exception ex) {
            log.error("Error while fetching authors ", ex);
        } finally {
            session.close();
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
            transaction.commit();
            log.info("Author deleted successfully");
        } catch (Exception ex) {
            log.error("Error while deleting author ", ex);
        } finally {
            session.close();
        }
        return result;
    }
}
