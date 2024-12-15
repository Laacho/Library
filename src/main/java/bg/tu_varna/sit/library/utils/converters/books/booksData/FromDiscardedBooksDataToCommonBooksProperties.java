package bg.tu_varna.sit.library.utils.converters.books.booksData;

import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.discarded_books.BooksData;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;
@Mapper(from = BooksData.class , to = CommonBooksProperties.class)

public class FromDiscardedBooksDataToCommonBooksProperties implements Converter<BooksData, CommonBooksProperties> {
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
