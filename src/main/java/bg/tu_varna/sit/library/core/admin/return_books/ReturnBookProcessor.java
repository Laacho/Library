package bg.tu_varna.sit.library.core.admin.return_books;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.BorrowedBooks;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.BorrowedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.exceptions.UsernameDoesNotExist;
import bg.tu_varna.sit.library.models.return_books.BooksForReturn;
import bg.tu_varna.sit.library.models.return_books.ReturnBooksInputModel;
import bg.tu_varna.sit.library.models.return_books.ReturnBooksOperationModel;
import bg.tu_varna.sit.library.models.return_books.ReturnBooksOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Processor
public class ReturnBookProcessor extends BaseProcessor implements ReturnBooksOperationModel {
    private final BorrowedBooksRepository borrowedBooksRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private static final Logger log= Logger.getLogger(ReturnBookProcessor.class);
    private ReturnBookProcessor() {
        borrowedBooksRepository = SingletonFactory.getSingletonInstance(BorrowedBooksRepositoryImpl.class);
        userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, ReturnBooksOutputModel> process(ReturnBooksInputModel input) {
        return Try.of(() -> {
                    log.info("Started returning books");
                    validate(input);
                    UserCredentials userCredentials = userCredentialsRepository.findByUsername(input.getUsername())
                            .orElseThrow(() -> new UsernameDoesNotExist("Username Not Found","User with username: " +input.getUsername()+" has not been found"));
                    User user = userCredentials.getUser();
                    List<BorrowedBooks> borrowedBooks = borrowedBooksRepository.findByUser(user);
                    List<BooksForReturn> booksForReturns = getBooksForReturns(borrowedBooks);
                    ReturnBooksOutputModel output = ReturnBooksOutputModel.builder()
                            .booksForReturns(booksForReturns)
                            .userId(user.getId())
                            .build();
                    log.info("Finished returning books");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    @NotNull
    private List<BooksForReturn> getBooksForReturns(List<BorrowedBooks> borrowedBooks) {
        List<BooksForReturn> booksForReturns = new ArrayList<>();
        for (BorrowedBooks borrowedBook : borrowedBooks) {
            booksForReturns.add(conversionService.convert(borrowedBook,BooksForReturn.class));
        }
        return booksForReturns;
    }
}
