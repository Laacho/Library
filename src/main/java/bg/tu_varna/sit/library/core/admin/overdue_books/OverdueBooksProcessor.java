package bg.tu_varna.sit.library.core.admin.overdue_books;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.BorrowedBooks;
import bg.tu_varna.sit.library.data.repositories.implementations.BorrowedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.exceptions.NoBooksPresent;
import bg.tu_varna.sit.library.models.overdue_books.OverdueBooks;
import bg.tu_varna.sit.library.models.overdue_books.OverdueBooksInputModel;
import bg.tu_varna.sit.library.models.overdue_books.OverdueBooksOperationModel;
import bg.tu_varna.sit.library.models.overdue_books.OverdueBooksOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Processor
public class OverdueBooksProcessor extends BaseProcessor implements OverdueBooksOperationModel {
    private final BorrowedBooksRepository borrowedBooksRepository;
    private static final Logger log= Logger.getLogger(OverdueBooksProcessor.class);
    private OverdueBooksProcessor() {
        borrowedBooksRepository = SingletonFactory.getSingletonInstance(BorrowedBooksRepositoryImpl.class);
    }

    @Override
    public Either<Exception, OverdueBooksOutputModel> process(OverdueBooksInputModel input) {
        return Try.of(() -> {
                    log.info("Started overdue books");
                    validate(input);
                    List<BorrowedBooks> all = borrowedBooksRepository.findAll();
                    List<OverdueBooks> overdueBooks = getOverdueBooks(all);
                    if (overdueBooks.isEmpty()) throw new NoBooksPresent("Not Found Borrowed Books","Does not have borrowed books in the database");
                    OverdueBooksOutputModel output = OverdueBooksOutputModel.builder().overdueBooks(overdueBooks).build();
                    log.info("Finished overdue books");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    @NotNull
    private List<OverdueBooks> getOverdueBooks(List<BorrowedBooks> all) {
        List<OverdueBooks> overdueBooks = new ArrayList<>();
        for (BorrowedBooks borrowedBooks : all) {
            if (borrowedBooks.getReturnDeadline().isBefore(LocalDate.now())) {
                overdueBooks.add(conversionService.convert(borrowedBooks,OverdueBooks.class));
            }
        }
        return overdueBooks;
    }
}
