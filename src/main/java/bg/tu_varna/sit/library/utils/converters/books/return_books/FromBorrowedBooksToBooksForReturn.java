package bg.tu_varna.sit.library.utils.converters.books.return_books;

import bg.tu_varna.sit.library.data.entities.BorrowedBooks;
import bg.tu_varna.sit.library.models.return_books.BooksForReturn;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

@Mapper(from = BorrowedBooks.class, to = BooksForReturn.class)
public class FromBorrowedBooksToBooksForReturn implements Converter<BorrowedBooks, BooksForReturn> {
    @Override
    public BooksForReturn convert(BorrowedBooks source) {
        return BooksForReturn.builder()
                .borrowingDate(source.getBorrowingDate())
                .books(source.getBorrowedBooks())
                .returnDate(source.getReturnDate())
                .deadline(source.getReturnDeadline())
                .build();
    }
}
