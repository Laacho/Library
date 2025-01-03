package bg.tu_varna.sit.library.utils.converters.reader_profile;

import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.get_reader_profile.BookDataForReader;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

import java.util.stream.Collectors;

@Mapper(from = BookDataForReader.class, to = CommonBooksProperties.class)
public class FromBookDataForReaderToCommonBooksProperties implements Converter<BookDataForReader, CommonBooksProperties> {
    @Override
    public CommonBooksProperties convert(BookDataForReader source) {
        return CommonBooksProperties.builder()
                .isbn(source.getIsbn())
                .title(source.getTitle())
                .publisher(source.getPublisher())
                .genre(source.getGenre())
                .pathImage(source.getPathToImage())
                .inventoryNumber(String.valueOf(source.getInventoryNumber()))
                .authors(source.getAuthorDataForReaders().stream()
                        .map(author -> author.getFirstName() + " " + author.getLastName())
                        .collect(Collectors.toSet()))
                .build();
    }
}
