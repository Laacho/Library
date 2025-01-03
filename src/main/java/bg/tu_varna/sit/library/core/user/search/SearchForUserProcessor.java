package bg.tu_varna.sit.library.core.user.search;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.models.search_for_user.SearchForUserInputModel;
import bg.tu_varna.sit.library.models.search_for_user.SearchForUserOperationModel;
import bg.tu_varna.sit.library.models.search_for_user.SearchForUserOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Processor
public class SearchForUserProcessor extends BaseProcessor implements SearchForUserOperationModel {
    private final BookRepository bookRepository;
    private static final Logger log = Logger.getLogger(SearchForUserProcessor.class);

    public SearchForUserProcessor() {
        this.bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }

    @Override
    public Either<Exception, SearchForUserOutputModel> process(SearchForUserInputModel input) {
        return Try.of(() -> {
                    log.info("Started searching for a book");
                    List<Book> bookContainingText = bookRepository.findBookContainingText(input.getTitle());
                    List<Predicate<Book>> filters = new ArrayList<>();
                    if (input.getFilterAuthor() != null) {
                        filters.add(book -> book.getAuthors()
                                .stream()
                                .map(Author::toString)
                                .anyMatch(author -> author.equalsIgnoreCase(input.getFilterAuthor())));
                    }
                    if (input.getFilterGenre() != null) {
                        filters.add(book -> book.getGenre()
                                .getName()
                                .equalsIgnoreCase(input.getFilterGenre()));
                    }
                    if (input.getFilterPublisher() != null) {
                        filters.add(book -> book.getPublisher()
                                .getName()
                                .equalsIgnoreCase(input.getFilterPublisher()));
                    }
                    List<Book> result = bookContainingText.stream()    //ako nqma filti vrushta true
                            .filter(filters.stream().reduce(x -> true, Predicate::and))
                            .toList();
                    SearchForUserOutputModel output =
                            SearchForUserOutputModel.builder()
                                    .result(result)
                                    .build();
                    log.info("Finished searching for a book");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
}
