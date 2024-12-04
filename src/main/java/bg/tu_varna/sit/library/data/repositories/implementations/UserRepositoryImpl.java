package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.utils.annotations.Singleton;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class UserRepositoryImpl implements UserRepository {
    private static final Logger log=Logger.getLogger(UserRepositoryImpl.class);
    private UserRepositoryImpl(){};
    @Override
    public Long save(User entity) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long result = null;
        try {
            result = (Long) session.save(entity);
            transaction.commit();
            log.info("User saved");
        }catch (Exception ex){
            log.error("Error while saving entity: "+entity,ex);
        }finally {
           session.close();
        }
        return result;
    }

    @Override
    public void saveAll(List<User> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (User entity : entities) {
                session.save(entity);
            }
            transaction.commit();
            log.info("Users saved");
        }catch (Exception ex){
            log.error("Error while saving entities: "+entities,ex);
        }finally {
            session.close();
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<User> result = Optional.empty();
        try {
            String jpql = "SELECT u FROM User u WHERE u.id = :id";
            result = Optional.of(session.createQuery(jpql, User.class)
                    .setParameter("id", id)
                    .getSingleResult());
            transaction.commit();
            log.info("User found with id: "+id);
        } catch (Exception ex) {
            log.error("Error while fetching user: "+id,ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<User> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> list = new ArrayList<>();
        try {
            String jpql = "SELECT u FROM User u";
            list.addAll(session.createQuery(jpql, User.class).getResultList());
            transaction.commit();
            log.info("Users found");
        } catch (Exception ex) {
            log.error("Error while fetching users: "+ex,ex);
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public Optional<User> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<User> result = Optional.empty();
        try {
            result = findById(id);
            if (result.isPresent())
                session.delete(result.get());
            else {
                //todo replace with better exception
                throw new RuntimeException();
            }
            transaction.commit();
            log.info("User deleted: "+id);
        } catch (Exception ex) {
            log.error("Error while fetching user: "+id,ex);
        } finally {
            session.close();
        }
        return result;
    }
}
