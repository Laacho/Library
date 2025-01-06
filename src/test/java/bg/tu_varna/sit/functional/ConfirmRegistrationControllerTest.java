package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.ConfirmRegistrationController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConfirmRegistrationControllerTest {

  private ConfirmRegistrationController confirmRegistrationController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        confirmRegistrationController =new ConfirmRegistrationController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, confirmRegistrationController);
        assertInstanceOf(Initializable.class, confirmRegistrationController);
    }

    @Test
    void testNotNul() {
        assertNotNull(confirmRegistrationController);
    }
}
