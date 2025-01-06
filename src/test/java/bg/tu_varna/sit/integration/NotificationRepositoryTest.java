package bg.tu_varna.sit.integration;

import bg.tu_varna.sit.library.application.ConfigurationDatabase;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Notification;
import bg.tu_varna.sit.library.data.repositories.implementations.NotificationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenericRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationRepositoryTest {

    private NotificationRepository notificationRepository;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        notificationRepository= SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class);
        ConfigurationDatabase.config();
        Connection.openSession();
    }

    @Test
    void getInstance(){
        assertInstanceOf(NotificationRepositoryImpl.class, notificationRepository);
        assertInstanceOf(GenericRepository.class, notificationRepository);
    }
    @Test
    void TestSingleton(){
        NotificationRepository instance1 = SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class);
        assertSame(instance1, notificationRepository);
    }
    @Test
    void testGetAll(){
        assertNotNull(notificationRepository.findAll());
    }
    @Test
    void testFindByIdFail(){
        assertEquals(Optional.empty(), notificationRepository.findById(-1L));
        assertEquals(Optional.empty(), notificationRepository.findById(4443242432L));
    }
    @Test
    void testFindByIdSuccess(){
        Optional<Notification> byId = notificationRepository.findById(1L);
        assertNotNull(byId);
        assertTrue(byId.isPresent());
        Notification notification = byId.get();
        assertNotNull(notification);
    }
    @Test
    void testFindAllAdminNotifications(){
        List<Notification> allAdminNotification = notificationRepository.findAllAdminNotification();
        assertNotNull(allAdminNotification);
        if(!allAdminNotification.isEmpty()){
            assertTrue(allAdminNotification.get(0).getIsAdmin());
            assertNull(allAdminNotification.get(0).getUser());
        }
    }
    @Test
    void testFindAllNotificationsForUser(){
        List<Notification> allUserNotification = notificationRepository.findAllUserNotification(1L);
        assertNotNull(allUserNotification);
        if(!allUserNotification.isEmpty()){
            assertFalse(allUserNotification.get(0).getIsAdmin());
            assertNotNull(allUserNotification.get(0).getUser());
        }
    }
}
