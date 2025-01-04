package bg.tu_varna.sit.library.core.user.search;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Genre;
import bg.tu_varna.sit.library.data.repositories.implementations.GenreRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenreRepository;
import bg.tu_varna.sit.library.models.get_all_genres.GetAllGenresInputModel;
import bg.tu_varna.sit.library.models.get_all_genres.GetAllGenresOperationModel;
import bg.tu_varna.sit.library.models.get_all_genres.GetAllGenresOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.List;

@Processor
public class GetAllGenresProcessor extends BaseProcessor implements GetAllGenresOperationModel {
    private final GenreRepository genreRepository;
    private static final Logger log = Logger.getLogger(GetAllGenresProcessor.class);

    public GetAllGenresProcessor() {
        this.genreRepository = SingletonFactory.getSingletonInstance(GenreRepositoryImpl.class);
    }

    @Override
    public Either<Exception, GetAllGenresOutputModel> process(GetAllGenresInputModel input) {
        return Try.of(()->{
                log.info("Started getting all genres");
                    validate(input);
                    List<Genre> all = genreRepository.findAll();
                    GetAllGenresOutputModel output = GetAllGenresOutputModel.builder()
                            .genres(all)
                            .build();
                    log.info("Finished getting all genres");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
}
