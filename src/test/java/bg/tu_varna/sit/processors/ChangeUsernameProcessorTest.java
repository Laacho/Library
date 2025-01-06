package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.user.my_profile.ChangePasswordProcessor;
import bg.tu_varna.sit.library.core.user.my_profile.ChangeUsernameProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.change_password.ChangePasswordOperationModel;
import bg.tu_varna.sit.library.models.change_username.ChangeUsernameOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class ChangeUsernameProcessorTest {
    @Mock
    private UserCredentialsRepository userCredentialsRepository;
    private ChangeUsernameProcessor changeUsernameProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        changeUsernameProcessor =SingletonFactory.getSingletonInstance(ChangeUsernameProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, changeUsernameProcessor);
        assertInstanceOf(ChangeUsernameOperationModel.class, changeUsernameProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.changeUsernameProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
