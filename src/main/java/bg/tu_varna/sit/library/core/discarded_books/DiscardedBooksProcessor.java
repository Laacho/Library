package bg.tu_varna.sit.library.core.discarded_books;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.DiscardedBooks;
import bg.tu_varna.sit.library.data.repositories.implementations.DiscardedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.DiscardedBooksRepository;
import bg.tu_varna.sit.library.models.discarded_books.BooksData;
import bg.tu_varna.sit.library.models.discarded_books.DiscardedBooksInputModel;
import bg.tu_varna.sit.library.models.discarded_books.DiscardedBooksOperationModel;
import bg.tu_varna.sit.library.models.discarded_books.DiscardedBooksOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Processor
public class DiscardedBooksProcessor extends BaseProcessor implements DiscardedBooksOperationModel {
    private final DiscardedBooksRepository discardedBooksRepository;

    private DiscardedBooksProcessor() {
        discardedBooksRepository = SingletonFactory.getSingletonInstance(DiscardedBooksRepositoryImpl.class);
    }

    @Override
    public Either<Exception, DiscardedBooksOutputModel> process(DiscardedBooksInputModel input) {
        return Try.of(() -> {
                    List<DiscardedBooks> books = discardedBooksRepository.findAll();
                    List<BooksData> booksData = getBooksDataList(books);
                    DiscardedBooksOutputModel output = DiscardedBooksOutputModel.builder().booksData(booksData).build();
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }
    @NotNull
    private List<BooksData> getBooksDataList(List<DiscardedBooks> books) {
        List<BooksData> booksData = new ArrayList<>();
        for (DiscardedBooks book : books) {
            booksData.add(conversionService.convert(book, BooksData.class));
        }
        return booksData;
    }
}
