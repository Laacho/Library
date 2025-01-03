package bg.tu_varna.sit.library.core.admin.notification_view;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.NotificationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.models.update_notifications_for_admin.UpdateNotificationsForAdminInputModel;
import bg.tu_varna.sit.library.models.update_notifications_for_admin.UpdateNotificationsForAdminOperationModel;
import bg.tu_varna.sit.library.models.update_notifications_for_admin.UpdateNotificationsForAdminOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.List;

@Processor
public class UpdateNotificationsForAdminProcessor extends BaseProcessor implements UpdateNotificationsForAdminOperationModel {
    private static final Logger log=Logger.getLogger(UpdateNotificationsForAdminProcessor.class);
    private final NotificationRepository notificationRepository;

    public UpdateNotificationsForAdminProcessor( ) {
        this.notificationRepository = SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class);
    }

    @Override
    public Either<Exception, UpdateNotificationsForAdminOutputModel> process(UpdateNotificationsForAdminInputModel input) {
        return Try.of(()->{
                log.info("Started processing update notifications for admin");
                    List<String> notifications = input.getMessages();
                    for (String notification : notifications) {
                        notificationRepository.updateNotificationsToBeReadForAdmin(notification);
                    }
                    UpdateNotificationsForAdminOutputModel output = outputBuilder();
                    log.info("Finished updating notifications to read");
                    return output;

        }).toEither()
                .mapLeft(exceptionManager::handle);
    }
    private UpdateNotificationsForAdminOutputModel outputBuilder() {
        return UpdateNotificationsForAdminOutputModel
                .builder()
                .message("Notifications updated successfully")
                .build();
    }
}
