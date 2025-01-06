package bg.tu_varna.sit.integration;

import bg.tu_varna.sit.library.application.ConfigurationDatabase;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Genre;
import bg.tu_varna.sit.library.data.repositories.implementations.GenreRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenericRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenreRepository;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GenreRepositoryTest {

    private GenreRepository genreRepository;


    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        genreRepository= SingletonFactory.getSingletonInstance(GenreRepositoryImpl.class);
        ConfigurationDatabase.config();
        Connection.openSession();
    }
    @Test
    void getInstance(){
        assertInstanceOf(GenreRepositoryImpl.class, genreRepository);
        assertInstanceOf(GenericRepository.class, genreRepository);
    }
    @Test
    void TestSingleton(){
        GenreRepository instance1 = SingletonFactory.getSingletonInstance(GenreRepositoryImpl.class);
        assertSame(instance1, genreRepository);
    }
    @Test
    void testGetAll(){
        assertNotNull(genreRepository.findAll());
        assertFalse(genreRepository.findAll().isEmpty());
    }
    @Test
    void testGetById(){
        assertEquals(Optional.empty(), genreRepository.findById(-1L));
        assertEquals(Optional.empty(), genreRepository.findById(111563432313312L));

        Optional<Genre> genreOptional = genreRepository.findById(1L);
        Genre expectedGenre = new Genre(1L, "Fantasy");
        assertTrue(genreOptional.isPresent());
        assertEquals(expectedGenre.getName(), genreOptional.get().getName());
        assertEquals(expectedGenre.getId(), genreOptional.get().getId());
    }
    @Test
    void testFindByNameSuccess() {
        String name = "Fantasy";
        Genre expectedGenre = new Genre(1L, "Fantasy");
        Optional<Genre> result = genreRepository.findByName(name);
        assertTrue(result.isPresent());
        assertEquals(expectedGenre.getName(), result.get().getName());
        assertEquals(expectedGenre.getId(), result.get().getId());
    }
    @Test
    void testFindByNameNotFound() {
        String name = "NonExistentGenre";
        Optional<Genre> result = genreRepository.findByName(name);
        assertFalse(result.isPresent());
    }



}


