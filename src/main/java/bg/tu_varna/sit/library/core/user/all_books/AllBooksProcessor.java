package bg.tu_varna.sit.library.core.user.all_books;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.models.all_books.AllBooksInputModel;
import bg.tu_varna.sit.library.models.all_books.AllBooksOperationModel;
import bg.tu_varna.sit.library.models.all_books.AllBooksOutputModel;
import bg.tu_varna.sit.library.models.all_books.BooksData;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Processor
public class AllBooksProcessor extends BaseProcessor implements AllBooksOperationModel {
    private final BookRepository bookRepository;
    private static final Logger log = Logger.getLogger(AllBooksProcessor.class);
    public AllBooksProcessor() {
        bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }

    @Override
    public Either<Exception, AllBooksOutputModel> process(AllBooksInputModel input) {
        return Try.of(() -> {
                    log.info("Started processing all books");
                    validate(input);
                    List<Book> books = bookRepository.findAll();
                    List<BooksData> booksData = getBooksDataList(books);
                    AllBooksOutputModel output = AllBooksOutputModel.builder().booksData(booksData).build();
                    log.info("Finished processing all books");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    @NotNull
    private List<BooksData> getBooksDataList(List<Book> books) {
        List<BooksData> booksData = new ArrayList<>();
        for (Book book : books) {
            booksData.add(conversionService.convert(book, BooksData.class));
        }
        return booksData;
    }
}
