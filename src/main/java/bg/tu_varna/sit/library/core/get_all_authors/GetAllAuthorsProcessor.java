package bg.tu_varna.sit.library.core.get_all_authors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.data.repositories.implementations.AuthorRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.AuthorRepository;
import bg.tu_varna.sit.library.models.get_all_authors.GetAllAuthorsInputModel;
import bg.tu_varna.sit.library.models.get_all_authors.GetAllAuthorsOperationModel;
import bg.tu_varna.sit.library.models.get_all_authors.GetAllAuthorsOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.List;

@Processor
public class GetAllAuthorsProcessor extends BaseProcessor implements GetAllAuthorsOperationModel {
    private final AuthorRepository authorRepository;
    private static final Logger log = Logger.getLogger(GetAllAuthorsProcessor.class);


    public GetAllAuthorsProcessor() {
        this.authorRepository = SingletonFactory.getSingletonInstance(AuthorRepositoryImpl.class);
    }

    @Override
    public Either<Exception, GetAllAuthorsOutputModel> process(GetAllAuthorsInputModel input) {
        return Try.of(()->{
                log.info("Started getting all authors");
                    List<Author> all = authorRepository.findAll();
                    GetAllAuthorsOutputModel output = GetAllAuthorsOutputModel.builder()
                            .authors(all)
                            .build();
                    log.info("Finished getting all authors");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
}
