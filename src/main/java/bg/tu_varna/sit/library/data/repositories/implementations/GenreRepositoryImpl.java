package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Genre;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenreRepository;
import bg.tu_varna.sit.library.exceptions.UserWithIdDoesNotExist;
import bg.tu_varna.sit.library.utils.annotations.Singleton;
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
        Optional<Genre> result = Optional.empty();
        try {
            String jpql = "SELECT g FROM Genre g WHERE g.id = :id";
            result = Optional.of(session.createQuery(jpql, Genre.class)
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
    public List<Genre> findAll() {
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
        return list;
    }

    @Override
    public Optional<Genre> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Genre> result = Optional.empty();
        try {
            result = findById(id);
            if (result.isPresent())
                session.delete(result.get());
            else {
                throw new UserWithIdDoesNotExist("Genre Not Found!","Genre with id " + id + " does not exist");
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

    @Override
    public Optional<Genre> findByName(String name) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Genre> result = Optional.empty();
        try{
            String jpql = "SELECT g FROM Genre g WHERE LOWER(g.name) = :name";
            result= Optional.of(session.createQuery(jpql, Genre.class)
                    .setParameter("name",name)
                    .getSingleResult());
            transaction.commit();
            log.info("Successfully found entity by name: " + name);
        }
        catch (Exception ex){
            log.error("Error finding entity by name", ex);
        }
        finally {
            session.close();
        }
        return result;
    }
}
