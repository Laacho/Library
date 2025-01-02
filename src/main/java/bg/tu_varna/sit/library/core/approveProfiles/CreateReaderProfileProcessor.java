package bg.tu_varna.sit.library.core.approveProfiles;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.ReaderProfile;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.implementations.UserRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserRepository;
import bg.tu_varna.sit.library.models.create_reader_profile.CreateReaderProfileInputModel;
import bg.tu_varna.sit.library.models.create_reader_profile.CreateReaderProfileOperationModel;
import bg.tu_varna.sit.library.models.create_reader_profile.CreateReaderProfileOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.time.LocalDateTime;

@Processor
public class CreateReaderProfileProcessor extends BaseProcessor implements CreateReaderProfileOperationModel {
    private final ReaderProfileRepository readerProfileRepository;
    private final UserRepository userRepository;

    private CreateReaderProfileProcessor() {
        readerProfileRepository = SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class);
        userRepository = SingletonFactory.getSingletonInstance(UserRepositoryImpl.class);
    }

    @Override
    public Either<Exception, CreateReaderProfileOutputModel> process(CreateReaderProfileInputModel input) {
        return Try.of(() -> {
                    User user = userRepository.findById(input.getId()).orElseThrow(() -> new RuntimeException());//todo
                    ReaderProfile readerProfile = readerProfileRepository.findByUser(user).orElseThrow(() -> new RuntimeException());
                    readerProfile = readerProfile.toBuilder()
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .isProfileApproved(true)
                            .user(user)
                            .build();
                    readerProfileRepository.update(readerProfile);
                    return CreateReaderProfileOutputModel.builder().message("Successfully created reader profile.").build();
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
}
