package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.settings.DeleteUserProcessor;
import bg.tu_varna.sit.library.core.admin.settings.DemoteUserProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.delete_user.DeleteUserOperationModel;
import bg.tu_varna.sit.library.models.demote_user.DemoteUserOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class DemoteUserProcessorTest {

    @Mock
    private UserCredentialsRepository userCredentialsRepository;
    private DemoteUserProcessor deleteUserProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        deleteUserProcessor =SingletonFactory.getSingletonInstance(DemoteUserProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, deleteUserProcessor);
        assertInstanceOf(DemoteUserOperationModel.class, deleteUserProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.deleteUserProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}