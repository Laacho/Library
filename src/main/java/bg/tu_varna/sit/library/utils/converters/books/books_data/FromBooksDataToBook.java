package bg.tu_varna.sit.library.utils.converters.books.books_data;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.all_books.BooksData;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

@Mapper(from = BooksData.class, to = Book.class)
public class FromBooksDataToBook implements Converter<BooksData, Book> {
    @Override
    public Book convert(BooksData source) {
        return Book.builder()
                .id(source.getId())
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
