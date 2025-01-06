package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.logging.login.LoginProcessor;
import bg.tu_varna.sit.library.core.logging.logout.LogoutProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.login.LoginOperationModel;
import bg.tu_varna.sit.library.models.logout.LogoutOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class LogoutProcessorTest {

    @Mock
    private UserCredentialsRepository userCredentialsRepository;

    private LogoutProcessor logoutProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        logoutProcessor =SingletonFactory.getSingletonInstance(LogoutProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, logoutProcessor);
        assertInstanceOf(LogoutOperationModel.class, logoutProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.logoutProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
