package bg.tu_varna.sit.library.core.change_password;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.change_password.ChangePasswordInputModel;
import bg.tu_varna.sit.library.models.change_password.ChangePasswordOperationModel;
import bg.tu_varna.sit.library.models.change_password.ChangePasswordOutputModel;
import bg.tu_varna.sit.library.utils.Hasher;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.Optional;

@Processor
public class ChangePasswordProcessor extends BaseProcessor implements ChangePasswordOperationModel {
    private final UserCredentialsRepository userCredentialsRepository;
    private static final Logger log = Logger.getLogger(ChangePasswordProcessor.class);

    public ChangePasswordProcessor( ) {
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, ChangePasswordOutputModel> process(ChangePasswordInputModel input) {
        return Try.of(()->{
                log.info("Started change password");
                    UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
                    String newPassword = input.getNewPassword();
                    String oldPassword = input.getOldPassword();
                    if(newPassword.equals(oldPassword)) {
                        //todo
                        System.out.println("New password cant be the same as the old password");
                        throw new RuntimeException("New password cant be the same as the old password");
                    }
                    if (!Hasher.verifyPassword(oldPassword,userSession.getPassword())) {
                        System.out.println("Old password does not match");
                        throw new RuntimeException("Old password does not match");
                    }
                    if(!input.getNewPassword().equals(input.getConfirmPassword())){
                        System.out.println("New password and confirm password don't match!");
                        throw new RuntimeException("New password and confirm password don't match!");
                    }
                    Optional<UserCredentials> byUsername = userCredentialsRepository.findByUsername(userSession.getUsername());
                    if(byUsername.isPresent()) {
                        UserCredentials userCredentials = byUsername.get();
                        userCredentials.setPassword(Hasher.hashPassword(newPassword));
                        userCredentialsRepository.update(userCredentials);
                    }
                    ChangePasswordOutputModel changePasswordSuccessful = outputBuilder();
                    log.info("Finished change password");
                    return changePasswordSuccessful;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private ChangePasswordOutputModel outputBuilder() {
        return ChangePasswordOutputModel.builder()
                .message("Change password successful")
                .build();
    }
}
