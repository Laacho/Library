package bg.tu_varna.sit.integration;

import bg.tu_varna.sit.library.application.ConfigurationDatabase;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.repositories.implementations.DiscardedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.DiscardedBooksRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenericRepository;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DiscardedBooksRepositoryTest {

    private DiscardedBooksRepository discardedBooksRepository;

    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        discardedBooksRepository= SingletonFactory.getSingletonInstance(DiscardedBooksRepositoryImpl.class);
        ConfigurationDatabase.config();
        Connection.openSession();
    }
    @Test
    void getInstance(){
        assertInstanceOf(DiscardedBooksRepositoryImpl.class, discardedBooksRepository);
        assertInstanceOf(GenericRepository.class, discardedBooksRepository);
    }
    @Test
    void TestSingleton(){
        DiscardedBooksRepository instance1 = SingletonFactory.getSingletonInstance(DiscardedBooksRepositoryImpl.class);
        assertSame(instance1, discardedBooksRepository);
    }
    @Test
    void testGetAll(){
        assertNotNull(discardedBooksRepository.findAll());
    }

    @Test
    void testGeTByIdError(){
        assertEquals(Optional.empty(),discardedBooksRepository.findById(-1L));
        assertEquals(Optional.empty(),discardedBooksRepository.findById(312391239L));
    }
}
