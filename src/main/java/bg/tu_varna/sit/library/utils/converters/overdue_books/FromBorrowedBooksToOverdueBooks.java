package bg.tu_varna.sit.library.utils.converters.overdue_books;

import bg.tu_varna.sit.library.data.entities.BorrowedBooks;
import bg.tu_varna.sit.library.models.overdue_books.OverdueBooks;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;
@Mapper(from = BorrowedBooks.class,to=OverdueBooks.class)
public class FromBorrowedBooksToOverdueBooks implements Converter<BorrowedBooks, OverdueBooks> {
    @Override
    public OverdueBooks convert(BorrowedBooks source) {
        return OverdueBooks.builder()
                .books(source.getBorrowedBooks())
                .borrowingDate(source.getBorrowingDate())
                .returnDate(source.getReturnDate())
                .deadline(source.getReturnDeadline())
                .user(source.getUser())
                .build();
    }
}
