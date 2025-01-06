package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.user.reader_profile.GetReaderProfileProcessor;
import bg.tu_varna.sit.library.core.user.reader_profile.RequestReaderProfileProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.NotificationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.request_reader_profile.RequestReaderProfileOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class RequestReaderProfileProcessorTest {
    @Mock
   private ReaderProfileRepository readerProfileRepository;
   @Mock
    private UserCredentialsRepository userCredentialsRepository;
   @Mock
   private NotificationRepository notificationRepository;
    private RequestReaderProfileProcessor requestReaderProfileProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        requestReaderProfileProcessor =SingletonFactory.getSingletonInstance(RequestReaderProfileProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class)).thenReturn(readerProfileRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class)).thenReturn(notificationRepository);

        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, requestReaderProfileProcessor);
        assertInstanceOf(RequestReaderProfileOperationModel.class, requestReaderProfileProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.requestReaderProfileProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
