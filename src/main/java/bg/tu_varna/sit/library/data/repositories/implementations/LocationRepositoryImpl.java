package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Location;
import bg.tu_varna.sit.library.data.repositories.interfaces.LocationRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationRepositoryImpl implements LocationRepository {
    @Override
    public void save(Location entity) {
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
    public void saveAll(List<Location> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (Location entity : entities) {
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
    public Optional<Location> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Location> result = null;
        try {
            String jpql = "SELECT l FROM Location l WHERE a.id = ?" + id;
            result = Optional.ofNullable(session.createQuery(jpql, Location.class).getSingleResult());
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
    public Optional<List<Location>> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Location> list = new ArrayList<>();
        try {
            String jpql = "SELECT l FROM Location l";
            list.addAll(session.createQuery(jpql, Location.class).getResultList());
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
