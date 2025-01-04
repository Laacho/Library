package bg.tu_varna.sit.library.core.admin.approve_profiles;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Notification;
import bg.tu_varna.sit.library.data.entities.ReaderProfile;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.repositories.implementations.NotificationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.implementations.UserRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserRepository;
import bg.tu_varna.sit.library.exceptions.ReaderProfileAlreadyExists;
import bg.tu_varna.sit.library.exceptions.ReaderProfileDoesNotExist;
import bg.tu_varna.sit.library.exceptions.UserWithIdDoesNotExist;
import bg.tu_varna.sit.library.models.create_reader_profile.CreateReaderProfileInputModel;
import bg.tu_varna.sit.library.models.create_reader_profile.CreateReaderProfileOperationModel;
import bg.tu_varna.sit.library.models.create_reader_profile.CreateReaderProfileOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;

@Processor
public class CreateReaderProfileProcessor extends BaseProcessor implements CreateReaderProfileOperationModel {
    private final ReaderProfileRepository readerProfileRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private static final Logger log= Logger.getLogger(CreateReaderProfileProcessor.class);
    private CreateReaderProfileProcessor() {
        readerProfileRepository = SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class);
        userRepository = SingletonFactory.getSingletonInstance(UserRepositoryImpl.class);
        notificationRepository = SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class);
    }

    @Override
    public Either<Exception, CreateReaderProfileOutputModel> process(CreateReaderProfileInputModel input) {
        return Try.of(() -> {
                    log.info("Started creating reader profile");
                    validate(input);
                    User user = userRepository.findById(input.getId())
                            .orElseThrow(() -> new UserWithIdDoesNotExist("User Not Found","No user found with the provided ID. Please check the ID and try again."));
                    ReaderProfile readerProfile = readerProfileRepository.findByUser(user)
                            .orElseThrow(() -> new ReaderProfileDoesNotExist("Reader Profile Not Found","Reader profile for this user has not been found. Please ensure the user has a valid profile."));
                    if(readerProfile.getIsProfileApproved()) throw new ReaderProfileAlreadyExists("Reader Profile Exists","Reader profile with username "+user.getUserCredentials().getUsername()+" already exists.");
                    readerProfile = readerProfile.toBuilder()
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .isProfileApproved(true)
                            .user(user)
                            .build();
                    readerProfileRepository.update(readerProfile);
                    createNotification(user);
                    CreateReaderProfileOutputModel output = outputBuilder();
                    log.info("Finished creating reader profile");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private CreateReaderProfileOutputModel outputBuilder() {
        return CreateReaderProfileOutputModel.builder()
                .message("Successfully created reader profile.")
                .build();
    }

    private void createNotification(User user) {
        Notification profileHasBeenApproved = Notification.builder()
                .user(user)
                .message("Your profile has been approved")
                .isAdmin(false)
                .isRead(false)
                .build();
        notificationRepository.save(profileHasBeenApproved);
    }
}
