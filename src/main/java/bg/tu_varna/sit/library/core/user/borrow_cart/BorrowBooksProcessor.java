package bg.tu_varna.sit.library.core.user.borrow_cart;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.entities.BorrowedBooks;
import bg.tu_varna.sit.library.data.entities.Notification;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.BorrowedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.NotificationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.exceptions.UsernameDoesNotExist;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.borrow_books.BorrowBooksInputModel;
import bg.tu_varna.sit.library.models.borrow_books.BorrowBooksOperationModel;
import bg.tu_varna.sit.library.models.borrow_books.BorrowBooksOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Processor
public class BorrowBooksProcessor extends BaseProcessor implements BorrowBooksOperationModel {
    private final UserCredentialsRepository userRepository;
    private final BorrowedBooksRepository borrowedBooksRepository;
    private final NotificationRepository notificationRepository;
    private final BookRepository bookRepository;
    private static final Logger log= Logger.getLogger(BorrowBooksProcessor.class);
    private BorrowBooksProcessor() {
        userRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
        borrowedBooksRepository = SingletonFactory.getSingletonInstance(BorrowedBooksRepositoryImpl.class);
        notificationRepository = SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class);
        bookRepository = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
    }

    @Override
    public Either<Exception, BorrowBooksOutputModel> process(BorrowBooksInputModel input) {
        return Try.of(() -> {
                    log.info("Started borrowing book process");
                    validate(input);
                    User user = userRepository.findByUsername(input.getUsername())
                            .orElseThrow(() -> new UsernameDoesNotExist("Username Not Found","User with username: " +input.getUsername()+" has not been found"))
                            .getUser();
                    List<Book> books = bookRepository.findAll();
                    Set<Book> booksForBorrow = getBooksForBorrow(input, books);
                    BorrowedBooks convert = conversionService.convert(input, BorrowedBooks.class);
                    convert.setUser(user);
                    convert.setBorrowedBooks(booksForBorrow);
                    borrowedBooksRepository.save(convert);
                    notificationRepository.save(Notification.builder()
                            .isAdmin(true)
                            .message("User " + user.getUserCredentials().getUsername() + " wants to borrow books. Please check the request")
                            .isRead(false)
                            .build());
                    BorrowBooksOutputModel output =
                            outputBuilder();
                    log.info("Finished borrowing book process");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private static BorrowBooksOutputModel outputBuilder() {
        return BorrowBooksOutputModel.builder().message("Successfully send request").build();
    }

    @NotNull
    private Set<Book> getBooksForBorrow(BorrowBooksInputModel input, List<Book> books) {
        Set<Book> booksForBorrow = new HashSet<>();
        for (CommonBooksProperties book : input.getBooks()) {
            for (Book b : books) {
                if (book.getInventoryNumber().equals(b.getInventoryNumber())) {
                    booksForBorrow.add(b);
                }
            }
        }
        return booksForBorrow;
    }
}
