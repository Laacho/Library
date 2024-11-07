package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.ReaderProfile;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReaderProfileRepositoryImpl implements ReaderProfileRepository {
    @Override
    public void save(ReaderProfile entity) {
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
    public void saveAll(List<ReaderProfile> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (ReaderProfile entity : entities) {
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
    public Optional<ReaderProfile> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<ReaderProfile> result = null;
        try {
            String jpql = "SELECT r FROM ReaderProfile r WHERE a.id = ?" + id;
            result = Optional.ofNullable(session.createQuery(jpql, ReaderProfile.class).getSingleResult());
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
    public Optional<List<ReaderProfile>> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<ReaderProfile> list = new ArrayList<>();
        try {
            String jpql = "SELECT r FROM ReaderProfile r";
            list.addAll(session.createQuery(jpql, ReaderProfile.class).getResultList());
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
