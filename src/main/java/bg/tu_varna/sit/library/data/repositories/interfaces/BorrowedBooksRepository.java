package bg.tu_varna.sit.library.data.repositories.interfaces;

import bg.tu_varna.sit.library.data.entities.BorrowedBooks;

public interface BorrowedBooksRepository extends GenericRepository<BorrowedBooks> {
    void update(BorrowedBooks books);
}
