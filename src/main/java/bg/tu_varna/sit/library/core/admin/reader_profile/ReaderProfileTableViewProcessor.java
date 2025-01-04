package bg.tu_varna.sit.library.core.admin.reader_profile;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.ReaderProfile;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.models.reader_profile_table_view.ReaderProfileData;
import bg.tu_varna.sit.library.models.reader_profile_table_view.ReaderProfileTableViewInputModel;
import bg.tu_varna.sit.library.models.reader_profile_table_view.ReaderProfileTableViewOperationModel;
import bg.tu_varna.sit.library.models.reader_profile_table_view.ReaderProfileTableViewOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Processor
public class ReaderProfileTableViewProcessor extends BaseProcessor implements ReaderProfileTableViewOperationModel {
    private final ReaderProfileRepository readerProfileRepository;
    private static final Logger log= Logger.getLogger(ReaderProfileTableViewProcessor.class);
    private ReaderProfileTableViewProcessor() {
        readerProfileRepository = SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class);
    }

    @Override
    public Either<Exception, ReaderProfileTableViewOutputModel> process(ReaderProfileTableViewInputModel input) {
        return Try.of(() -> {
                log.info("Started reader profile table view");
                    validate(input);
                    List<ReaderProfile> all = readerProfileRepository.findAll();
                    List<ReaderProfileData> readerProfileData = getReaderProfileData(all);
                    ReaderProfileTableViewOutputModel output = ReaderProfileTableViewOutputModel.builder()
                            .readerProfileData(readerProfileData)
                            .build();
                    log.info("Finished reader profile table view");
                  return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    @NotNull
    private List<ReaderProfileData> getReaderProfileData(List<ReaderProfile> all) {
        List<ReaderProfileData> readerProfileData = new ArrayList<>();
        for (ReaderProfile readerProfile : all) {
            readerProfileData.add(conversionService.convert(readerProfile,ReaderProfileData.class));
        }
        return readerProfileData;
    }
}
