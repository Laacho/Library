package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.utils.annotations.Singleton;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Singleton
public class BookRepositoryImpl implements BookRepository {
    private static final Logger log = Logger.getLogger(BookRepositoryImpl.class);
    private BookRepositoryImpl(){};
    @Override
    public Long save(Book entity) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long result = null;
        try {
            result = (Long) session.save(entity);
            transaction.commit();
            log.info("Book saved: " + entity);
        }catch (Exception ex){
            log.error("Error while saving book: " + entity, ex);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public void saveAll(List<Book> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (Book entity : entities) {
                session.save(entity);
            }
            transaction.commit();
            log.info("Books saved: " + entities);
        }catch (Exception ex){
            log.error("Error while saving books: " + entities, ex);
        }finally {
            session.close();
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Book> result = Optional.empty();
        try {
            String jpql = "SELECT b FROM Book b WHERE b.id = :id";
            result = Optional.of(session.createQuery(jpql, Book.class)
                    .setParameter("id", id)
                    .getSingleResult());
            transaction.commit();
            log.info("Book found: " + result);
        } catch (Exception ex) {
            log.error("Error while finding book: " + id, ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<List<Book>> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Book> list = new ArrayList<>();
        try {
            String jpql = "SELECT b FROM Book b";
            list.addAll(session.createQuery(jpql, Book.class).getResultList());
            transaction.commit();
            log.info("Books found: " + list);
        } catch (Exception ex) {
            log.error("Error while finding books", ex);
        } finally {
            session.close();
        }
        return Optional.of(list);
    }

    @Override
    public Optional<Book> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Book> result = Optional.empty();
        try {
            result = findById(id);
            if (result.isPresent())
                session.delete(result.get());
            else {
                //todo replace with better exception
                throw new RuntimeException();
            }
            transaction.commit();
            log.info("Book deleted: " + result);
        } catch (Exception ex) {
            log.error("Error while deleting book: " + id, ex);
        } finally {
            session.close();
        }
        return result;
    }
}
