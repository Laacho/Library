package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.settings.PromoteUserProcessor;
import bg.tu_varna.sit.library.core.admin.settings.ResetPasswordForUserProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.promote_user.PromoteUserOperationModel;
import bg.tu_varna.sit.library.models.reset_password_for_user.ResetPasswordForUserOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class ResetPasswordForUserProcessorTest {

    @Mock
    private UserCredentialsRepository userCredentialsRepository;
    private ResetPasswordForUserProcessor resetPasswordForUserProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        resetPasswordForUserProcessor =SingletonFactory.getSingletonInstance(ResetPasswordForUserProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, resetPasswordForUserProcessor);
        assertInstanceOf(ResetPasswordForUserOperationModel.class, resetPasswordForUserProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.resetPasswordForUserProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
