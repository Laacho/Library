package bg.tu_varna.sit.library.core.login;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.ExceptionManager;
import bg.tu_varna.sit.library.models.login.LoginInputModel;
import bg.tu_varna.sit.library.models.login.LoginOperationModel;
import bg.tu_varna.sit.library.models.login.LoginOutputModel;
import bg.tu_varna.sit.library.utils.Hasher;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Processor
public class LoginProcessor extends BaseProcessor implements LoginOperationModel {
    private final UserCredentialsRepository userCredentialsRepository;
    private final ExceptionManager exceptionManager;
    private static final Logger log = Logger.getLogger(LoginProcessor.class);

    private LoginProcessor() {
        super();
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
        this.exceptionManager = SingletonFactory.getSingletonInstance(ExceptionManager.class);
    }

    @Override
    public Either<Exception, LoginOutputModel> process(LoginInputModel input) {

        return Try.of(() -> {
                    UserCredentials userCredentials = checkIfUsernameExists(input.getUsername());
                    checkIfPasswordMatches(userCredentials.getPassword(), input.getPassword());
                    UserSession userSession = buildUserSession(userCredentials);
                    log.info("Successfully logged in user with username: " + input.getUsername() + " and password: " + input.getPassword());
                    return conversionService.convert(userSession, LoginOutputModel.class);
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


    private void checkIfPasswordMatches(String dbPassword, String inputPassword) {
        if (!Hasher.verifyPassword(inputPassword, dbPassword)) {
            throw new RuntimeException();
        }
    }

    private UserCredentials checkIfUsernameExists(String username) {
        Optional<UserCredentials> searchedByUsername = userCredentialsRepository.findByUsername(username);
        if (searchedByUsername.isEmpty()) //todo
            throw new RuntimeException();
        else return searchedByUsername.get();
    }
}
