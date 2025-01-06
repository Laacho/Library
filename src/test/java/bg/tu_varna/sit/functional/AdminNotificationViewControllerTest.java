package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.admin.AdminNotificationViewController;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AdminNotificationViewControllerTest {

  private AdminNotificationViewController adminNotificationViewController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        adminNotificationViewController =new AdminNotificationViewController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, adminNotificationViewController);
        assertInstanceOf(AdminController.class, adminNotificationViewController);
        assertInstanceOf(Initializable.class, adminNotificationViewController);
    }

    @Test
    void testNotNul() {
        assertNotNull(adminNotificationViewController);
    }
}
