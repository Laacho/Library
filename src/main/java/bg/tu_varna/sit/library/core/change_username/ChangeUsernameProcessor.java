package bg.tu_varna.sit.library.core.change_username;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.exceptions.UsernameDoesNotExist;
import bg.tu_varna.sit.library.models.changeUsername.ChangeUsernameInputModel;
import bg.tu_varna.sit.library.models.changeUsername.ChangeUsernameOperationModel;
import bg.tu_varna.sit.library.models.changeUsername.ChangeUsernameOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.Optional;


@Processor
public class ChangeUsernameProcessor extends BaseProcessor implements ChangeUsernameOperationModel {
    private final UserCredentialsRepository userCredentialsRepository;
    private static final Logger log = Logger.getLogger(ChangeUsernameProcessor.class);

    public ChangeUsernameProcessor() {
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, ChangeUsernameOutputModel> process(ChangeUsernameInputModel input) {
        return Try.of(()->{
                    log.info("Started changing username");
                    UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
                    UserCredentials byUsername = userCredentialsRepository.findByUsername(userSession.getUsername())
                            .orElseThrow(()->new UsernameDoesNotExist("Username Not Found","User with username: " +input.getUsername()+" has not been found"));
                    checkIfUsernameExists(input.getUsername());
                    UserCredentials userCredentials = byUsername;
                    userCredentials.setUsername(input.getUsername());
                    userCredentialsRepository.update(userCredentials);
                    ChangeUsernameOutputModel output = outputBuilder();
                    log.info("Finished changing username");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private ChangeUsernameOutputModel outputBuilder() {
        return ChangeUsernameOutputModel.builder()
                .message("Changed username")
                .build();
    }

    private void checkIfUsernameExists(String username) {
        Optional<UserCredentials> byUsername1 = userCredentialsRepository.findByUsername(username);
        if(byUsername1.isPresent()) {
        throw new RuntimeException("Username already in use");
            }
    }
}
