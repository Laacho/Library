package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public void save(User entity) {
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
    public void saveAll(List<User> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (User entity : entities) {
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
    public Optional<User> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<User> result = null;
        try {
            String jpql = "SELECT u FROM User u WHERE a.id = ?" + id;
            result = Optional.ofNullable(session.createQuery(jpql, User.class).getSingleResult());
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
    public Optional<List<User>> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> list = new ArrayList<>();
        try {
            String jpql = "SELECT u FROM User u";
            list.addAll(session.createQuery(jpql, User.class).getResultList());
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
    public Optional<User> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<User> result = null;
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
