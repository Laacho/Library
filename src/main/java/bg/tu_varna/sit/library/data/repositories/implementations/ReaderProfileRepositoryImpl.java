package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.ReaderProfile;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReaderProfileRepositoryImpl implements ReaderProfileRepository {
    private static final Logger log = Logger.getLogger(ReaderProfileRepositoryImpl.class);
    @Override
    public Long save(ReaderProfile entity) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long result = null;
        try {
            result = (Long) session.save(entity);
            transaction.commit();
            log.info("Successfully saved entity: " + entity);
        }catch (Exception ex){
            log.error("Error while saving entity" + entity,ex);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public void saveAll(List<ReaderProfile> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (ReaderProfile entity : entities) {
                session.save(entity);
            }
            transaction.commit();
            log.info("Successfully saved entities: " + entities);
        }catch (Exception ex){
            log.error("Error while saving entities" + entities);
        }finally {
            session.close();
        }
    }

    @Override
    public Optional<ReaderProfile> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<ReaderProfile> result = null;
        try {
            String jpql = "SELECT r FROM ReaderProfile r WHERE r.id = :id";
            result = Optional.ofNullable(session.createQuery(jpql, ReaderProfile.class)
                    .setParameter("id", id)
                    .getSingleResult());
           transaction.commit();
            log.info("Successfully find entity by id: " + id);
        } catch (Exception ex) {
            log.error("Error while fetching entity by id" + id,ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<List<ReaderProfile>> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<ReaderProfile> list = new ArrayList<>();
        try {
            String jpql = "SELECT r FROM ReaderProfile r";
            list.addAll(session.createQuery(jpql, ReaderProfile.class).getResultList());
            transaction.commit();
            log.info("Successfully find entities: " + list);
        } catch (Exception ex) {
            log.error("Error while fetching entities" + list);
        } finally {
            session.close();
        }
        return Optional.of(list);
    }

    @Override
    public Optional<ReaderProfile> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<ReaderProfile> result = null;
        try {
            result = findById(id);
            if (result.isPresent())
                session.delete(result.get());
            else {
                //todo replace with better exception
                throw new RuntimeException();
            }
            transaction.commit();
            log.info("Successfully deleted entity by id: " + id);
        } catch (Exception ex) {
            log.error("Error while deleting entity by id" + id,ex);
        } finally {
            session.close();
        }
        return result;
    }
}
