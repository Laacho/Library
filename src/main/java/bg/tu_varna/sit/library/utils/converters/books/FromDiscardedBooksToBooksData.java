package bg.tu_varna.sit.library.utils.converters.books;

import bg.tu_varna.sit.library.data.entities.DiscardedBooks;
import bg.tu_varna.sit.library.models.discarded_books.BooksData;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;
@Mapper(from = DiscardedBooks.class , to = BooksData.class)
public class FromDiscardedBooksToBooksData implements Converter<DiscardedBooks, BooksData> {
    @Override
    public BooksData convert(DiscardedBooks source) {
        return BooksData.builder()
                .price(source.getBook().getPrice())
                .discardingDate(source.getDiscardingDate())
                .genre(source.getBook().getGenre())
                .isbn(source.getBook().getIsbn())
                .inventoryNumber(source.getBook().getInventoryNumber())
                .title(source.getBook().getTitle())
                .publisher(source.getBook().getPublisher())
                .reason(source.getReason())
                .quantity(source.getQuantity())
                .authors(source.getBook().getAuthors())
                .build();
    }
}
