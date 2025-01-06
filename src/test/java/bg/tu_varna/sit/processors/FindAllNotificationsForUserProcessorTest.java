package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.user.notifications.FindAllNotificationsForUserProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.UserCredentialsRepository;
import bg.tu_varna.sit.library.models.find_all_notifications_for_user.FindAllNotificationsForUserOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class FindAllNotificationsForUserProcessorTest {
    @Mock
   private NotificationRepository notificationRepository;
   @Mock
    private UserCredentialsRepository userCredentialsRepository;
    private FindAllNotificationsForUserProcessor findAllNotificationsForUserProcessor;
    @BeforeEach
    public void setUp() throws Exception {
        SingletonFactory.init();
        findAllNotificationsForUserProcessor =SingletonFactory.getSingletonInstance(FindAllNotificationsForUserProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(UserCredentialsRepositoryImpl.class)).thenReturn(userCredentialsRepository);
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(BookRepositoryImpl.class)).thenReturn(notificationRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, findAllNotificationsForUserProcessor);
        assertInstanceOf(FindAllNotificationsForUserOperationModel.class, findAllNotificationsForUserProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.findAllNotificationsForUserProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(hasAnnotation);
    }

}
