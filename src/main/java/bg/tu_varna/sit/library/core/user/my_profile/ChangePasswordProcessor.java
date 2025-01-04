package bg.tu_varna.sit.library.core.user.my_profile;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.exceptions.NewPasswordSameAsTheOldOne;
import bg.tu_varna.sit.library.exceptions.PasswordDoesNotMatch;
import bg.tu_varna.sit.library.exceptions.UsernameDoesNotExist;
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

@Processor
public class ChangePasswordProcessor extends BaseProcessor implements ChangePasswordOperationModel {
    private final UserCredentialsRepository userCredentialsRepository;
    private static final Logger log = Logger.getLogger(ChangePasswordProcessor.class);

    public ChangePasswordProcessor() {
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, ChangePasswordOutputModel> process(ChangePasswordInputModel input) {
        return Try.of(() -> {
                    log.info("Started change password");
                    validate(input);
                    UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
                    String newPassword = input.getNewPassword();
                    String oldPassword = input.getOldPassword();
                    if (newPassword.equals(oldPassword)) {
                        throw new NewPasswordSameAsTheOldOne("Same password", "New password cant be the same as the old password");
                    }
                    if (!Hasher.verifyPassword(oldPassword, userSession.getPassword())) {
                        throw new PasswordDoesNotMatch("Password Does Not Match", "Old password does not match");
                    }
                    if (!input.getNewPassword().equals(input.getConfirmPassword())) {
                        throw new PasswordDoesNotMatch("Password Does Not Match", "New password and confirm password don't match!");
                    }
                    UserCredentials byUsername = userCredentialsRepository.findByUsername(userSession.getUsername())
                            .orElseThrow(() -> new UsernameDoesNotExist("Username Not Found", "User with username: " + userSession.getUsername() + " has not been found"));
                    byUsername.setPassword(Hasher.hashPassword(newPassword));
                    userCredentialsRepository.update(byUsername);
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
