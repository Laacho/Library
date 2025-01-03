package bg.tu_varna.sit.library.core.user.notifications;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.NotificationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.remove_notifications.UpdateNotificationsToReadInputModel;
import bg.tu_varna.sit.library.models.remove_notifications.UpdateNotificationsToReadOperationModel;
import bg.tu_varna.sit.library.models.remove_notifications.UpdateNotificationsToReadOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.List;

@Processor
public class UpdateNotificationsToReadProcessor extends BaseProcessor implements UpdateNotificationsToReadOperationModel {

    private static final Logger log= Logger.getLogger(UpdateNotificationsToReadProcessor.class);
    private final NotificationRepository notificationRepository;
    private final UserCredentialsRepository userCredentialsRepository;

    private UpdateNotificationsToReadProcessor( ) {
        this.notificationRepository = SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class);
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, UpdateNotificationsToReadOutputModel> process(UpdateNotificationsToReadInputModel input) {
        return Try.of(()->{
                log.info("Started updating notifications to read");
                    List<String> notifications = input.getNotifications();
                    UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
                    UserCredentials userCredentials = userCredentialsRepository.findByUsername(userSession.getUsername()).get();
                    for (String notification : notifications) {
                        notificationRepository.updateNotificationToBeRead(notification, userCredentials.getId());
                    }
                    UpdateNotificationsToReadOutputModel output = outputBuilder();
                    log.info("Finished updating notifications to read");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private UpdateNotificationsToReadOutputModel outputBuilder() {
        return UpdateNotificationsToReadOutputModel
                .builder()
                .message("Notifications updated successfully")
                .build();
    }
}
