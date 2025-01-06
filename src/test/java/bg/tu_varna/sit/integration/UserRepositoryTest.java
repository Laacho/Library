package bg.tu_varna.sit.integration;

import bg.tu_varna.sit.library.application.ConfigurationDatabase;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.repositories.implementations.UserRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenericRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserRepository;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {
    private UserRepository userRepository;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        userRepository= SingletonFactory.getSingletonInstance(UserRepositoryImpl.class);
        ConfigurationDatabase.config();
        Connection.openSession();
    }
    @Test
    void getInstance(){
        assertInstanceOf(UserRepositoryImpl.class, userRepository);
        assertInstanceOf(GenericRepository.class, userRepository);
    }
    @Test
    void TestSingleton(){
        UserRepository instance1 = SingletonFactory.getSingletonInstance(UserRepositoryImpl.class);
        assertSame(instance1, userRepository);
    }
    @Test
    void testGetAll(){
        assertNotNull(userRepository.findAll());
    }
    @Test
    void testFindByIdFail(){
        assertEquals(Optional.empty(), userRepository.findById(-1L));
        assertEquals(Optional.empty(), userRepository.findById(28312361263L));
    }
    @Test
    void testFindByIdSuccess(){
        Optional<User> byId = userRepository.findById(1L);
        assertTrue(byId.isPresent());
        User user = byId.get();
        assertNotNull(user);
        assertEquals(1L, user.getId());
    }
}
