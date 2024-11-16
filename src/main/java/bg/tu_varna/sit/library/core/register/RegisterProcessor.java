package bg.tu_varna.sit.library.core.register;

import bg.tu_varna.sit.library.utils.EmailService;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserRepository;
import bg.tu_varna.sit.library.models.ExceptionManager;
import bg.tu_varna.sit.library.models.register.RegisterInputModel;
import bg.tu_varna.sit.library.models.register.RegisterOperationModel;
import bg.tu_varna.sit.library.models.register.RegisterOutputModel;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.Optional;


@Processor
public class RegisterProcessor extends BaseProcessor implements RegisterOperationModel {
    private final UserCredentialsRepository userCredentialsRepository;
    private final UserRepository userRepository;
    private final ExceptionManager exceptionManager;


    private RegisterProcessor() {
        super();
        this.userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
        this.userRepository = SingletonFactory.getSingletonInstance(UserRepositoryImpl.class);
        this.exceptionManager = SingletonFactory.getSingletonInstance(ExceptionManager.class);
        ;
    }

    @Override
    public Either<Exception, RegisterOutputModel> process(RegisterInputModel input) {
        return Try.of(() -> {
                    checkForExistingEmail(input);
                    checkForExistingUsername(input);
                    User build = conversionService.convert(input, User.class);
                    Long id = userRepository.save(build);
                    User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException());
                    UserCredentials converted = conversionService.convert(input, UserCredentials.class);
                    String verificationCode = EmailService.generateVerificationCode();
                    UserCredentials userCredentials = buildUserCredentialsWithUserAndVerificationCode(converted, user, verificationCode);
                    userCredentialsRepository.save(userCredentials);
                    EmailService.sendMail(userCredentials.getEmail(), verificationCode);
                    return conversionService.convert("Successfully", RegisterOutputModel.class);
                }).toEither()
                .mapLeft(exceptionManager::handle);

    }

    private UserCredentials buildUserCredentialsWithUserAndVerificationCode(UserCredentials converted, User user, String verificationCode) {
        UserCredentials userCredentials = converted.toBuilder().
                user(user).
                verificationCode(verificationCode).
                build();
        return userCredentials;
    }

    private void checkForExistingUsername(RegisterInputModel input) {
        Optional<UserCredentials> searchedForExistingUsername = userCredentialsRepository.findByUsername(input.getUsername());
        if (searchedForExistingUsername.isPresent())
            throw new RuntimeException();//todo;
    }

    private void checkForExistingEmail(RegisterInputModel input) {
        Optional<UserCredentials> searchedForExistingEmail = userCredentialsRepository.findByEmail(input.getEmail());
        if (searchedForExistingEmail.isPresent())
            throw new RuntimeException();//todo
    }
}
