package bg.tu_varna.sit.library.core.approve_books;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.data.entities.*;
import bg.tu_varna.sit.library.data.repositories.implementations.BorrowedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.NotificationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.models.approve_books.BooksForApproveData;
import bg.tu_varna.sit.library.models.update_borrowed_books.UpdateBorrowedBooksInputModel;
import bg.tu_varna.sit.library.models.update_borrowed_books.UpdateBorrowedBooksOperationModel;
import bg.tu_varna.sit.library.models.update_borrowed_books.UpdateBorrowedBooksOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Processor
public class UpdateBorrowedBooksProcessor extends BaseProcessor implements UpdateBorrowedBooksOperationModel {
    private final BorrowedBooksRepository borrowedBooksRepositoryImpl;
    private final NotificationRepository notificationRepository;

    private UpdateBorrowedBooksProcessor() {
        borrowedBooksRepositoryImpl = SingletonFactory.getSingletonInstance(BorrowedBooksRepositoryImpl.class);
        notificationRepository = SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class);
    }

    @Override
    public Either<Exception, UpdateBorrowedBooksOutputModel> process(UpdateBorrowedBooksInputModel input) {
        return Try.of(() -> {
                    List<BooksForApproveData> books = input.getBooks();
                    convertInputToDB(books);
                    saveNotificationForUser(books);
                    return UpdateBorrowedBooksOutputModel.builder()
                            .meesage("Successfully updated borrowed books")
                            .build();
                }).toEither()
                .mapLeft(exceptionManager::handle);
    }

    private void saveNotificationForUser(List<BooksForApproveData> books) {
        for (BooksForApproveData book : books) {
            User user = book.getUser();
            StringBuilder sb = buildNotificationMessage(book);
            Notification notification = buildNotificationEntity(user, sb);
            notificationRepository.save(notification);
        }
    }

    private Notification buildNotificationEntity(User user, StringBuilder sb) {
        Notification notification = Notification.builder()
                .user(user)
                .isAdmin(false)
                .isRead(false)
                .message(sb.toString())
                .build();
        return notification;
    }

    @NotNull
    private StringBuilder buildNotificationMessage(BooksForApproveData book) {
        StringBuilder sb = new StringBuilder();
        int number = 1;
        sb.append("Това са одобрените ти книги, които можеш да заемеш от нас за период ")
                .append(System.lineSeparator())
                .append(book.getBorrowingDate()).append("/")
                .append(book.getReturnDate())
                .append(System.lineSeparator());
        for (Book bookBook : book.getBooks()) {
            sb.append(number)
                    .append(". ")
                    .append(bookBook.getTitle()).append(" ")
                    .append(bookBook.getAuthors().stream()
                            .map(Author::toString)
                            .collect(Collectors.joining(", ")))
                    .append(System.lineSeparator());
            number++;
        }
        return sb;
    }

    private void convertInputToDB(List<BooksForApproveData> books) {
        for (BooksForApproveData book : books) {
            BorrowedBooks convert = conversionService.convert(book, BorrowedBooks.class);
            if (convert.getBorrowedBooks().isEmpty()) {
                borrowedBooksRepositoryImpl.deleteById(convert.getId());
                continue;
            }
            borrowedBooksRepositoryImpl.update(convert);
        }
    }
}
