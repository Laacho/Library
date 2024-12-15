package bg.tu_varna.sit.library.data.repositories.interfaces;

import bg.tu_varna.sit.library.data.entities.BorrowedBooks;
import bg.tu_varna.sit.library.data.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BorrowedBooksRepository extends GenericRepository<BorrowedBooks> {
    void update(BorrowedBooks books);
    List<BorrowedBooks> findByUser(User user);
    Optional<BorrowedBooks> findByUserAndBorrowedDateAndReturnDate(User user, LocalDate borrowedDate,LocalDate returnDate);
}
