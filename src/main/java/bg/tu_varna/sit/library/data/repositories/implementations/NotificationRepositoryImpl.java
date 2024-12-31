package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Notification;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.utils.annotations.Singleton;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class NotificationRepositoryImpl implements NotificationRepository {
    private static final Logger log = Logger.getLogger(NotificationRepositoryImpl.class);

    private NotificationRepositoryImpl() {};

    @Override
    public Long save(Notification entity) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long result = null;
        try {
            result = (Long) session.save(entity);
            transaction.commit();
            log.info("Successfully saved " + entity);
        } catch (Exception ex) {
            log.error("Notification add error: " + ex.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void saveAll(List<Notification> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (Notification entity : entities) {
                session.save(entity);
            }
            transaction.commit();
            log.info("Successfully saved entities");
        }catch (Exception ex){
            log.error("Error while saving entities", ex);
        }finally {
            session.close();
        }
    }

    @Override
    public Optional<Notification> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Notification> result = Optional.empty();
        try {
            String jpql = "SELECT n FROM Notification n WHERE n.id = :id";
            result = Optional.of(session.createQuery(jpql, Notification.class)
                    .setParameter("id", id)
                    .getSingleResult());
            transaction.commit();
            log.info("Successfully found entity with id " + id);
        } catch (Exception ex) {
            log.error("Error while finding entity with id " + id, ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Notification> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Notification> list = new ArrayList<>();
        try {
            String jpql = "SELECT n FROM Notification n";
            list.addAll(session.createQuery(jpql, Notification.class).getResultList());
            transaction.commit();

            log.info("Successfully found entities");
        } catch (Exception ex) {
            log.error("Error while finding entities", ex);
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public Optional<Notification> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Notification> result = Optional.empty();
        try {
            result = findById(id);
            if (result.isPresent())
                session.delete(result.get());
            else {
                //todo replace with better exception
                throw new RuntimeException();
            }
            transaction.commit();
            log.info("Successfully deleted entity with id " + id);
        } catch (Exception ex) {
            log.error("Error while deleting entity with id " + id, ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Notification> findAllAdminNotification() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Notification> list = new ArrayList<>();
        try {
            String jpql = "SELECT n FROM Notification n where n.isAdmin = true";
            list.addAll(session.createQuery(jpql, Notification.class).getResultList());
            transaction.commit();

            log.info("Successfully found entities");
        } catch (Exception ex) {
            log.error("Error while finding entities", ex);
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public List<Notification> findAllUserNotification(Long userId) {
        Session session=Connection.openSession();
        Transaction transaction=session.beginTransaction();
        List<Notification> list=new ArrayList<>();
        try{
            String jpql="SELECT n from Notification n where n.isRead = false and n.user.id=:userId";
            list.addAll(session.createQuery(jpql, Notification.class)
                    .setParameter("userId", userId)
                    .getResultList());
            transaction.commit();
        }catch (Exception ex){
            log.error("Error while finding entities", ex);
        }
        finally {
            session.close();
        }
        return list;
    }

    @Override
    public void updateNotificationToBeRead(String notification, Long userId) {
        Session session=Connection.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            String jpql="UPDATE Notification n SET n.isRead = true WHERE n.message = :message AND n.user.id = :userID";
            session.createQuery(jpql)
                    .setParameter("userID", userId)
                    .setParameter("message", notification)
                            .executeUpdate();
            transaction.commit();
        }
        catch (Exception ex){
            log.error("Error while deleting entity with id " + userId, ex);
        }
        finally {
            session.close();
        }
    }

}
