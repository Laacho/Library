package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.logging.confirm_registration.ConfirmRegistrationProcessor;
import bg.tu_varna.sit.library.core.logging.login.LoginProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.confirm_registration.ConfirmRegistrationOperationModel;
import bg.tu_varna.sit.library.models.login.LoginOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class LoginProcessorTest {

    @Mock
    private UserCredentialsRepository userCredentialsRepository;

    private LoginProcessor confirmRegistrationProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        confirmRegistrationProcessor =SingletonFactory.getSingletonInstance(LoginProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, confirmRegistrationProcessor);
        assertInstanceOf(LoginOperationModel.class, confirmRegistrationProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.confirmRegistrationProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
