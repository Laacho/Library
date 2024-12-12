package bg.tu_varna.sit.library.utils.converters.approve_books;

import bg.tu_varna.sit.library.data.entities.BorrowedBooks;
import bg.tu_varna.sit.library.models.approve_books.BooksForApproveData;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

@Mapper(to = BorrowedBooks.class,from = BooksForApproveData.class)
public class FromBooksForApproveDataToBorrowedBooks implements Converter<BooksForApproveData,BorrowedBooks> {

    @Override
    public BorrowedBooks convert(BooksForApproveData source) {
        return BorrowedBooks.builder()
                .id(source.getId())
                .borrowedBooks(source.getBooks())
                .borrowingDate(source.getBorrowingDate())
                .returnDate(source.getReturnDate())
                .returnDeadline(source.getDeadline())
                .isRequestApproved(true)
                .user(source.getUser())
                .build();
    }
}
