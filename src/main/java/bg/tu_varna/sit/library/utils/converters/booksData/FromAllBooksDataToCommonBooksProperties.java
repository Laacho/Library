package bg.tu_varna.sit.library.utils.converters.booksData;

import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.all_books.BooksData;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

public class FromAllBooksDataToCommonBooksProperties implements Converter<BooksData, CommonBooksProperties> {
    @Override
    public CommonBooksProperties convert(BooksData source) {
        return CommonBooksProperties.builder()
                .inventoryNumber(source.getInventoryNumber())
                .publisher(source.getPublisher())
                .title(source.getTitle())
                .price(source.getPrice())
                .genre(source.getGenre())
                .authors(source.getAuthors())
                .isbn(source.getIsbn())
                .build();
    }
}
