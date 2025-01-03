package bg.tu_varna.sit.library.data.repositories.interfaces;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.entities.Genre;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends GenericRepository<Book> {
    List<Book> findBookContainingText(String text);
    List<Book> findByGenre(Genre genreId);
    Optional<Book> findByInventoryNumber(String inventoryNumber);
    void update(Book book);
    List<Book> findAllGoodBooks();
}
