package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.approve_books.ApproveBooksProcessor;
import bg.tu_varna.sit.library.core.admin.approve_profiles.ApproveProfilesProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.approve_profiles.ApproveProfilesOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class ApproveProfilesProcessorTest {
    @Mock
    private  UserCredentialsRepository userCredentialsRepository;

    private ApproveProfilesProcessor approveProfilesProcessor;
    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        approveProfilesProcessor=SingletonFactory.getSingletonInstance(ApproveProfilesProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);
        }
    }
    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class,approveProfilesProcessor);
        assertInstanceOf(ApproveProfilesOperationModel.class,approveProfilesProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.approveProfilesProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
