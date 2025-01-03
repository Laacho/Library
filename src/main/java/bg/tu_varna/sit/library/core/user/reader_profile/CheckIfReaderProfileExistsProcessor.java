package bg.tu_varna.sit.library.core.user.reader_profile;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.ReaderProfile;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.check_if_reader_profile_exists.CheckIfReaderProfileExistsInputModel;
import bg.tu_varna.sit.library.models.check_if_reader_profile_exists.CheckIfReaderProfileExistsOperationModel;
import bg.tu_varna.sit.library.models.check_if_reader_profile_exists.CheckIfReaderProfileExistsOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.Optional;

@Processor
public class CheckIfReaderProfileExistsProcessor extends BaseProcessor implements CheckIfReaderProfileExistsOperationModel {
    private final ReaderProfileRepository readerProfileRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private static final Logger log= Logger.getLogger(CheckIfReaderProfileExistsProcessor.class);
    private CheckIfReaderProfileExistsProcessor() {
        readerProfileRepository = SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class);
        userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, CheckIfReaderProfileExistsOutputModel> process(CheckIfReaderProfileExistsInputModel input) {
        return Try.of(() -> {
                    log.info("Started checking if reader profile exists");
                    UserCredentials userCredentials = userCredentialsRepository.findByUsername(input.getUsername()).orElseThrow(() -> new RuntimeException());
                    Optional<ReaderProfile> readerProfile = readerProfileRepository.findByUser(userCredentials.getUser());
                    boolean doesExist = false, isApproved = false;
                    if (readerProfile.isPresent()) {
                        isApproved = readerProfile.get().getIsProfileApproved();
                        doesExist = true;
                    }
                    CheckIfReaderProfileExistsOutputModel output = CheckIfReaderProfileExistsOutputModel.builder().doesExists(doesExist).isApproved(isApproved).build();
                    log.info("Finished checking if reader profile exists");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
}
