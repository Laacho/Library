package bg.tu_varna.sit.library.data.repositories.interfaces;

import bg.tu_varna.sit.library.data.entities.Notification;

import java.util.List;

public interface NotificationRepository extends GenericRepository<Notification> {

    List<Notification> findAllAdminNotification();
    List<Notification> findAllUserNotification(Long userId);
    void updateNotificationToBeRead(String notification, Long userId);
}
