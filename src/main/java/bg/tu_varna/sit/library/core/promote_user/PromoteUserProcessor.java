package bg.tu_varna.sit.library.core.promote_user;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.promote_user.PromoteUserInputModel;
import bg.tu_varna.sit.library.models.promote_user.PromoteUserOperationModel;
import bg.tu_varna.sit.library.models.promote_user.PromoteUserOutputModel;
import bg.tu_varna.sit.library.utils.EmailService;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.concurrent.CompletableFuture;

@Processor
public class PromoteUserProcessor extends BaseProcessor implements PromoteUserOperationModel {
    private static final Logger log = Logger.getLogger(PromoteUserProcessor.class);
    private final UserCredentialsRepository userCredentialsRepository;

    private PromoteUserProcessor( ) {
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, PromoteUserOutputModel> process(PromoteUserInputModel input) {
        return Try.of(()->{
                    log.info("Started promoting user");
                    UserCredentials userCredentials = input.getUserCredentials();
                    userCredentials.setAdmin(true);
                    userCredentialsRepository.update(userCredentials);
                    CompletableFuture.runAsync(()-> EmailService.sendContactMail(userCredentials.getEmail(),"Promoted!","You have been promoted!"));
                    PromoteUserOutputModel outputModel = outputBuilder();
                    log.info(outputModel.getMessage());
                    return outputModel;
                })
                .toEither()
                .mapLeft(exceptionManager::handle);
    }

    private PromoteUserOutputModel outputBuilder() {
        return PromoteUserOutputModel.builder().message("User successfully promoted").build();
    }
}
