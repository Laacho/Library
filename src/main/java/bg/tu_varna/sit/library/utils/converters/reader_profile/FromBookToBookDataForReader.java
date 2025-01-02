package bg.tu_varna.sit.library.utils.converters.reader_profile;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.get_reader_profile.AuthorDataForReader;
import bg.tu_varna.sit.library.models.get_reader_profile.BookDataForReader;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.ConversionService;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

import java.util.stream.Collectors;

@Mapper(from = Book.class,to = BookDataForReader.class)
public class FromBookToBookDataForReader implements Converter<Book, BookDataForReader> {
    @Override
    public BookDataForReader convert(Book source) {
        ConversionService conversionService = SingletonFactory.getSingletonInstance(ConversionService.class);
        return BookDataForReader.builder()
                .id(source.getId())
                .genre(source.getGenre().getName())
                .title(source.getTitle())
                .inventoryNumber(source.getInventoryNumber())
                .isbn(source.getIsbn())
                .pathToImage(source.getPath())
                .publisher(source.getPublisher().getName())
                .authorDataForReaders(source.getAuthors().stream()
                        .map(author -> conversionService.convert(author, AuthorDataForReader.class))
                        .collect(Collectors.toSet()))
                .build();
    }
}
