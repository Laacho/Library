package bg.tu_varna.sit.library.utils.converters.books.books_data;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

import java.util.stream.Collectors;

@Mapper(from = Book.class, to = CommonBooksProperties.class)
public class FromBookToCommonBooksProperties implements Converter<Book, CommonBooksProperties> {
    @Override
    public CommonBooksProperties convert(Book source) {
        return CommonBooksProperties.builder()
                .pathImage(source.getPath())
                .isbn(source.getIsbn())
                .title(source.getTitle())
                .price(source.getPrice().doubleValue())
                .inventoryNumber(source.getInventoryNumber())
                .genre(source.getGenre().getName())
                .authors(source.getAuthors().stream()
                        .map(author -> author.getFirstName() + " " + author.getLastName())
                        .collect(Collectors.toSet()))
                .publisher(source.getPublisher().getName())
                .build();
    }
}
