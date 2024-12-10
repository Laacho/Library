package bg.tu_varna.sit.library.data.repositories.interfaces;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.entities.Genre;

import java.util.List;

public interface BookRepository extends GenericRepository<Book> {
    List<Book> findBookContainingText(String text);
    List<Book> findByGenre(Genre genreId);
}
