package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.exceptions.UserWithIdDoesNotExist;
import bg.tu_varna.sit.library.utils.annotations.Singleton;
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
public class
AuthorRepositoryImpl implements AuthorRepository {
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
        Optional<Author> result = Optional.empty();
        try {
            String jpql = "SELECT a FROM Author a WHERE a.id = :id";
            result = Optional.of(session.createQuery(jpql, Author.class)
                    .setParameter("id", id)
                    .getSingleResult());
            transaction.commit();
             log.info("Get all list");
        } catch (Exception ex) {
            log.error("Error while fetching author ", ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Author> findAll() {
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
        return list;
    }

    @Override
    public Optional<Author> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Author> result = Optional.empty();
        try {
            result = findById(id);
            if (result.isPresent())
                session.delete(result.get());
            else {
                throw new UserWithIdDoesNotExist("Author Not Found!","Author with id " + id + " does not exist");
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
