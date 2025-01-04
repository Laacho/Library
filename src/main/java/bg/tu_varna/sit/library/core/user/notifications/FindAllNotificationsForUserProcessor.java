package bg.tu_varna.sit.library.core.user.notifications;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Notification;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.NotificationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.exceptions.UsernameDoesNotExist;
import bg.tu_varna.sit.library.models.find_all_notifications_for_user.FindAllNotificationsForUserInputModel;
import bg.tu_varna.sit.library.models.find_all_notifications_for_user.FindAllNotificationsForUserOperationModel;
import bg.tu_varna.sit.library.models.find_all_notifications_for_user.FindAllNotificationsForUserOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Processor
public class FindAllNotificationsForUserProcessor extends BaseProcessor implements FindAllNotificationsForUserOperationModel {
    private static final Logger log= Logger.getLogger(FindAllNotificationsForUserProcessor.class);
    private final NotificationRepository notificationRepository;
    private final UserCredentialsRepository userCredentialsRepository;

    public FindAllNotificationsForUserProcessor() {
        this.notificationRepository = SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class);
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, FindAllNotificationsForUserOutputModel> process(FindAllNotificationsForUserInputModel input) {
        return Try.of(()->{
                    log.info("Started getting all notifications for user");
                    validate(input);
                    UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
                    UserCredentials loggedUser = userCredentialsRepository.findByUsername(userSession.getUsername())
                            .orElseThrow(() -> new UsernameDoesNotExist("Username Not Found","User with username: " +userSession.getUsername()+" has not been found"));
                    List<Notification> allUserNotification = notificationRepository.findAllUserNotification(loggedUser.getId());
                    List<String> resultList = convertToOutput(allUserNotification);
                    FindAllNotificationsForUserOutputModel output = buildOutput(resultList);
                    log.info("Finished getting all notifications for user");
                    return output;
                })
                .toEither()
                .mapLeft(exceptionManager::handle);
    }

    private FindAllNotificationsForUserOutputModel buildOutput(List<String> resultMap) {
        return FindAllNotificationsForUserOutputModel.builder()
                .notifications(resultMap)
                .build();
    }

    private  List<String> convertToOutput(List<Notification> allUserNotification) {
        List<String> result=new ArrayList<>();
         for (Notification notification : allUserNotification) {
            String message = notification.getMessage();
                result.add(message);
        }
        return result;
    }
}
