package bg.tu_varna.sit.library.core.register;

import bg.tu_varna.sit.library.common.Hasher;
import bg.tu_varna.sit.library.common.converters.common.ConversionService;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserRepository;
import bg.tu_varna.sit.library.models.ExceptionManager;
import bg.tu_varna.sit.library.models.register.RegisterInputModel;
import bg.tu_varna.sit.library.models.register.RegisterOperationModel;
import bg.tu_varna.sit.library.models.register.RegisterOutputModel;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.Optional;


public class RegisterProcessor extends BaseProcessor implements RegisterOperationModel {
    private final UserCredentialsRepository userCredentialsRepository;
    private final UserRepository userRepository;
    private final ExceptionManager exceptionManager;

    public RegisterProcessor(ConversionService conversionService,UserCredentialsRepository userCredentialsRepository, UserRepository userRepository, ExceptionManager exceptionManager) {
        super(conversionService);
        this.userCredentialsRepository = userCredentialsRepository;
        this.userRepository = userRepository;
        this.exceptionManager = exceptionManager;
    }

    @Override
    public Either<Exception, RegisterOutputModel> process(RegisterInputModel input) {
        return Try.of(() -> {
                    checkForExistingEmail(input);
                    checkForExistingUsername(input);
                    User build = conversionService.convert(input,User.class);
                    Long id = userRepository.save(build);
                    User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException());
                    UserCredentials converted = conversionService.convert(input,UserCredentials.class);
                    UserCredentials userCredentials = converted.toBuilder().user(user).build();
                    userCredentialsRepository.save(userCredentials);
                    return conversionService.convert("Successfully",RegisterOutputModel.class);
                }).toEither()
                .mapLeft(exceptionManager::handle);

    }

    private void checkForExistingUsername(RegisterInputModel input) {
        Optional<UserCredentials> searchedForExistingUsername = userCredentialsRepository.findByUsername(input.getUsername());
        if(searchedForExistingUsername.isPresent())
            throw new RuntimeException();//todo;
    }

    private void checkForExistingEmail(RegisterInputModel input) {
        Optional<UserCredentials> searchedForExistingEmail = userCredentialsRepository.findByEmail(input.getEmail());
        if(searchedForExistingEmail.isPresent())
            throw  new RuntimeException();//todo
    }
}
