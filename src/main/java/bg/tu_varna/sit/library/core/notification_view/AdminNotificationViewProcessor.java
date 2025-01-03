package bg.tu_varna.sit.library.core.notification_view;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Notification;
import bg.tu_varna.sit.library.data.repositories.implementations.NotificationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.models.notifications_view.AdminNotificationViewInputModel;
import bg.tu_varna.sit.library.models.notifications_view.AdminNotificationViewOperationModel;
import bg.tu_varna.sit.library.models.notifications_view.AdminNotificationViewOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Processor
public class AdminNotificationViewProcessor extends BaseProcessor implements AdminNotificationViewOperationModel {
    private final NotificationRepository notificationRepository;

    private AdminNotificationViewProcessor() {
        notificationRepository = SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class);
    }

    @Override
    public Either<Exception, AdminNotificationViewOutputModel> process(AdminNotificationViewInputModel input) {
        return Try.of(() -> {
                    List<Notification> notificationList = notificationRepository.findAll();
                    List<String> adminNotificationMessages = getAdminNotificationMessages(notificationList);
                    return AdminNotificationViewOutputModel.builder().messages(adminNotificationMessages).build();
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    @NotNull
    private List<String> getAdminNotificationMessages(List<Notification> notificationList) {
        List<String> adminNotificationMessages = new ArrayList<>();
        for (Notification notification : notificationList) {
            if(notification.getIsAdmin() && !notification.getIsRead()){
                adminNotificationMessages.add(notification.getMessage());
            }
        }
        return adminNotificationMessages;
    }
}
