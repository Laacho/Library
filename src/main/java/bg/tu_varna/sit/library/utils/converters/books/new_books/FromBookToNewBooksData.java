package bg.tu_varna.sit.library.utils.converters.books.new_books;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.new_books.NewBooksData;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;
@Mapper(from = Book.class,to = NewBooksData.class)
public class FromBookToNewBooksData implements Converter<Book, NewBooksData> {
    @Override
    public NewBooksData convert(Book source) {
        return NewBooksData.builder()
                .bookId(source.getId())
                .pathToImage(source.getPath())
                .title(source.getTitle())
                .build();
    }
}
