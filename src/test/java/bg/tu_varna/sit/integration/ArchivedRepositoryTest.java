package bg.tu_varna.sit.integration;

import bg.tu_varna.sit.library.application.ConfigurationDatabase;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.ArchivedBooks;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.repositories.implementations.ArchivedRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.ArchivedRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.BookRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenericRepository;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ArchivedRepositoryTest {

    private ArchivedRepository archivedRepository;
    private BookRepository bookRepository;
    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        archivedRepository= SingletonFactory.getSingletonInstance(ArchivedRepositoryImpl.class);
        bookRepository= SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
        ConfigurationDatabase.config();
        Connection.openSession();
    }
    @Test
    void getInstance(){
        assertInstanceOf(ArchivedRepositoryImpl.class, archivedRepository);
        assertInstanceOf(GenericRepository.class, archivedRepository);
    }
    @Test
    void TestSingleton(){
        ArchivedRepository instance1 = SingletonFactory.getSingletonInstance(ArchivedRepositoryImpl.class);
        assertSame(instance1, archivedRepository);
        BookRepository bookRepository= SingletonFactory.getSingletonInstance(BookRepositoryImpl.class);
        assertSame(bookRepository, this.bookRepository);
    }
    @Test
    void testGetAll(){
        assertNotNull(archivedRepository.findAll());
        assertFalse(archivedRepository.findAll().isEmpty());
    }
    @Test
    void testGetByIdSuccess(){
        Optional<ArchivedBooks> archivedBooksOptional = archivedRepository.findById(1L);
        Book byId = bookRepository.findById(2L).get();
        ArchivedBooks archivedBooks = new ArchivedBooks(1L,byId, LocalDate.of(2025,1, 7));
        assertTrue(archivedBooksOptional.isPresent());
        assertSame(archivedBooksOptional.get().getId(), archivedBooks.getId());
        assertEquals(archivedBooksOptional.get().getArchiveDate(), archivedBooks.getArchiveDate());
    }
    @Test
    void testGetByIdFail(){
        assertEquals(Optional.empty(), archivedRepository.findById(-1L));
        assertEquals(Optional.empty(), archivedRepository.findById(111563432313312L));
    }
}
