package bg.tu_varna.sit.library.core.admin.settings;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.demote_user.DemoteUserInputModel;
import bg.tu_varna.sit.library.models.demote_user.DemoteUserOperationModel;
import bg.tu_varna.sit.library.models.demote_user.DemoteUserOutputModel;
import bg.tu_varna.sit.library.utils.EmailService;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.concurrent.CompletableFuture;

@Processor
public class DemoteUserProcessor extends BaseProcessor implements DemoteUserOperationModel {
    private final static Logger log = Logger.getLogger(DemoteUserProcessor.class);
    private final UserCredentialsRepository userCredentialsRepository;

    public DemoteUserProcessor( ) {
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, DemoteUserOutputModel> process(DemoteUserInputModel input) {
        return Try.of(()->{
            log.info("Started demoting user");
                    validate(input);
                    UserCredentials userCredentials = input.getUserCredentials();
                    userCredentials.setAdmin(false);
                    userCredentialsRepository.update(userCredentials);
                    CompletableFuture.runAsync(()->EmailService.sendContactMail(userCredentials.getEmail(),"Demoted!","You have been demoted!"));
                    DemoteUserOutputModel userSuccessfullyDemoted = outputBuilder();
                    log.info(userSuccessfullyDemoted.getMessage());
                    return userSuccessfullyDemoted;
                })
                .toEither()
                .mapLeft(exceptionManager::handle);
    }

    private DemoteUserOutputModel outputBuilder() {
        return DemoteUserOutputModel.builder()
                .message("User successfully demoted")
                .build();
    }

}
