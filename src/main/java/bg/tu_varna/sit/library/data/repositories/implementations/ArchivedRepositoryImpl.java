package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.common.annotations.Singleton;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Archived;
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
    public Long save(Archived entity) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long result = null;
        try {
            result = (Long) session.save(entity);
            transaction.commit();
            log.info("Successfully saved " + entity);
        } catch (Exception ex) {
           log.error("Archive add error: "+ex.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void saveAll(List<Archived> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (Archived entity : entities) {
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
    public Optional<Archived> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Archived> result = null;
        try {
            String jpql = "SELECT a FROM Archived a WHERE a.id = :id";
            result = Optional.ofNullable(session.createQuery(jpql, Archived.class)
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
    public Optional<List<Archived>> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Archived> list = new ArrayList<>();
        try {
            String jpql = "SELECT a FROM Archived a";
            list.addAll(session.createQuery(jpql, Archived.class).getResultList());
            transaction.commit();
            log.info("Successfully retrieved information");
        } catch (Exception ex) {
             log.error("Error: " + ex.getMessage());
        } finally {
            session.close();
        }
        return Optional.of(list);
    }

    @Override
    public Optional<Archived> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Archived> archived = null;
        try {
            archived = findById(id);
            if (archived.isPresent())
                session.delete(archived.get());
            else {
                //todo replace with better exception
                throw new RuntimeException();
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
