package bg.tu_varna.sit.integration;

import bg.tu_varna.sit.library.application.ConfigurationDatabase;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.entities.Genre;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.GenreRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenericRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenreRepository;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryTest {

    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        bookRepository= SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
        genreRepository= SingletonFactory.getSingletonInstance(GenreRepositoryImpl.class);
        ConfigurationDatabase.config();
        Connection.openSession();
    }
    @Test
    void getInstance(){
        assertInstanceOf(BookRepositoryImpl.class, bookRepository);
        assertInstanceOf(GenericRepository.class, bookRepository);
    }
    @Test
    void TestSingleton(){
        BookRepository instance1 = SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
        assertSame(instance1, bookRepository);
    }
    @Test
    void testGetAll(){
        assertNotNull(bookRepository.findAll());
        assertFalse(bookRepository.findAll().isEmpty());
    }
    @Test
    void testGetById() {
        assertEquals(Optional.empty(), bookRepository.findById(-10L));
        assertEquals(Optional.empty(), bookRepository.findById(1765356312L));

        Optional<Book> byId = bookRepository.findById(2L);
        assertNotNull(byId);
        assertTrue(byId.isPresent());
        Book book = byId.get();
        assertEquals(2L, book.getId());
        assertEquals("Harry Potter", book.getTitle());
        assertEquals(98.00,book.getPrice().doubleValue());
        assertEquals("Fantasy",book.getGenre().getName());
        assertEquals("Harper Collins",book.getPublisher().getName());
    }
    @Test
    void testBookContainingText(){
        String text="Should not exist";
        assertEquals(Collections.emptyList(), bookRepository.findBookContainingText(text));
        assertEquals(Collections.emptyList(), bookRepository.findBookContainingText(text));
    }
    @Test
    void testFindByGenreError(){
        Genre genre=new Genre();
        genre.setName("Invalid Genre");
        List<Book> byGenre = bookRepository.findByGenre(genre);
        assertEquals(0, byGenre.size());
    }
    @Test
    void testFindByGenreSuccess(){
        String name="Fantasy";
        Optional<Genre> byName = genreRepository.findByName(name);
        assertNotNull(byName);
        assertTrue(byName.isPresent());
        Genre genre = byName.get();
        List<Book> byGenre = bookRepository.findByGenre(genre);
        assertEquals(3, byGenre.size());
    }
    @Test
    void findByInventoryNumber(){
        Optional<Book> byInventoryNumber = bookRepository.findByInventoryNumber(String.valueOf(1));
        assertNotNull(byInventoryNumber);
        assertTrue(byInventoryNumber.isPresent());
        Book book = byInventoryNumber.get();
        assertEquals("Harry Potter", book.getTitle());
        assertEquals(98.00,book.getPrice().doubleValue());
        assertEquals("Fantasy",book.getGenre().getName());
        assertEquals("Harper Collins",book.getPublisher().getName());
    }
    @Test
    void findByInventoryNumberError(){
        Optional<Book> byInventoryNumber = bookRepository.findByInventoryNumber(String.valueOf(-143));
        assertEquals(Optional.empty(), byInventoryNumber);
        assertTrue(byInventoryNumber.isEmpty());
    }
    @Test
    void testFindAllGoodBooks(){
        List<Book> allGoodBooks = bookRepository.findAllGoodBooks();
        assertNotNull(allGoodBooks);
        assertFalse(allGoodBooks.isEmpty());
    }
}
