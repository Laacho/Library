package bg.tu_varna.sit.processors;

import bg.tu_varna.sit.library.core.BaseProcessor;
import bg.tu_varna.sit.library.core.admin.notification_view.UpdateNotificationsForAdminProcessor;
import bg.tu_varna.sit.library.data.repositories.interfaces.NotificationRepository;
import bg.tu_varna.sit.library.models.update_notifications_for_admin.UpdateNotificationsForAdminOperationModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.annotations.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class UpdateNotificationsForAdminTest {
    @Mock
    private NotificationRepository notificationRepository;

    private UpdateNotificationsForAdminProcessor updateNotificationsForAdminProcessor;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        updateNotificationsForAdminProcessor = SingletonFactory.getSingletonInstance(UpdateNotificationsForAdminProcessor.class);
    }

    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonFactory> singletonManagerMock = mockStatic(SingletonFactory.class)) {
            singletonManagerMock.when(() -> SingletonFactory.getSingletonInstance(NotificationRepository.class))
                    .thenReturn(notificationRepository);
        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, updateNotificationsForAdminProcessor);
        assertInstanceOf(UpdateNotificationsForAdminOperationModel.class, updateNotificationsForAdminProcessor);
    }

    @Test
    void isAnnotationPresent() {
        boolean annotationPresent = this.updateNotificationsForAdminProcessor.getClass().isAnnotationPresent(Processor.class);
        assertTrue(annotationPresent);
    }

}
