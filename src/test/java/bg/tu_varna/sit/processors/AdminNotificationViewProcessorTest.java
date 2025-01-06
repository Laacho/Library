package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.approve_books.UpdateBorrowedBooksProcessor;
import bg.tu_varna.sit.library.core.admin.notification_view.AdminNotificationViewProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.BookRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.GenreRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.NotificationRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.interfaces.BorrowedBooksRepository;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.models.notifications_view.AdminNotificationViewOperationModel;
import bg.tu_varna.sit.library.models.update_borrowed_books.UpdateBorrowedBooksOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

public class AdminNotificationViewProcessorTest {
    @Mock
    private NotificationRepository notificationRepository;

    private AdminNotificationViewProcessor adminNotificationViewProcessor;
    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        adminNotificationViewProcessor = SingletonFactory.getSingletonInstance(AdminNotificationViewProcessor.class);
    }
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(NotificationRepositoryImpl.class)).thenReturn(notificationRepository);
        }
    }
    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, adminNotificationViewProcessor);
        assertInstanceOf(AdminNotificationViewOperationModel.class, adminNotificationViewProcessor);
    }
    @Test
    void isAnnotationPresent() {
        boolean annotationPresent = this.adminNotificationViewProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(annotationPresent);
    }
}
