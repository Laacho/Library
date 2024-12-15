package bg.tu_varna.sit.library.core.search;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.models.ExceptionManager;
import bg.tu_varna.sit.library.models.search.SearchInputModel;
import bg.tu_varna.sit.library.models.search.SearchOperationModel;
import bg.tu_varna.sit.library.models.search.SearchOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.List;

@Processor
public class SearchProcessor extends BaseProcessor implements SearchOperationModel {
    private final BookRepository bookRepository;
    private final ExceptionManager exceptionManager;
    private static final Logger log = Logger.getLogger(SearchProcessor.class);


    private SearchProcessor() {
        this.exceptionManager = SingletonFactory.getSingletonInstance(ExceptionManager.class);
        this.bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }

    @Override
    public Either<Exception, SearchOutputModel> process(SearchInputModel input) {
        return Try.of(() -> {
                    log.info("Started searching books");
                    List<Book> result = bookRepository.findBookContainingText(input.getText());
                    SearchOutputModel foundBooks = SearchOutputModel.builder()
                            .books(result)
                            .build();
                    log.info("Finished searching books");
                    return foundBooks;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
}
