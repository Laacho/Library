package bg.tu_varna.sit.integration;

import bg.tu_varna.sit.library.application.ConfigurationDatabase;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.repositories.implementations.AuthorRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.BorrowedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.AuthorRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenericRepository;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BorrowedBookRepositoryTest {
    private BorrowedBooksRepository borrowedBooksRepository;

    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        borrowedBooksRepository= SingletonFactory.getSingletonInstance(BorrowedBooksRepositoryImpl.class);
        ConfigurationDatabase.config();
        Connection.openSession();
    }
    @Test
    void getInstance(){
        assertInstanceOf(BorrowedBooksRepositoryImpl.class, borrowedBooksRepository);
        assertInstanceOf(GenericRepository.class, borrowedBooksRepository);
    }
    @Test
    void TestSingleton(){
        BorrowedBooksRepository instance1 = SingletonFactory.getSingletonInstance(BorrowedBooksRepositoryImpl.class);
        assertSame(instance1, borrowedBooksRepository);
    }
    @Test
    void testGetAll(){
        assertNotNull(borrowedBooksRepository.findAll());
    }

}
