package bg.tu_varna.sit.integration;

import bg.tu_varna.sit.library.application.ConfigurationDatabase;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.Location;
import bg.tu_varna.sit.library.data.repositories.implementations.BorrowedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.LocationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenericRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.LocationRepository;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LocationRepositoryTest {

    private LocationRepository locationRepository;

    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        locationRepository= SingletonFactory.getSingletonInstance(LocationRepositoryImpl.class);
        ConfigurationDatabase.config();
        Connection.openSession();
    }
    @Test
    void getInstance(){
        assertInstanceOf(LocationRepositoryImpl.class, locationRepository);
        assertInstanceOf(GenericRepository.class, locationRepository);
    }
    @Test
    void TestSingleton(){
        LocationRepository instance1 = SingletonFactory.getSingletonInstance(LocationRepositoryImpl.class);
        assertSame(instance1, locationRepository);
    }
    @Test
    void testGetAll(){
        assertNotNull(locationRepository.findAll());
    }

    @Test
    void testFindByIdSuccess(){
        assertNotNull(locationRepository.findById(1L));
        assertNotNull(locationRepository.findById(2L));
    }
    @Test
    void testFindByIdFail(){
        assertEquals(Optional.empty(), locationRepository.findById(-1L));
        assertEquals(Optional.empty(), locationRepository.findById(2384326462342L));
    }
    @Test
    void testFindByNameAndRowError(){
        String invalidName = "Should not find";
        Long invalidRow = Long.MAX_VALUE;
        Optional<Location> byNameAndRow = locationRepository.findByNameAndRow(invalidName, invalidRow);
        assertNotNull(byNameAndRow);
        assertTrue(byNameAndRow.isEmpty());
    }
    @Test
    void testFindByNameAndRowSuccess(){
        String name = "Fantasy";
        Long row=5L;
        Optional<Location> byNameAndRow = locationRepository.findByNameAndRow(name, row);
        assertNotNull(byNameAndRow);
        assertTrue(byNameAndRow.isPresent());
        Location location = byNameAndRow.get();
        assertNotNull(location);
        assertEquals(5L,location.getRowNum());

    }

}