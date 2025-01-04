package bg.tu_varna.sit.library.core.admin.settings;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.exceptions.UserWithIdDoesNotExist;
import bg.tu_varna.sit.library.models.delete_user.DeleteUserInputModel;
import bg.tu_varna.sit.library.models.delete_user.DeleteUserOperationModel;
import bg.tu_varna.sit.library.models.delete_user.DeleteUserOutputModel;
import bg.tu_varna.sit.library.utils.EmailService;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.concurrent.CompletableFuture;

@Processor
public class DeleteUserProcessor extends BaseProcessor implements DeleteUserOperationModel {
    private final static Logger log = Logger.getLogger(DeleteUserProcessor.class);
    private final UserCredentialsRepository userCredentialsRepository;

    public DeleteUserProcessor() {
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, DeleteUserOutputModel> process(DeleteUserInputModel input) {
        return Try.of(() -> {
                    log.info("Started deleting user");
                    validate(input);
                    userCredentialsRepository.deleteById(input.getUserId()).orElseThrow(() -> new UserWithIdDoesNotExist("User Not Found", "User with " + input.getUserId() + " does not exist"));
                    CompletableFuture.runAsync(() -> EmailService.sendContactMail(input.getEmail(), "Your profile was deleted!", "Deleted profile!"));
                    DeleteUserOutputModel outputModel = outputBuilder();
                    log.info(outputModel.getMessage());
                    return outputModel;
                })
                .toEither()
                .mapLeft(exceptionManager::handle);
    }

    private static DeleteUserOutputModel outputBuilder() {
        return DeleteUserOutputModel.builder()
                .message("User successfully deleted")
                .build();
    }
}
