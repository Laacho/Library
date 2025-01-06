package bg.tu_varna.sit.library.core.logging.register;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserRepository;
import bg.tu_varna.sit.library.exceptions.EmailIsPresent;
import bg.tu_varna.sit.library.exceptions.UsernameAlreadyExists;
import bg.tu_varna.sit.library.exceptions.UsernameDoesNotExist;
import bg.tu_varna.sit.library.models.register.RegisterInputModel;
import bg.tu_varna.sit.library.models.register.RegisterOperationModel;
import bg.tu_varna.sit.library.models.register.RegisterOutputModel;
import bg.tu_varna.sit.library.utils.EmailService;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Processor
public class RegisterProcessor extends BaseProcessor implements RegisterOperationModel {
    private final UserCredentialsRepository userCredentialsRepository;
    private final UserRepository userRepository;

    private static final Logger log= Logger.getLogger(RegisterProcessor.class);
    private RegisterProcessor() {
        super();
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
        this.userRepository = SingletonFactory.getSingletonInstance(UserRepositoryImpl.class);
    }

    @Override
    public Either<Exception, RegisterOutputModel> process(RegisterInputModel input) {
        return Try.of(() -> {

                    log.info("Started register process");
                    validate(input);
                    checkForExistingEmail(input);
                    checkForExistingUsername(input);
                    User build = conversionService.convert(input, User.class);
                    Long id = userRepository.save(build);
                    User user = userRepository.findById(id).get();
                    UserCredentials converted = conversionService.convert(input, UserCredentials.class);
                    String verificationCode = EmailService.generateVerificationCode();
                    UserCredentials userCredentials = buildUserCredentialsWithUserAndVerificationCode(converted, user, verificationCode);
                    userCredentialsRepository.save(userCredentials);
                    CompletableFuture.runAsync(() -> EmailService.sendMail(userCredentials.getEmail(), verificationCode));
                    buildUserSession(userCredentials);
                    log.info("Finished register process");
                    return conversionService.convert("Successfully", RegisterOutputModel.class);
                }).toEither()
                .mapLeft(exceptionManager::handle);

    }

    @NotNull
    private UserSession buildUserSession(UserCredentials userCredentials) {
        UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
        userSession.setAdmin(userCredentials.getAdmin());
        userSession.setEmail(userCredentials.getEmail());
        userSession.setUsername(userCredentials.getUsername());
        userSession.setBirthdate(userCredentials.getUser().getBirthdate());
        userSession.setPassword(userCredentials.getPassword());
        userSession.setRating(userCredentials.getRating());
        userSession.setFirstName(userCredentials.getUser().getFirstName());
        userSession.setLastName(userCredentials.getUser().getLastName());
        userSession.setVerified(userCredentials.getVerified());
        userSession.setDateOfVerification(userCredentials.getDateOfVerification());
        userSession.setVerificationCode(userCredentials.getVerificationCode());
        return userSession;
    }

    private UserCredentials buildUserCredentialsWithUserAndVerificationCode(UserCredentials converted, User user, String verificationCode) {
        return converted.toBuilder().
                user(user).
                verificationCode(verificationCode).
                build();
    }

    private void checkForExistingUsername(RegisterInputModel input) throws UsernameAlreadyExists {
        Optional<UserCredentials> byUsername = userCredentialsRepository.findByUsername(input.getUsername());
        if(byUsername.isPresent()){
            throw new UsernameAlreadyExists("Username Found","User with username: " +input.getUsername()+" already exist");
        }
    }

    private void checkForExistingEmail(RegisterInputModel input) throws EmailIsPresent {
        Optional<UserCredentials> searchedForExistingEmail = userCredentialsRepository.findByEmail(input.getEmail());
        if (searchedForExistingEmail.isPresent())
            throw new EmailIsPresent("Email Is In Use","Email already in use");
    }
}
