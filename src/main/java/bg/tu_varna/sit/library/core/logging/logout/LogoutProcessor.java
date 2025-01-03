package bg.tu_varna.sit.library.core.logging.logout;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.exceptions.UsernameDoesNotExist;
import bg.tu_varna.sit.library.models.logout.LogoutInputModel;
import bg.tu_varna.sit.library.models.logout.LogoutOperationModel;
import bg.tu_varna.sit.library.models.logout.LogoutOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

@Processor
public class LogoutProcessor extends BaseProcessor implements LogoutOperationModel {
    private static final Logger log=Logger.getLogger(LogoutProcessor.class);
    private final UserCredentialsRepository userCredentialsRepository;

    public LogoutProcessor() {
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);

    }

    @Override
    public Either<Exception, LogoutOutputModel> process(LogoutInputModel input) {
        return Try.of(()->{
                    log.info("Started logging out process");
                    UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
                    if(!userSession.getAdmin()) {
                        saveCartBookToDB(userSession);
                    }
                    clearUserSession(userSession);
                    LogoutOutputModel output = outputBuilder();
                    log.info("Finished logging out process");
                    return output;

                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private  LogoutOutputModel outputBuilder() {
        return LogoutOutputModel.builder()
                .message("Logged out successfully")
                .build();
    }

    private void saveCartBookToDB(UserSession userSession) throws UsernameDoesNotExist {
        Set<Book> cartBooks = userSession.getCartBooks();
        UserCredentials user = userCredentialsRepository.findByUsername(userSession.getUsername())
                .orElseThrow(() -> new UsernameDoesNotExist("Username Not Found","User with username: " +userSession.getUsername()+" has not been found"));;
        user.setCartForBooks(cartBooks);
        userCredentialsRepository.update(user);
    }

    private void clearUserSession(UserSession userSession) {
        userSession.setFirstName("");
        userSession.setLastName("");
        userSession.setBirthdate(null);
        userSession.setUsername("");
        userSession.setPassword("");
        userSession.setVerified(false);
        userSession.setVerificationCode("");
        userSession.setDateOfVerification(null);
        userSession.setEmail("");
        userSession.setAdmin(false);
        userSession.setRating(0.0);
        userSession.setCartBooks(new HashSet<>());
        userSession.setNewEmailVerificationCode("");
        userSession.setWantsToLogout(false);
    }
}
