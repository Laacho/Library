package bg.tu_varna.sit.library.utils.converters.books.borrow_books;

import bg.tu_varna.sit.library.data.entities.BorrowedBooks;
import bg.tu_varna.sit.library.models.borrow_books.BorrowBooksInputModel;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

import java.time.LocalDate;

@Mapper(from = BorrowBooksInputModel.class, to= BorrowedBooks.class)
public class FromBorrowBooksInputModelToBorrowedBooks implements Converter<BorrowBooksInputModel, BorrowedBooks> {
    @Override
    public BorrowedBooks convert(BorrowBooksInputModel source) {
        return BorrowedBooks.builder()
                .borrowingDate(source.getBorrowDate())
                .isRequestApproved(false)
                .returnDeadline(LocalDate.now())
                .returnDate(source.getReturnDate())
                .build();
    }
}
