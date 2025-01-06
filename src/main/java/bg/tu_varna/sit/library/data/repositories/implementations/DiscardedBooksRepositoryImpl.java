package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.exceptions.UserWithIdDoesNotExist;
import bg.tu_varna.sit.library.utils.annotations.Singleton;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.DiscardedBooks;
import bg.tu_varna.sit.library.data.repositories.interfaces.DiscardedBooksRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Singleton
public class DiscardedBooksRepositoryImpl implements DiscardedBooksRepository {
    private static final Logger log=Logger.getLogger(DiscardedBooksRepositoryImpl.class);
    private DiscardedBooksRepositoryImpl(){};
    @Override
    public Long save(DiscardedBooks entity) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long result = null;
        try {
            result = (Long) session.save(entity);
            transaction.commit();
            log.info("Successfully saved entity" + entity);
        }catch (Exception ex){
            log.error("Error while saving entity" + entity);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public void saveAll(List<DiscardedBooks> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (DiscardedBooks entity : entities) {
                session.save(entity);
            }
            transaction.commit();
        } catch (Exception ex) {
            log.error("Error while saving entities" + entities);
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<DiscardedBooks> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<DiscardedBooks> result = Optional.empty();
        try {
            String jpql = "SELECT d FROM DiscardedBooks d WHERE d.id = :id";
            result = Optional.of(session.createQuery(jpql, DiscardedBooks.class)
                    .setParameter("id", id)
                    .getSingleResult());
            transaction.commit();
            log.info("Successfully find entity by id" + id);
        } catch (Exception ex) {
            log.error("Error while finding entity by id" + id);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<DiscardedBooks> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<DiscardedBooks> list = new ArrayList<>();
        try {
            String jpql = "SELECT d FROM DiscardedBooks d";
            list.addAll(session.createQuery(jpql, DiscardedBooks.class).getResultList());
            transaction.commit();
            log.info("Successfully find entities");
        } catch (Exception ex) {
            log.error("Error while finding entities");
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public Optional<DiscardedBooks> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<DiscardedBooks> result = Optional.empty();
        try {
            result = findById(id);
            if (result.isPresent())
                session.delete(result.get());
            else {
                throw new UserWithIdDoesNotExist("Discarded Books Not Found!","Discarded Books with id " + id + " does not exist");
            }
            transaction.commit();
            log.info("Successfully deleted entity by id" + id);
        } catch (Exception ex) {
            log.error("Error while deleting entity by id" + id);
        } finally {
            session.close();
        }
        return result;
    }
}
