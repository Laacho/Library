package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.DiscardedBooks;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.exceptions.UserNotFound;
import bg.tu_varna.sit.library.exceptions.UserWithIdDoesNotExist;
import bg.tu_varna.sit.library.utils.annotations.Singleton;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class UserCredentialsRepositoryImpl implements UserCredentialsRepository {
    private static final Logger log = Logger.getLogger(UserCredentialsRepositoryImpl.class);

    private UserCredentialsRepositoryImpl() {
    }

    ;

    @Override
    public Long save(UserCredentials entity) {
        Session session = Connection.openSession();
        Transaction transaction = null;
        Long result = null;
        try {
            transaction = session.beginTransaction();
            UserCredentials merge = session.merge(entity);
            result = merge.getId();
            transaction.commit();
            log.info("Successfully saved entity: " + entity);
        } catch (Exception ex) {
            log.error("Error saving entity: " + entity);
        } finally {
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
            log.error("Error saving entities: " + entities);
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<UserCredentials> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<UserCredentials> result = Optional.empty();
        try {
            String jpql = "SELECT u FROM UserCredentials u WHERE u.id = :id";
            result = Optional.of(session.createQuery(jpql, UserCredentials.class)
                    .setParameter("id", id)
                    .getSingleResult());
            transaction.commit();
            log.info("Successfully saved entity: " + result);
        } catch (Exception ex) {
            log.error("Error saving entity: " + result, ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<UserCredentials> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<UserCredentials> list = new ArrayList<>();
        try {
            String jpql = "SELECT u FROM UserCredentials u";
            list.addAll(session.createQuery(jpql, UserCredentials.class).getResultList());
            transaction.commit();
            log.info("Successfully saved entities: " + list);
        } catch (Exception ex) {
            log.error("Error saving entities: " + list, ex);
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public Optional<UserCredentials> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<UserCredentials> result = Optional.empty();
        try {
            result = findById(id);
            if (result.isPresent())
                session.delete(result.get());
            else {
                throw new UserWithIdDoesNotExist("User Credentials Not Found!","User Credentials with id " + id + " does not exist");
            }
            log.info("Successfully deleted entity: " + result);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Error saving entity: " + result, ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<UserCredentials> findByUsername(String username) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<UserCredentials> result = Optional.empty();
        try {
            String jpql = "SELECT u FROM UserCredentials u WHERE u.username = :username";
            result = Optional.of(session.createQuery(jpql, UserCredentials.class)
                    .setParameter("username", username)
                    .getSingleResult());
            transaction.commit();
        } catch (Exception ex) {
            log.error("Error finding entity with username: " + username);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<UserCredentials> findByEmail(String email) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<UserCredentials> result = Optional.empty();
        try {
            String jpql = "SELECT u FROM UserCredentials u WHERE u.email = :email";
            result = Optional.of(session.createQuery(jpql, UserCredentials.class)
                    .setParameter("email", email)
                    .getSingleResult());
            transaction.commit();
        } catch (Exception ex) {
            log.error("Error finding entity by email: " + email);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void update(UserCredentials userCredentials) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(userCredentials);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Error updating entity");
        } finally {
            session.close();
        }
    }

    @Override
    public List<UserCredentials> findAllUsers() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<UserCredentials> list = new ArrayList<>();
        try {
            String jpql = "SELECT u FROM UserCredentials u where u.admin=false";
            list.addAll(session.createQuery(jpql, UserCredentials.class).getResultList());
            transaction.commit();
            log.info("Successfully saved entities: " + list);
        } catch (Exception ex) {
            log.error("Error saving entities: " + list, ex);
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public Optional<UserCredentials> findByUser(User user) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<UserCredentials> result = Optional.empty();
        try {
            String jpql = "SELECT u FROM UserCredentials u WHERE u.user = :user";
            result = Optional.of(session.createQuery(jpql, UserCredentials.class)
                    .setParameter("user", user)
                    .getSingleResult());
            transaction.commit();
        } catch (Exception ex) {
            log.error("Error finding user credentials by given user");
        } finally {
            session.close();
        }
        return result;
    }
}
