package bg.tu_varna.sit.library.core.overdue_books;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.BorrowedBooks;
import bg.tu_varna.sit.library.data.repositories.implementations.BorrowedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.models.overdue_books.OverdueBooks;
import bg.tu_varna.sit.library.models.overdue_books.OverdueBooksInputModel;
import bg.tu_varna.sit.library.models.overdue_books.OverdueBooksOperationModel;
import bg.tu_varna.sit.library.models.overdue_books.OverdueBooksOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Processor
public class OverdueBooksProcessor extends BaseProcessor implements OverdueBooksOperationModel {
    private final BorrowedBooksRepository borrowedBooksRepository;

    private OverdueBooksProcessor() {
        borrowedBooksRepository = SingletonFactory.getSingletonInstance(BorrowedBooksRepositoryImpl.class);
    }

    @Override
    public Either<Exception, OverdueBooksOutputModel> process(OverdueBooksInputModel input) {
        return Try.of(() -> {
                    List<BorrowedBooks> all = borrowedBooksRepository.findAll();
                    if (all.isEmpty()) throw new RuntimeException();//todo
                    List<OverdueBooks> overdueBooks = getOverdueBooks(all);
                    return OverdueBooksOutputModel.builder().overdueBooks(overdueBooks).build();
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
