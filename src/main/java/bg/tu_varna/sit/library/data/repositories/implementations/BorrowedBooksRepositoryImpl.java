package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.utils.annotations.Singleton;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.BorrowedBooks;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Singleton
public class BorrowedBooksRepositoryImpl implements BorrowedBooksRepository {
    private static final Logger log = Logger.getLogger(BorrowedBooksRepositoryImpl.class);
    private BorrowedBooksRepositoryImpl(){};
    @Override
    public Long save(BorrowedBooks entity) {
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
    public void saveAll(List<BorrowedBooks> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (BorrowedBooks entity : entities) {
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
    public Optional<BorrowedBooks> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<BorrowedBooks> result = Optional.empty();
        try {
            String jpql = "SELECT r FROM BorrowedBooks r WHERE r.id = :id";
            result = Optional.of(session.createQuery(jpql, BorrowedBooks.class)
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
    public List<BorrowedBooks> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<BorrowedBooks> list = new ArrayList<>();
        try {
            String jpql = "SELECT r FROM BorrowedBooks r";
            list.addAll(session.createQuery(jpql, BorrowedBooks.class).getResultList());
            transaction.commit();
            log.info("Successfully find entities: " + list);
        } catch (Exception ex) {
            log.error("Error while fetching entities" + list);
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public Optional<BorrowedBooks> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<BorrowedBooks> result = Optional.empty();
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
