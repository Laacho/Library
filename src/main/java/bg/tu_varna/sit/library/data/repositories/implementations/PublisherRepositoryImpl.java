package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Publisher;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.repositories.interfaces.PublisherRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublisherRepositoryImpl implements PublisherRepository {
    @Override
    public Long save(Publisher entity) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long result = null;
        try {
            result = (Long) session.save(entity);
            transaction.commit();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public void saveAll(List<Publisher> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (Publisher entity : entities) {
                session.save(entity);
            }
            transaction.commit();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            session.close();
        }
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Publisher> result = null;
        try {
            String jpql = "SELECT p FROM Publisher p WHERE p.id = :id";
            result = Optional.ofNullable(session.createQuery(jpql, Publisher.class)
                    .setParameter("id", id)
                    .getSingleResult());
            transaction.commit();
            // log.info("Get all list");
        } catch (Exception ex) {
            // log.error("Get Task error: " + ex.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<List<Publisher>> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Publisher> list = new ArrayList<>();
        try {
            String jpql = "SELECT p FROM Publisher p";
            list.addAll(session.createQuery(jpql, Publisher.class).getResultList());
            transaction.commit();
            // log.info("Get all list");
        } catch (Exception ex) {
            // log.error("Get Task error: " + ex.getMessage());
        } finally {
            session.close();
        }
        return Optional.of(list);
    }

    @Override
    public Optional<Publisher> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Publisher> result = null;
        try {
            result = findById(id);
            if (result.isPresent())
                session.delete(result.get());
            else {
                //todo replace with better exception
                throw new RuntimeException();
            }
            transaction.commit();
            // log.info("Get all list");
        } catch (Exception ex) {
            // log.error("Get Task error: " + ex.getMessage());
        } finally {
            session.close();
        }
        return result;
    }
}
