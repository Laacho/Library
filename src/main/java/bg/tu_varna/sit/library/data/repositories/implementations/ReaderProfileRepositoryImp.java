package bg.tu_varna.sit.library.data.repositories.implementations;

import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Publisher;
import bg.tu_varna.sit.library.data.entities.ReaderProfile;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.utils.annotations.Singleton;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Singleton
public class ReaderProfileRepositoryImp implements ReaderProfileRepository {
    private static final Logger log = Logger.getLogger(ReaderProfileRepositoryImp.class);
    private ReaderProfileRepositoryImp(){};
    @Override
    public Long save(ReaderProfile entity) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long result = null;
        try {
            result = (Long) session.save(entity);
            transaction.commit();
            log.info("Successfully saved entity "+entity);
        }catch (Exception ex){
            log.error("Error saving entity "+entity, ex);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public void saveAll(List<ReaderProfile> entities) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (ReaderProfile entity : entities) {
                session.save(entity);
            }
            transaction.commit();
            log.info("Successfully saved entities "+entities);
        }catch (Exception ex){
            log.error("Error saving entities "+entities, ex);
        }finally {
            session.close();
        }
    }

    @Override
    public Optional<ReaderProfile> findById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<ReaderProfile> result = Optional.empty();
        try {
            String jpql = "SELECT r FROM ReaderProfile r WHERE r.id = :id";
            result = Optional.of(session.createQuery(jpql, ReaderProfile.class)
                    .setParameter("id", id)
                    .getSingleResult());
            transaction.commit();
            log.info("Successfully find publisher with id "+id);
        } catch (Exception ex) {
            log.error("Error finding publisher with id "+id, ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<ReaderProfile> findAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<ReaderProfile> list = new ArrayList<>();
        try {
            String jpql = "SELECT r FROM ReaderProfile r";
            list.addAll(session.createQuery(jpql, ReaderProfile.class).getResultList());
            transaction.commit();
            log.info("Successfully found publishers");
        } catch (Exception ex) {
            log.error("Error finding publishers", ex);
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public Optional<ReaderProfile> deleteById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<ReaderProfile> result = Optional.empty();
        try {
            result = findById(id);
            if (result.isPresent())
                session.delete(result.get());
            else {
                //todo replace with better exception
                throw new RuntimeException();
            }
            transaction.commit();
            log.info("Successfully deleted publisher with id "+id);
        } catch (Exception ex) {
            log.error("Error deleting publisher with id "+id, ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<ReaderProfile> findByUser(User user) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<ReaderProfile> result = Optional.empty();
        try {
            String jpql = "SELECT r FROM ReaderProfile r WHERE r.user = :user";
            result = Optional.of(session.createQuery(jpql, ReaderProfile.class)
                    .setParameter("user", user)
                    .getSingleResult());
            transaction.commit();
            log.info("Successfully find reader profile with username "+user.getUserCredentials().getUsername());
        } catch (Exception ex) {
            log.error("Error finding reader profile with username "+user.getUserCredentials().getUsername(), ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void update(ReaderProfile readerProfile) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(readerProfile);
            transaction.commit();
        } catch (Exception ex) {

        } finally {
            session.close();
        }
    }
}
