package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.admin.SendEmailController;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SendEmailControllerTest {

  private SendEmailController sendEmailController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        sendEmailController =new  SendEmailController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, sendEmailController);
        assertInstanceOf(AdminController.class, sendEmailController);
        assertInstanceOf(Initializable.class, sendEmailController);
    }

    @Test
    void testNotNul() {
        assertNotNull(sendEmailController);
    }
}
