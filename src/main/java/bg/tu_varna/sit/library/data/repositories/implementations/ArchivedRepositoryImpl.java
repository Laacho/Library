package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.exceptions.UserWithIdDoesNotExist;
import bg.tu_varna.sit.library.utils.annotations.Singleton;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.ArchivedBooks;
import bg.tu_varna.sit.library.data.repositories.interfaces.ArchivedRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Singleton
public class ArchivedRepositoryImpl implements ArchivedRepository {
    private static final Logger log= Logger.getLogger(ArchivedRepositoryImpl.class);
    private ArchivedRepositoryImpl(){};

    @Override
    public Long save(ArchivedBooks entity) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long result = null;
        try {
            ArchivedBooks merge = session.merge(entity);
            result = merge.getId();
            log.info("Successfully saved " + entity);
        } catch (Exception ex) {
           log.error("Archive add error: "+ex.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void saveAll(List<ArchivedBooks> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (ArchivedBooks entity : entities) {
                session.save(entity);
            }
            transaction.commit();
            log.info("Successfully saved all entities");
        } catch (Exception ex) {
            log.error("Archive add error: "+ex.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<ArchivedBooks> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<ArchivedBooks> result = Optional.empty();
        try {
            String jpql = "SELECT a FROM ArchivedBooks a WHERE a.id = :id";
            result = Optional.of(session.createQuery(jpql, ArchivedBooks.class)
                    .setParameter("id", id)
                    .getSingleResult());
            transaction.commit();
            log.info("Get all list");
        } catch (Exception ex) {
            log.error("Get Task error: " + ex.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<ArchivedBooks> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<ArchivedBooks> list = new ArrayList<>();
        try {
            String jpql = "SELECT a FROM ArchivedBooks a";
            list.addAll(session.createQuery(jpql, ArchivedBooks.class).getResultList());
            transaction.commit();
            log.info("Successfully retrieved information");
        } catch (Exception ex) {
             log.error("Error: " + ex.getMessage());
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public Optional<ArchivedBooks> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<ArchivedBooks> archived = Optional.empty();
        try {
            archived = findById(id);
            if (archived.isPresent())
                session.delete(archived.get());
            else {
                throw new UserWithIdDoesNotExist("Archive Books Not Found!","Archive Books with id " + id + " does not exist");
            }
            transaction.commit();
            log.info("Deleted successfully");
        } catch (Exception ex) {
            log.error("Deleting error: " + ex.getMessage());
        } finally {
            session.close();
        }
        return archived;
    }
}
