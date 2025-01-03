package bg.tu_varna.sit.library.core.check_if_code_matches;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.exceptions.IncorrectVerificationCode;
import bg.tu_varna.sit.library.exceptions.UsernameDoesNotExist;
import bg.tu_varna.sit.library.models.check_if_codes_matches.CheckIfCodesMatchesInputModel;
import bg.tu_varna.sit.library.models.check_if_codes_matches.CheckIfCodesMatchesOperationModel;
import bg.tu_varna.sit.library.models.check_if_codes_matches.CheckIfCodesMatchesOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

@Processor
public class CheckIfCodesMatchesProcessor extends BaseProcessor implements CheckIfCodesMatchesOperationModel {
    private final UserCredentialsRepository userCredentialsRepository;
    private static final Logger log = Logger.getLogger(CheckIfCodesMatchesProcessor.class);

    public CheckIfCodesMatchesProcessor() {
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, CheckIfCodesMatchesOutputModel> process(CheckIfCodesMatchesInputModel input) {
        return Try.of(() -> {
                    log.info("Started checking if codes match!");
                    String inputVerificationCode = input.getInputVerificationCode();
                    UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
                    if (!userSession.getNewEmailVerificationCode().equals(inputVerificationCode)) {
                        throw new IncorrectVerificationCode("Verification Code Incorrect", "New email verification code is incorrect");
                    }
                    //change email
                    UserCredentials userCredentials = userCredentialsRepository.findByUsername(userSession.getUsername())
                            .orElseThrow(() -> new UsernameDoesNotExist("Username Not Found", "User with username: " + userSession.getUsername() + " has not been found"));
                    userCredentials.setEmail(input.getNewEmail());
                    userCredentialsRepository.update(userCredentials);
                    CheckIfCodesMatchesOutputModel output = buildOutput();
                    log.info("Finished checking if codes match!");
                    return output;
                })
                .toEither()
                .mapLeft(exceptionManager::handle);
    }

    private CheckIfCodesMatchesOutputModel buildOutput() {
        return CheckIfCodesMatchesOutputModel.builder().message("Successfully changed email!").build();
    }
}
