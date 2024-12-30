package bg.tu_varna.sit.library.core.confirm_registration;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.confirm_registration.ConfirmRegistrationInputModel;
import bg.tu_varna.sit.library.models.confirm_registration.ConfirmRegistrationOperationModel;
import bg.tu_varna.sit.library.models.confirm_registration.ConfirmRegistrationOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.time.LocalDate;

@Processor
public class ConfirmRegistrationProcessor extends BaseProcessor implements ConfirmRegistrationOperationModel {
    private final UserCredentialsRepository userCredentialsRepository;
    private static final Logger log= Logger.getLogger(ConfirmRegistrationProcessor.class);
    private ConfirmRegistrationProcessor() {
        super();
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, ConfirmRegistrationOutputModel> process(ConfirmRegistrationInputModel input) {
        return Try.of(() -> {
                    log.info("Started confirm registration");
                    UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
                    String verificationCode = input.getVerificationCode();
                    UserCredentials userCredentials = userCredentialsRepository.findByUsername(userSession.getUsername()).orElseThrow(() -> new RuntimeException());
                    String verificationCodeFromDB = userCredentials.getVerificationCode();
                    checkVerificationCodes(verificationCodeFromDB, verificationCode, userCredentials);
                    userSession.setVerified(true);
                    log.info("Finished confirm registration");
                    return ConfirmRegistrationOutputModel.builder().build();
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private void checkVerificationCodes(String verificationCodeFromDB, String verificationCode, UserCredentials userCredentials) {
        if (verificationCodeFromDB.equals(verificationCode)) {
            UserCredentials built = userCredentials.toBuilder()
                    .verified(true)
                    .dateOfVerification(LocalDate.now())
                    .build();
            userCredentialsRepository.update(built);
            return;
        }
        //todo
        throw new RuntimeException();
    }
}
