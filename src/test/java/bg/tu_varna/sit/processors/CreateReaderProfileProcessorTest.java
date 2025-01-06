package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.add_book.CheckGenreProcessor;
import bg.tu_varna.sit.library.core.admin.approve_profiles.CreateReaderProfileProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.NotificationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.ReaderProfileRepositoryImp;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.ReaderProfileRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserRepository;
import bg.tu_varna.sit.library.models.add_genre.CheckGenreOperationModel;
import bg.tu_varna.sit.library.models.create_reader_profile.CreateReaderProfileOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class CreateReaderProfileProcessorTest {
    @Mock
    private  ReaderProfileRepository readerProfileRepository;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  NotificationRepository notificationRepository;
    private CreateReaderProfileProcessor createReaderProfileProcessor;
    @BeforeEach
    void setUp() throws Exception{
        SingletonFactory.init();
        createReaderProfileProcessor=SingletonFactory.getSingletonInstance(CreateReaderProfileProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserRepositoryImpl.class)).thenReturn(userRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class)).thenReturn(notificationRepository);
            singletonManagerMock.when(()->SingletonFactory.getSingletonInstance(ReaderProfileRepositoryImp.class)).thenReturn(readerProfileRepository);
        }
    }
    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class,createReaderProfileProcessor);
        assertInstanceOf(CreateReaderProfileOperationModel.class,createReaderProfileProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.createReaderProfileProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
