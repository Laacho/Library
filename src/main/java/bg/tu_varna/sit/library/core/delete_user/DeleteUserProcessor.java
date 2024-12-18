package bg.tu_varna.sit.library.core.delete_user;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.demote_user.DemoteUserProcessor;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
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
    public DeleteUserProcessor( ) {
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }
    @Override
    public Either<Exception, DeleteUserOutputModel> process(DeleteUserInputModel input) {
        return Try.of(()->{
                    log.info("Started deleting user");
                     userCredentialsRepository.deleteById(input.getUserId());
                    CompletableFuture.runAsync(()-> EmailService.sendContactMail(input.getEmail(),"Your profile was deleted!","Deleted profile!"));
                    DeleteUserOutputModel outputModel = DeleteUserOutputModel.builder().message("User successfully deleted").build();
                    log.info(outputModel.getMessage());
                    return outputModel;
                })
                .toEither()
                .mapLeft(exceptionManager::handle);
    }
}
