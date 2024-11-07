package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Archived;
import bg.tu_varna.sit.library.data.repositories.interfaces.ArchivedRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArchivedRepositoryImpl implements ArchivedRepository {
    @Override
    public void save(Archived entity) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(entity);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            transaction.commit();
            Connection.closeSession();
        }
    }

    @Override
    public void saveAll(List<Archived> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (Archived entity : entities) {
                session.save(entity);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            transaction.commit();
            Connection.closeSession();
        }
    }

    @Override
    public Optional<Archived> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Archived> result = null;
        try {
            String jpql = "SELECT a FROM Archived a WHERE a.id = ?" + id;
            result = Optional.ofNullable(session.createQuery(jpql, Archived.class).getSingleResult());
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
    public Optional<List<Archived>> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Archived> list = new ArrayList<>();
        try {
            String jpql = "SELECT a FROM Archived a";
            list.addAll(session.createQuery(jpql, Archived.class).getResultList());
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
            // log.info("Get all list");
        } catch (Exception ex) {
            // log.error("Get Task error: " + ex.getMessage());
        } finally {
            transaction.commit();
            Connection.closeSession();
        }
        return archived;
    }
}
