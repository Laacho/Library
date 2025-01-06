package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.users_table_view.UsersTableViewProcessor;
import bg.tu_varna.sit.library.core.logging.confirm_registration.ConfirmRegistrationProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.confirm_registration.ConfirmRegistrationOperationModel;
import bg.tu_varna.sit.library.models.users_table_view.UsersTableViewOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class ConfirmRegistrationProcessorTest {

    @Mock
    private UserCredentialsRepository userCredentialsRepository;

    private ConfirmRegistrationProcessor confirmRegistrationProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        confirmRegistrationProcessor =SingletonFactory.getSingletonInstance(ConfirmRegistrationProcessor.class);
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
        assertInstanceOf(ConfirmRegistrationOperationModel.class, confirmRegistrationProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.confirmRegistrationProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
