package bg.tu_varna.sit.integration;

import bg.tu_varna.sit.library.application.ConfigurationDatabase;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.ReaderProfile;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.repositories.implementations.PublisherRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenericRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.PublisherRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderProfileRepositoryTest {
    private ReaderProfileRepository readerProfileRepository;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        readerProfileRepository= SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class);
        ConfigurationDatabase.config();
        Connection.openSession();
    }

    @Test
    void getInstance(){
        assertInstanceOf(ReaderProfileRepositoryImp.class, readerProfileRepository);
        assertInstanceOf(GenericRepository.class, readerProfileRepository);
    }
    @Test
    void TestSingleton(){
        ReaderProfileRepository instance1 = SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class);
        assertSame(instance1, readerProfileRepository);
    }
    @Test
    void testGetAll(){
        assertNotNull(readerProfileRepository.findAll());
    }
    @Test
    void testFindByIdSuccess(){
        Optional<ReaderProfile> byId = readerProfileRepository.findById(1L);
        assertTrue(byId.isPresent());
        ReaderProfile readerProfile = byId.get();
        assertNotNull(readerProfile);
        assertEquals(1L, readerProfile.getId());
    }
    @Test
    void testFindByIdFail(){
        assertEquals(Optional.empty(), readerProfileRepository.findById(-1L));
        assertEquals(Optional.empty(), readerProfileRepository.findById(436534234L));
        assertEquals(Optional.empty(), readerProfileRepository.findById(null));
    }
    @Test
    void testFindByUserFail(){
        Exception exception = assertThrows(Exception.class, () -> {
            readerProfileRepository.findByUser(null);
        });
        assertTrue(exception.getMessage().contains("user"));
        Optional<ReaderProfile> optionalReaderProfile = readerProfileRepository.findByUser(new User());
        assertTrue(optionalReaderProfile.isEmpty());
    }


}
