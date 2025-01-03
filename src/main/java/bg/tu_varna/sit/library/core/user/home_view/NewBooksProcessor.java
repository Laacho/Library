package bg.tu_varna.sit.library.core.user.home_view;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.models.new_books.NewBooksData;
import bg.tu_varna.sit.library.models.new_books.NewBooksInputModel;
import bg.tu_varna.sit.library.models.new_books.NewBooksOperationModel;
import bg.tu_varna.sit.library.models.new_books.NewBooksOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Processor
public class NewBooksProcessor extends BaseProcessor implements NewBooksOperationModel {
    private final BookRepository bookRepository;
    private static final Logger log= Logger.getLogger(NewBooksProcessor.class);
    private NewBooksProcessor() {
        bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }

    @Override
    public Either<Exception, NewBooksOutputModel> process(NewBooksInputModel input) {
        return Try.of(() -> {
                    log.info("Started new book process");
                    List<Book> allGoodBooks = bookRepository.findAllGoodBooks();
                    List<NewBooksData> newBooksData = getNewBooksData(allGoodBooks);
                    NewBooksOutputModel output = NewBooksOutputModel.builder().newBooksData(newBooksData).build();
                    log.info("Finished new book process");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    @NotNull
    private List<NewBooksData> getNewBooksData(List<Book> allGoodBooks) {
        List<NewBooksData> newBooksData = new ArrayList<>();
        for (Book allGoodBook : allGoodBooks) {
            newBooksData.add(conversionService.convert(allGoodBook, NewBooksData.class));
        }
        return newBooksData;
    }
}
