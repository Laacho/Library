package bg.tu_varna.sit.library.core.admin.return_books;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.*;
import bg.tu_varna.sit.library.data.repositories.implementations.BorrowedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.NotificationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserRepository;
import bg.tu_varna.sit.library.exceptions.UserNotFound;
import bg.tu_varna.sit.library.exceptions.UserWithIdDoesNotExist;
import bg.tu_varna.sit.library.models.return_books.BooksForReturn;
import bg.tu_varna.sit.library.models.update_returned_books.UpdateReturnedBooksInputModel;
import bg.tu_varna.sit.library.models.update_returned_books.UpdateReturnedBooksOperationModel;
import bg.tu_varna.sit.library.models.update_returned_books.UpdateReturnedBooksOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Processor
public class UpdateReturnedBooksProcessor extends BaseProcessor implements UpdateReturnedBooksOperationModel {
    private final NotificationRepository notificationRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final UserRepository userRepository;
    private final BorrowedBooksRepository borrowedBooksRepository;
    private static final Logger log= Logger.getLogger(UpdateReturnedBooksProcessor.class);
    private UpdateReturnedBooksProcessor() {
        notificationRepository = SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class);
        userCredentialsRepository = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
        borrowedBooksRepository = SingletonFactory.getSingletonInstance(BorrowedBooksRepositoryImpl.class);
        userRepository = SingletonFactory.getSingletonInstance(UserRepositoryImpl.class);
    }

    @Override
    public Either<Exception, UpdateReturnedBooksOutputModel> process(UpdateReturnedBooksInputModel input) {
        return Try.of(() -> {
                    log.info("Started updating returned books");
                    Long userId = input.getUserId();
                    BooksForReturn books = input.getBooks();
                    User user = userRepository.findById(userId).orElseThrow(() -> new UserWithIdDoesNotExist("User Not Found","User with id "+userId+" has not been found"));
                    BorrowedBooks borrowedBooks = borrowedBooksRepository.findByUserAndBorrowedDateAndReturnDate(user, books.getBorrowingDate(), books.getReturnDate()).orElseThrow(() -> new RuntimeException());//todo
                    double mistakes = 0;
                    mistakes = getMistakes(books, mistakes);
                    mistakes = checkForDeadline(books, mistakes);
                    mistakes /= 10;
                    Double rating = getNewRating(user, mistakes);
                    UserCredentials userCredentials = userCredentialsRepository.findByUser(user)
                            .orElseThrow(() -> new UserNotFound("User Not Found","User has not been found"));//todo
                    userCredentials.setRating(rating);
                    userCredentialsRepository.update(userCredentials);
                    borrowedBooksRepository.deleteById(borrowedBooks.getId());
                    UpdateReturnedBooksOutputModel output = outputBuilder();
                    log.info("Finished updating returned books");
                    return output;
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private  UpdateReturnedBooksOutputModel outputBuilder() {
        return UpdateReturnedBooksOutputModel.builder()
                .message("Successfully updated borrowed books")
                .build();
    }

    private double checkForDeadline(BooksForReturn books, double mistakes) {
        if (books.getDeadline().isAfter(LocalDate.now())) {
            mistakes--;
        } else if (books.getDeadline().isBefore(LocalDate.now())) {
            mistakes++;
        }
        return mistakes;
    }

    @NotNull
    private static Double getNewRating(User user, double mistakes) {
        Double rating = user.getUserCredentials().getRating();
        if (rating == null) {
            rating = 5.0;
        }
        rating -= mistakes;
        if (rating > 5.0) {
            rating = 5.0;
        }
        return rating;
    }

    private double getMistakes(BooksForReturn books, double mistakes) {
        for (Book book : books.getBooks()) {
            if (book.getBookStatusBeforeBorrow() != book.getBookStatusAfterBorrow()) {
                sendNotification(book);
                mistakes++;
            }
        }
        return mistakes;
    }

    private void sendNotification(Book book) {
        Notification notification = Notification.builder()
                .message("Book with inventory number: " + book.getInventoryNumber() + System.lineSeparator() +
                        "status should be changed from " + book.getBookStatusBeforeBorrow()
                        + " to " + book.getBookStatusAfterBorrow() + System.lineSeparator())
                .isRead(false)
                .isAdmin(true)
                .build();
        notificationRepository.save(notification);
    }
}
