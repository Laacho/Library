package bg.tu_varna.sit.library.core.return_books;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.BorrowedBooks;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.BorrowedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserRepository;
import bg.tu_varna.sit.library.models.return_books.BooksForReturn;
import bg.tu_varna.sit.library.models.return_books.ReturnBooksInputModel;
import bg.tu_varna.sit.library.models.return_books.ReturnBooksOperationModel;
import bg.tu_varna.sit.library.models.return_books.ReturnBooksOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Processor
public class ReturnBookProcessor extends BaseProcessor implements ReturnBooksOperationModel {
    private final BorrowedBooksRepository borrowedBooksRepository;
    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;

    private ReturnBookProcessor() {
        borrowedBooksRepository = SingletonFactory.getSingletonInstance(BorrowedBooksRepositoryImpl.class);
        userRepository = SingletonFactory.getSingletonInstance(UserRepositoryImpl.class);
        userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
    }

    @Override
    public Either<Exception, ReturnBooksOutputModel> process(ReturnBooksInputModel input) {
        return Try.of(() -> {
                    UserCredentials userCredentials = userCredentialsRepository.findByUsername(input.getUsername()).orElseThrow(() -> new RuntimeException());
                    User user = userCredentials.getUser();
                    List<BorrowedBooks> borrowedBooks = borrowedBooksRepository.findByUser(user);
                    List<BooksForReturn> booksForReturns = getBooksForReturns(borrowedBooks);
                    return ReturnBooksOutputModel.builder().booksForReturns(booksForReturns).build();
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
