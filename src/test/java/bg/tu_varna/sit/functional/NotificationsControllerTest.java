package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.presentation.controllers.user.NotificationsController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NotificationsControllerTest {

  private NotificationsController notificationsController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        notificationsController =new    NotificationsController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(UserController.class, notificationsController);
        assertInstanceOf(Controller.class, notificationsController);
        assertInstanceOf(Initializable.class, notificationsController);
    }

    @Test
    void testNotNul() {
        assertNotNull(notificationsController);
    }
}
