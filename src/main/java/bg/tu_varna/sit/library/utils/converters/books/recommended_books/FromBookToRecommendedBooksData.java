package bg.tu_varna.sit.library.utils.converters.books.recommended_books;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.recommended_books.RecommendedBooksData;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

@Mapper(from = Book.class, to = RecommendedBooksData.class)
public class FromBookToRecommendedBooksData implements Converter<Book, RecommendedBooksData> {
    @Override
    public RecommendedBooksData convert(Book source) {
        return RecommendedBooksData.builder()
                .id(source.getId())
                .title(source.getTitle())
                .pathToImage(source.getPath())
                .build();
    }
}
