package bg.tu_varna.sit.library.core.approveProfiles;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserRepository;
import bg.tu_varna.sit.library.models.approve_books.ApproveBooksInputModel;
import bg.tu_varna.sit.library.models.approve_books.ApproveBooksOperationModel;
import bg.tu_varna.sit.library.models.approve_books.ApproveBooksOutputModel;
import bg.tu_varna.sit.library.models.approve_profiles.ApproveProfilesInputModel;
import bg.tu_varna.sit.library.models.approve_profiles.ApproveProfilesOperationModel;
import bg.tu_varna.sit.library.models.approve_profiles.ApproveProfilesOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;

@Processor
public class ApproveProfilesProcessor extends BaseProcessor implements ApproveProfilesOperationModel {
    private final UserCredentialsRepository userCredentialsRepository;

    private ApproveProfilesProcessor() {
        userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, ApproveProfilesOutputModel> process(ApproveProfilesInputModel input) {
        return Try.of(() -> {
                    String username = input.getUsername();
                    UserCredentials userCredentials = userCredentialsRepository.findByUsername(username).orElseThrow(() -> new RuntimeException());//todo
                   return conversionService.convert(userCredentials.getUser(),ApproveProfilesOutputModel.class);
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
}
