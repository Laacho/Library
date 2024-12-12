package bg.tu_varna.sit.library.utils.converters.approve_books;

import bg.tu_varna.sit.library.data.entities.BorrowedBooks;
import bg.tu_varna.sit.library.models.approve_books.BooksForApproveData;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;
@Mapper(from = BorrowedBooks.class,to = BooksForApproveData.class)
public class FromBorrowedBooksToBooksForApproveData implements Converter<BorrowedBooks, BooksForApproveData> {
    @Override
    public BooksForApproveData convert(BorrowedBooks source) {
        return BooksForApproveData.builder()
                .id(source.getId())
                .books(source.getBorrowedBooks())
                .borrowingDate(source.getBorrowingDate())
                .returnDate(source.getReturnDate())
                .user(source.getUser())
                .build();
    }
}
