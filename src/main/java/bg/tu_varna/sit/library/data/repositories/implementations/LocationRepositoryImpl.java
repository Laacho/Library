package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Location;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.repositories.interfaces.LocationRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationRepositoryImpl implements LocationRepository {
    @Override
    public Long save(Location entity) {
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
    public void saveAll(List<Location> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (Location entity : entities) {
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
    public Optional<Location> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Location> result = null;
        try {
            String jpql = "SELECT l FROM Location l WHERE l.id = :id";
            result = Optional.ofNullable(session.createQuery(jpql, Location.class)
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
    public Optional<List<Location>> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Location> list = new ArrayList<>();
        try {
            String jpql = "SELECT l FROM Location l";
            list.addAll(session.createQuery(jpql, Location.class).getResultList());
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
    public Optional<Location> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Location> result = null;
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