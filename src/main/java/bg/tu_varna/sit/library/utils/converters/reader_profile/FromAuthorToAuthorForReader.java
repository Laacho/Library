package bg.tu_varna.sit.library.utils.converters.reader_profile;

import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.models.get_reader_profile.AuthorDataForReader;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

@Mapper(from = Author.class, to = AuthorDataForReader.class)
public class FromAuthorToAuthorForReader implements Converter<Author, AuthorDataForReader> {
    @Override
    public AuthorDataForReader convert(Author source) {
        return AuthorDataForReader.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}
