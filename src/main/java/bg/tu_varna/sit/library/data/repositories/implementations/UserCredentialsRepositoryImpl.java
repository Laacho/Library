package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserCredentialsRepositoryImpl implements UserCredentialsRepository {
    private static final Logger log =Logger.getLogger(UserCredentialsRepositoryImpl.class);
    @Override
    public Long save(UserCredentials entity) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long result = null;
        try {
            result = (Long) session.save(entity);
            transaction.commit();
            log.info("Successfully saved entity: "+entity);
        } catch (Exception ex){
            log.error("Error saving entity: "+entity);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public void saveAll(List<UserCredentials> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (UserCredentials entity : entities) {
                session.save(entity);
            }
            transaction.commit();
        } catch (Exception ex) {
            log.error("Error saving entities: "+entities);
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<UserCredentials> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<UserCredentials> result = null;
        try {
            String jpql = "SELECT u FROM User u WHERE u.id = :id";
            result = Optional.ofNullable(session.createQuery(jpql, UserCredentials.class)
                    .setParameter("id", id)
                    .getSingleResult());
            transaction.commit();
            log.info("Successfully saved entity: "+result);
        } catch (Exception ex) {
            log.error("Error saving entity: "+result,ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<List<UserCredentials>> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<UserCredentials> list = new ArrayList<>();
        try {
            String jpql = "SELECT u FROM UserCredentials u";
            list.addAll(session.createQuery(jpql, UserCredentials.class).getResultList());
            transaction.commit();
            log.info("Successfully saved entities: "+list);
        } catch (Exception ex) {
            log.error("Error saving entities: "+list,ex);
        } finally {
            session.close();
        }
        return Optional.of(list);
    }

    @Override
    public Optional<UserCredentials> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<UserCredentials> result = null;
        try {
            result = findById(id);
            if (result.isPresent())
                session.delete(result.get());
            else {
                //todo replace with better exception
                throw new RuntimeException();
            }
            log.info("Successfully deleted entity: "+result);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Error saving entity: "+result,ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<UserCredentials> findByUsername(String username) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<UserCredentials> result = null;
        try {
            String jpql = "SELECT u FROM UserCredentials u WHERE u.username = :username";
            result = Optional.ofNullable(session.createQuery(jpql, UserCredentials.class)
                    .setParameter("username", username)
                    .getSingleResult());
            transaction.commit();
        } catch (Exception ex) {

        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<UserCredentials> findByEmail(String email) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<UserCredentials> result = null;
        try {
            String jpql = "SELECT u FROM UserCredentials u WHERE u.email = :email";
            result = Optional.ofNullable(session.createQuery(jpql, UserCredentials.class)
                    .setParameter("email", email)
                    .getSingleResult());
            transaction.commit();
        } catch (Exception ex) {

        } finally {
            session.close();
        }
        return result;
    }
}
