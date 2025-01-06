package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.LoginController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoginControllerTest {

  private LoginController loginController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        loginController =new LoginController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, loginController);
    }

    @Test
    void testNotNul() {
        assertNotNull(loginController);
    }
}
