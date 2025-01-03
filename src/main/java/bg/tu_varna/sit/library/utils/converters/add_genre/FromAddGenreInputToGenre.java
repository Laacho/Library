package bg.tu_varna.sit.library.utils.converters.add_genre;

import bg.tu_varna.sit.library.data.entities.Genre;
import bg.tu_varna.sit.library.models.add_genre.CheckGenreInputModel;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;
@Mapper(from = CheckGenreInputModel.class , to = Genre.class)
public class FromAddGenreInputToGenre implements Converter<CheckGenreInputModel, Genre> {
    @Override
    public Genre convert(CheckGenreInputModel source) {
        return Genre.builder()
                .name(source.getGenre())
                .build();
    }
}
