package bg.tu_varna.sit.integration;

import bg.tu_varna.sit.library.application.ConfigurationDatabase;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Publisher;
import bg.tu_varna.sit.library.data.repositories.implementations.PublisherRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenericRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.PublisherRepository;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PublisherRepositoryTest {

    private PublisherRepository publisherRepository;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        publisherRepository= SingletonFactory.getSingletonInstance(PublisherRepositoryImpl.class);
        ConfigurationDatabase.config();
        Connection.openSession();
    }

    @Test
    void getInstance(){
        assertInstanceOf(PublisherRepositoryImpl.class, publisherRepository);
        assertInstanceOf(GenericRepository.class, publisherRepository);
    }
    @Test
    void TestSingleton(){
        PublisherRepository instance1 = SingletonFactory.getSingletonInstance(PublisherRepositoryImpl.class);
        assertSame(instance1, publisherRepository);
    }
    @Test
    void testGetAll(){
        assertNotNull(publisherRepository.findAll());
    }
    @Test
    void testFindByIdSuccess(){
        Optional<Publisher> byId = publisherRepository.findById(3L);
        assertTrue(byId.isPresent());
        Publisher publisher = byId.get();
        assertEquals("Kolibri", publisher.getName());
    }
    @Test
    void testFindByIdFail(){
        assertEquals(Optional.empty(), publisherRepository.findById(-1L));
        assertEquals(Optional.empty(), publisherRepository.findById(432434234L));
    }
    @Test
    void testFindByNameSuccess(){
        Optional<Publisher> ozon = publisherRepository.findByName("Ozon");
        assertTrue(ozon.isPresent());
        Publisher publisher = ozon.get();
        assertNotNull(publisher);
        assertEquals("Ozon", publisher.getName());
    }
    @Test
    void testFindByNameFail(){
        assertEquals(Optional.empty(), publisherRepository.findByName("Should not exist"));
    }

}
