package bg.tu_varna.sit.library.core.register;

import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserRepositoryImpl;
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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@Processor
public class LoginProcessor extends BaseProcessor implements LoginOperationModel {
    private final UserCredentialsRepositoryImpl userCredentialsRepository;
    private final UserRepositoryImpl userRepository;
    private final ExceptionManager exceptionManager;
    private static final Logger log = Logger.getLogger(LoginProcessor.class);

    private LoginProcessor() {
        super();
        this.userRepository = SingletonFactory.getSingletonInstance(UserRepositoryImpl.class);
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
        this.exceptionManager = SingletonFactory.getSingletonInstance(ExceptionManager.class);
    }

    @Override
    public Either<Exception, LoginOutputModel> process(LoginInputModel input) {

        return Try.of(() -> {
                    UserCredentials userCredentials = checkIfUsernameExists(input.getUsername());
                    checkIfPasswordMatches(userCredentials.getPassword(), input.getPassword());
                    
                    User user = userCredentials.getUser();
                    UserSession result = conversionService.convert(userCredentials, UserSession.class).toBuilder()
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .birthdate(user.getBirthdate())
                            .build();
                    log.info("Successfully logged in user with username: " + input.getUsername() + " and password: " + input.getPassword());
                    return conversionService.convert(result, LoginOutputModel.class);
                }).toEither()
                .mapLeft(exceptionManager::handle);
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
