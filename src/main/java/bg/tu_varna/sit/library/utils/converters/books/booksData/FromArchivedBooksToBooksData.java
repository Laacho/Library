package bg.tu_varna.sit.library.utils.converters.books.booksData;

import bg.tu_varna.sit.library.data.entities.ArchivedBooks;
import bg.tu_varna.sit.library.models.archived_books.BooksData;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;
@Mapper(from = ArchivedBooks.class,to = BooksData.class)
public class FromArchivedBooksToBooksData implements Converter<ArchivedBooks, BooksData> {
    @Override
    public BooksData convert(ArchivedBooks source) {
        return BooksData.builder()
                .inventoryNumber(source.getBook().getInventoryNumber())
                .authors(source.getBook().getAuthors())
                .price(source.getBook().getPrice())
                .publisher(source.getBook().getPublisher())
                .archivedDate(source.getArchiveDate())
                .title(source.getBook().getTitle())
                .genre(source.getBook().getGenre())
                .isbn(source.getBook().getIsbn())
                .location(source.getBook().getLocation())
                .path(source.getBook().getPath())
                .build();
    }
}
