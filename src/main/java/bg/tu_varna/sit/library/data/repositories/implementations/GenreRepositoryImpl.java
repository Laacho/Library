package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.common.annotations.Singleton;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Genre;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenreRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Singleton
public class GenreRepositoryImpl implements GenreRepository {
    private static final Logger log=Logger.getLogger(GenreRepositoryImpl.class);
    private GenreRepositoryImpl(){};
    @Override
    public Long save(Genre entity) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long result = null;
        try {
            result = (Long) session.save(entity);
            transaction.commit();
            log.info("Successfully saved entity");
        }catch (Exception ex){
            log.error("Error saving entity", ex);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public void saveAll(List<Genre> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (Genre entity : entities) {
                session.save(entity);
            }
            transaction.commit();
            log.info("Successfully saved entities");
        } catch (Exception ex) {
            log.error("Error saving entities", ex);
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Genre> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Genre> result = null;
        try {
            String jpql = "SELECT g FROM Genre g WHERE g.id = :id";
            result = Optional.ofNullable(session.createQuery(jpql, Genre.class)
                    .setParameter("id", id)
                    .getSingleResult());
            transaction.commit();
            // log.info("Get all list");
        } catch (Exception ex) {
            log.error("Error finding entity by id", ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<List<Genre>> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Genre> list = new ArrayList<>();
        try {
            String jpql = "SELECT g FROM Genre g";
            list.addAll(session.createQuery(jpql, Genre.class).getResultList());
            transaction.commit();
            log.info("Successfully found entities");
        } catch (Exception ex) {
            log.error("Error finding entities", ex);
        } finally {
            session.close();
        }
        return Optional.of(list);
    }

    @Override
    public Optional<Genre> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Genre> result = null;
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
            log.error("Error deleting entity by id", ex);
        } finally {
            session.close();
        }
        return result;
    }
}
