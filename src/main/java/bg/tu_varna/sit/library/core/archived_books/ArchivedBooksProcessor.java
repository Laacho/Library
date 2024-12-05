package bg.tu_varna.sit.library.core.archived_books;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Archived;
import bg.tu_varna.sit.library.data.repositories.implementations.ArchivedRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.ArchivedRepository;
import bg.tu_varna.sit.library.models.archived_books.ArchivedBooksInputModel;
import bg.tu_varna.sit.library.models.archived_books.ArchivedBooksOperationModel;
import bg.tu_varna.sit.library.models.archived_books.ArchivedBooksOutputModel;
import bg.tu_varna.sit.library.models.archived_books.BooksData;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.jetbrains.annotations.NotNull;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Processor
public class ArchivedBooksProcessor extends BaseProcessor implements ArchivedBooksOperationModel {
    private final ArchivedRepository archivedRepository;

    private ArchivedBooksProcessor() {
        archivedRepository = SingletonFactory.getSingletonInstance(ArchivedRepositoryImpl.class);
    }

    @Override
    public Either<Exception, ArchivedBooksOutputModel> process(ArchivedBooksInputModel input) {
        return Try.of(() -> {
                    List<Archived> books = archivedRepository.findAll();
                    List<BooksData> booksData = getBooksDataList(books);
                    ArchivedBooksOutputModel output = ArchivedBooksOutputModel.builder().booksData(booksData).build();
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    @NotNull
    private List<BooksData> getBooksDataList(List<Archived> books) {
        List<BooksData> booksData = new ArrayList<>();
        for (Archived book : books) {
            booksData.add(conversionService.convert(book, BooksData.class));
        }
        return booksData;
    }

}
