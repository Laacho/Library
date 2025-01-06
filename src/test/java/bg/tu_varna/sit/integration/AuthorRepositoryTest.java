package bg.tu_varna.sit.integration;

import bg.tu_varna.sit.library.application.ConfigurationDatabase;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.data.repositories.implementations.AuthorRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.AuthorRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenericRepository;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorRepositoryTest {

    private AuthorRepository authorRepository;

    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        authorRepository= SingletonFactory.getSingletonInstance(AuthorRepositoryImpl.class);
        ConfigurationDatabase.config();
        Connection.openSession();
    }
    @Test
    void getInstance(){
        assertInstanceOf(AuthorRepositoryImpl.class, authorRepository);
        assertInstanceOf(GenericRepository.class, authorRepository);
    }
    @Test
    void TestSingleton(){
        AuthorRepository instance1 = SingletonFactory.getSingletonInstance(AuthorRepositoryImpl.class);
        assertSame(instance1, authorRepository);
    }
    @Test
    void testGetAll(){
        assertNotNull(authorRepository.findAll());
        assertFalse(authorRepository.findAll().isEmpty());
    }
    @Test
    void testGetByIdSuccess() {
        Optional<Author> byId = authorRepository.findById(1L);
        assertNotNull(byId);
        assertTrue(byId.isPresent());
        assertEquals(1L, byId.get().getId());
        Author author = byId.get();
        assertNotNull(author);
        assertEquals("Ivan", author.getFirstName());
        assertEquals("Petrov", author.getLastName());
    }
    @Test
    void testGetByIdFail(){
        assertEquals(Optional.empty(), authorRepository.findById(-1L));
        assertEquals(Optional.empty(), authorRepository.findById(11344323112L));
    }
}
