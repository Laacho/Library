package bg.tu_varna.sit.library.core.admin.approve_books;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.entities.BorrowedBooks;
import bg.tu_varna.sit.library.data.enums.BookStatus;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.BorrowedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.exceptions.BorrowBooksEmpty;
import bg.tu_varna.sit.library.models.approve_books.ApproveBooksInputModel;
import bg.tu_varna.sit.library.models.approve_books.ApproveBooksOperationModel;
import bg.tu_varna.sit.library.models.approve_books.ApproveBooksOutputModel;
import bg.tu_varna.sit.library.models.approve_books.BooksForApproveData;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Processor
public class ApproveBooksProcessor extends BaseProcessor implements ApproveBooksOperationModel {
    private final BorrowedBooksRepository borrowedBooksRepository;
    private final BookRepository bookRepository;
    private static final Logger log=Logger.getLogger(ApproveBooksProcessor.class);
    private ApproveBooksProcessor() {
        borrowedBooksRepository = SingletonFactory.getSingletonInstance(BorrowedBooksRepositoryImpl.class);
        bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }

    @Override
    public Either<Exception, ApproveBooksOutputModel> process(ApproveBooksInputModel input) {
        return Try.of(() -> {
                    log.info("Started approving books");
                    validate(input);
                    List<BorrowedBooks> borrowedBooks = borrowedBooksRepository.findAll();
                    if (borrowedBooks.isEmpty())
                        throw new BorrowBooksEmpty("No Book Borrowing Requests","There are no users who have requested to borrow books.");
                    List<BooksForApproveData> booksForApproveDataSet = getBooksForApproveData(borrowedBooks);
                    List<Book> all = bookRepository.findAll();
                    Map<String, List<Book>> booksWithSameISBN = getBooksWithSameISBN(borrowedBooks, all);
                    if(booksForApproveDataSet.isEmpty())
                        throw new BorrowBooksEmpty("No Book Borrowing Requests  ","There are no users who have requested to borrow books.");
                    ApproveBooksOutputModel output = ApproveBooksOutputModel.builder()
                            .books(booksForApproveDataSet)
                            .booksWithSameISBN(booksWithSameISBN)
                            .build();
                    log.info("Finished approving books");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    @NotNull
    private Map<String, List<Book>> getBooksWithSameISBN(List<BorrowedBooks> borrowedBooks, List<Book> all) {
        Map<String, List<Book>> booksWithSameISBN = new HashMap<>();
        for (BorrowedBooks borrowedBook : borrowedBooks) {
            for (Book book : borrowedBook.getBorrowedBooks()) {
                for (Book bookAll : all) {
                    if(!(bookAll.getInventoryNumber().equals(book.getInventoryNumber())) && book.getIsbn().equals(bookAll.getIsbn()) && bookAll.getBookStatusBeforeBorrow() == BookStatus.GOOD){
                        booksWithSameISBN.putIfAbsent(book.getIsbn(),new ArrayList<>());
                        if (!booksWithSameISBN.get(book.getIsbn()).contains(bookAll)) {
                            booksWithSameISBN.get(book.getIsbn()).add(bookAll);
                        }
                    }
                }
            }
        }
        return booksWithSameISBN;
    }

    @NotNull
    private List<BooksForApproveData> getBooksForApproveData(List<BorrowedBooks> all) {
        List<BooksForApproveData> booksForApproveDataSet = new ArrayList<>();
        for (BorrowedBooks borrowedBooks : all) {
            if (!borrowedBooks.getIsRequestApproved()) {
                BooksForApproveData convert = conversionService.convert(borrowedBooks, BooksForApproveData.class);
                booksForApproveDataSet.add(convert);
            }
        }
        return booksForApproveDataSet;
    }
}
