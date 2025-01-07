package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.exceptions.UserWithIdDoesNotExist;
import bg.tu_varna.sit.library.utils.annotations.Singleton;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.BorrowedBooks;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class BorrowedBooksRepositoryImpl implements BorrowedBooksRepository {
    private static final Logger log = Logger.getLogger(BorrowedBooksRepositoryImpl.class);

    private BorrowedBooksRepositoryImpl() {
    }

    @Override
    public Long save(BorrowedBooks entity) {
        Session session = Connection.openSession();
        Transaction transaction = null;
        Long result = null;
        try {
            transaction = session.beginTransaction();
           // result = (Long) session.save(entity);
            BorrowedBooks merge = session.merge(entity);
            result = merge.getId();
            transaction.commit();
            log.info("Successfully saved entity: " + entity);
        } catch (Exception ex) {
            log.error("Error while saving entity" + entity, ex);
        } finally {
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
                session.merge(entity);

            }
            transaction.commit();
            log.info("Successfully saved entities: " + entities);
        } catch (Exception ex) {
            log.error("Error while saving entities" + entities);
        } finally {
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
            log.error("Error while fetching entity by id" + id, ex);
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
                throw new UserWithIdDoesNotExist("Borrowed Books Not Found!","Borrowed Books with id " + id + " does not exist");
            }
            transaction.commit();
            log.info("Successfully deleted entity by id: " + id);
        } catch (Exception ex) {
            log.error("Error while deleting entity by id" + id, ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void update(BorrowedBooks books) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(books);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Error while updating entity");
        } finally {
            session.close();
        }
    }

    @Override
    public List<BorrowedBooks> findByUser(User user) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<BorrowedBooks> list = new ArrayList<>();
        try {
            String jpql = "SELECT r FROM BorrowedBooks r where r.user = :user";
            list.addAll(session.createQuery(jpql, BorrowedBooks.class)
                    .setParameter("user", user).
                    getResultList());
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
    public Optional<BorrowedBooks> findByUserAndBorrowedDateAndReturnDate(User user, LocalDate borrowedDate, LocalDate returnDate) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<BorrowedBooks> result = Optional.empty();
        try {
            String jpql = "SELECT r FROM BorrowedBooks r WHERE r.user = :user and r.borrowingDate = :borrowedDate and r.returnDate= :returnDate";
            result = Optional.of(session.createQuery(jpql, BorrowedBooks.class)
                    .setParameter("user", user)
                    .setParameter("borrowedDate", borrowedDate)
                    .setParameter("returnDate", returnDate)
                    .getSingleResult());
            transaction.commit();
            log.info("Successfully find entity by user: " + user);
        } catch (Exception ex) {
            log.error("Error while fetching entity by user" + user, ex);
        } finally {
            session.close();
        }
        return result;
    }
}
