package bg.tu_varna.sit.library.core.reset_password_for_user;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.reset_password_for_user.ResetPasswordForUserInputModel;
import bg.tu_varna.sit.library.models.reset_password_for_user.ResetPasswordForUserOperationModel;
import bg.tu_varna.sit.library.models.reset_password_for_user.ResetPasswordForUserOutputModel;
import bg.tu_varna.sit.library.utils.EmailService;
import bg.tu_varna.sit.library.utils.Hasher;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.concurrent.CompletableFuture;

@Processor
public class ResetPasswordForUserProcessor extends BaseProcessor implements ResetPasswordForUserOperationModel {
    private static final Logger log = Logger.getLogger(ResetPasswordForUserProcessor.class);
    private final UserCredentialsRepository userCredentialsRepository;

    private ResetPasswordForUserProcessor( ) {
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, ResetPasswordForUserOutputModel> process(ResetPasswordForUserInputModel input) {
        return Try.of(()->{
                    log.info("Started resetting password for user");
                    UserCredentials userCredentials = input.getUserCredentials();
                    String passwordForUser = EmailService.generateVerificationCode();
                    String newTempPassword = Hasher.hashPassword(passwordForUser);
                    userCredentials.setPassword(newTempPassword);
                    userCredentialsRepository.update(userCredentials);
                    CompletableFuture.runAsync(()->EmailService.sendContactMail(userCredentials.getEmail(),"Your password was reset.This is your new password: ",  passwordForUser));
                    ResetPasswordForUserOutputModel build = ResetPasswordForUserOutputModel.builder().message("Successfully reset password for user!").build();
                    log.info(build.getMessage());
                    return build;
                })
                .toEither()
                .mapLeft(exceptionManager::handle);
    }
}
