package bg.tu_varna.sit.library.utils.converters.books.booksData;

import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.archived_books.BooksData;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

import java.util.stream.Collectors;

@Mapper(from = BooksData.class , to = CommonBooksProperties.class)

public class FromArchiveBooksDataToCommonBooksProperties implements Converter<BooksData, CommonBooksProperties> {
    @Override
    public CommonBooksProperties convert(BooksData source) {
        return CommonBooksProperties.builder()
                .inventoryNumber(source.getInventoryNumber())
                .publisher(source.getPublisher().getName())
                .title(source.getTitle())
                .price(source.getPrice().doubleValue())
                .genre(source.getGenre().getName())
                .authors(source.getAuthors().stream()
                        .map(author -> author.getFirstName() + " " + author.getLastName())
                        .collect(Collectors.toSet()))
                .isbn(source.getIsbn())
                .pathImage(source.getPath())
                .build();
    }
}
