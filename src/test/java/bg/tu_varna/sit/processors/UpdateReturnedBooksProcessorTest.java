package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.return_books.ReturnBookProcessor;
import bg.tu_varna.sit.library.core.admin.return_books.UpdateReturnedBooksProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.BorrowedBooksRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.NotificationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserRepository;
import bg.tu_varna.sit.library.models.return_books.ReturnBooksOperationModel;
import bg.tu_varna.sit.library.models.update_returned_books.UpdateReturnedBooksOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class UpdateReturnedBooksProcessorTest {

    @Mock
    private BorrowedBooksRepository borrowedBooksRepository;
    @Mock
    private UserCredentialsRepository userCredentialsRepository;

    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private UserRepository userRepository;

    private UpdateReturnedBooksProcessor updateReturnedBooksProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        updateReturnedBooksProcessor =SingletonFactory.getSingletonInstance(UpdateReturnedBooksProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BorrowedBooksRepositoryImpl.class)).thenReturn(borrowedBooksRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class)).thenReturn(notificationRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserRepositoryImpl.class)).thenReturn(userRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, updateReturnedBooksProcessor);
        assertInstanceOf(UpdateReturnedBooksOperationModel.class, updateReturnedBooksProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.updateReturnedBooksProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
