package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.utils.annotations.Singleton;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Location;
import bg.tu_varna.sit.library.data.repositories.interfaces.LocationRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Singleton
public class LocationRepositoryImpl implements LocationRepository {
    private static final Logger log=Logger.getLogger(LocationRepositoryImpl.class);
    private LocationRepositoryImpl(){};
    @Override
    public Long save(Location entity) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long result = null;
        try {
            result = (Long) session.save(entity);
            transaction.commit();
            log.info("Successfully added entity");
        }catch (Exception ex){
            log.error("Error while saving entity", ex);
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
            log.info("Successfully saved entities");
        }catch (Exception ex){
            log.error("Error while saving entities", ex);
        }finally {
            session.close();
        }
    }

    @Override
    public Optional<Location> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Location> result = Optional.empty();
        try {
            String jpql = "SELECT l FROM Location l WHERE l.id = :id";
            result = Optional.of(session.createQuery(jpql, Location.class)
                    .setParameter("id", id)
                    .getSingleResult());
            transaction.commit();
            log.info("Successfully found entity with id " + id);
        } catch (Exception ex) {
            log.error("Error while finding entity with id " + id, ex);
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

            log.info("Successfully found entities");
        } catch (Exception ex) {
            log.error("Error while finding entities", ex);
        } finally {
            session.close();
        }
        return Optional.of(list);
    }

    @Override
    public Optional<Location> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Location> result = Optional.empty();
        try {
            result = findById(id);
            if (result.isPresent())
                session.delete(result.get());
            else {
                //todo replace with better exception
                throw new RuntimeException();
            }
            transaction.commit();
            log.info("Successfully deleted entity with id " + id);
        } catch (Exception ex) {
            log.error("Error while deleting entity with id " + id, ex);
        } finally {
            session.close();
        }
        return result;
    }
}
