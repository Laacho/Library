package bg.tu_varna.sit.library.core.admin.approve_profiles;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.exceptions.UsernameDoesNotExist;
import bg.tu_varna.sit.library.models.approve_profiles.ApproveProfilesInputModel;
import bg.tu_varna.sit.library.models.approve_profiles.ApproveProfilesOperationModel;
import bg.tu_varna.sit.library.models.approve_profiles.ApproveProfilesOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

@Processor
public class ApproveProfilesProcessor extends BaseProcessor implements ApproveProfilesOperationModel {
    private final UserCredentialsRepository userCredentialsRepository;
    private static final Logger log= Logger.getLogger(ApproveProfilesProcessor.class);
    private ApproveProfilesProcessor() {
        userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, ApproveProfilesOutputModel> process(ApproveProfilesInputModel input) {
        return Try.of(() -> {
                    log.info("Started approving profiles");
                    String username = input.getUsername();
                    UserCredentials userCredentials = userCredentialsRepository.findByUsername(username).orElseThrow(() -> new UsernameDoesNotExist("Username Not Found","The username you provided does not exist in our records."));
                    ApproveProfilesOutputModel output = conversionService.convert(userCredentials.getUser(), ApproveProfilesOutputModel.class);
                    log.info("Finished approving profiles");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
}
