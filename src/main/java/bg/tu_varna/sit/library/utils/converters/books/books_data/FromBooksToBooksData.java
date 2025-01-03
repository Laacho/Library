package bg.tu_varna.sit.library.utils.converters.books.books_data;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.all_books.BooksData;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

@Mapper(from = Book.class , to = BooksData.class)
public class FromBooksToBooksData implements Converter<Book, BooksData> {
    @Override
    public BooksData convert(Book source) {
        return BooksData.builder()
                .price(source.getPrice())
                .inventoryNumber(source.getInventoryNumber())
                .isbn(source.getIsbn())
                .title(source.getTitle())
                .genre(source.getGenre())
                .publisher(source.getPublisher())
                .location(source.getLocation())
                .authors(source.getAuthors())
                .path(source.getPath())
                .build();
    }
}
