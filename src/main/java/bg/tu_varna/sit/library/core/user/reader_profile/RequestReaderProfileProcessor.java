package bg.tu_varna.sit.library.core.user.reader_profile;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Notification;
import bg.tu_varna.sit.library.data.entities.ReaderProfile;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.NotificationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.exceptions.UsernameDoesNotExist;
import bg.tu_varna.sit.library.models.request_reader_profile.RequestReaderProfileInputModel;
import bg.tu_varna.sit.library.models.request_reader_profile.RequestReaderProfileOperationModel;
import bg.tu_varna.sit.library.models.request_reader_profile.RequestReaderProfileOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

@Processor
public class RequestReaderProfileProcessor extends BaseProcessor implements RequestReaderProfileOperationModel {
    private final ReaderProfileRepository readerProfileRepository;
    private final NotificationRepository notificationRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private static final Logger log= Logger.getLogger(RequestReaderProfileProcessor.class);
    private RequestReaderProfileProcessor() {
        readerProfileRepository = SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class);
        notificationRepository = SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class);
        userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, RequestReaderProfileOutputModel> process(RequestReaderProfileInputModel input) {
        return Try.of(() -> {
                    log.info("Started request reader profile");
                    validate(input);
                    UserCredentials userCredentials = userCredentialsRepository.findByUsername(input.getUsername())
                            .orElseThrow(() -> new UsernameDoesNotExist("Username Not Found","User with username: " +input.getUsername()+" has not been found"));
                    ReaderProfile readerProfile = ReaderProfile.builder()
                            .isProfileApproved(false)
                            .user(userCredentials.getUser())
                            .build();
                    readerProfileRepository.save(readerProfile);
                    Notification notification = Notification.builder()
                            .isRead(false)
                            .message("User with username " + input.getUsername() + " wants to create a reader profile. Please approve it!")
                            .isAdmin(true)
                            .build();
                    notificationRepository.save(notification);
                    RequestReaderProfileOutputModel output = outputBuilder();
                    log.info("Finished request reader profile");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private static RequestReaderProfileOutputModel outputBuilder() {
        return RequestReaderProfileOutputModel.builder().message("The request was successful").build();
    }
}
