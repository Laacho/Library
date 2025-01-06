package bg.tu_varna.sit.integration;

import bg.tu_varna.sit.library.application.ConfigurationDatabase;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.GenericRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserCredentialsRepositoryTest {

    private UserCredentialsRepository userCredentialsRepository;

    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        userCredentialsRepository= SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
        ConfigurationDatabase.config();
        Connection.openSession();
    }
    @Test
    void getInstance(){
        assertInstanceOf(UserCredentialsRepositoryImpl.class, userCredentialsRepository);
        assertInstanceOf(GenericRepository.class, userCredentialsRepository);
    }
    @Test
    void TestSingleton(){
        UserCredentialsRepository instance1 = SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class);
        assertSame(instance1, userCredentialsRepository);
    }
    @Test
    void testGetAll(){
        assertNotNull(userCredentialsRepository.findAll());
    }
    @Test
    void testFindByIdSuccess(){
        Optional<UserCredentials> byId = userCredentialsRepository.findById(1L);
        assertTrue(byId.isPresent());
        UserCredentials userCredentials = byId.get();
        assertNotNull(userCredentials);
        assertEquals(1L, userCredentials.getId());
        assertEquals("lacho", userCredentials.getUsername());
    }
    @Test
    void testFindByIdFail(){
        assertEquals(Optional.empty(), userCredentialsRepository.findById(-1L));
        assertEquals(Optional.empty(), userCredentialsRepository.findById(28312361263L));
    }
    @Test
    void testFindAllUsers(){
        List<UserCredentials> allUsers = userCredentialsRepository.findAllUsers();
        assertNotNull(allUsers);
        if(!allUsers.isEmpty()){
            for (UserCredentials allUser : allUsers) {
                assertNotNull(allUser);
                assertFalse(allUser.getAdmin());
            }
        }
    }
    @Test
    void testFindByUser(){
        Optional<UserCredentials> byUser = userCredentialsRepository.findByUser(new User());
        assertTrue(byUser.isEmpty());
    }
    @Test
    void testFindByUsernameFail(){
        String invalidUsername = "Should not exist";
        Optional<UserCredentials> byUser = userCredentialsRepository.findByUsername(invalidUsername);
        assertTrue(byUser.isEmpty());
    }
    @Test
    void testFindByUsernameSuccess(){
        Optional<UserCredentials> byUsername = userCredentialsRepository.findByUsername("lacho");
        assertTrue(byUsername.isPresent());
        UserCredentials userCredentials = byUsername.get();
        assertNotNull(userCredentials);
        assertEquals("lacho", userCredentials.getUsername());
    }
    @Test
    void testFindByEmailFail(){
        String invalidEmail = "Should not exist";
        Optional<UserCredentials> byEmail = userCredentialsRepository.findByEmail(invalidEmail);
        assertTrue(byEmail.isEmpty());
    }
    @Test
    void testFindByEmailSuccess(){
        Optional<UserCredentials> byEmail = userCredentialsRepository.findByEmail("lachezarradushev@gmail.com");
        assertTrue(byEmail.isPresent());
        UserCredentials userCredentials = byEmail.get();
        assertNotNull(userCredentials);
        assertEquals("lachezarradushev@gmail.com", userCredentials.getEmail());
    }
}

