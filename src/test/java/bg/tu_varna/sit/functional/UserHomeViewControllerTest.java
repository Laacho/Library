package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.presentation.controllers.user.UserHomeViewController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserHomeViewControllerTest {

  private UserHomeViewController userHomeViewController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        userHomeViewController =new UserHomeViewController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(UserController.class, userHomeViewController);
        assertInstanceOf(Controller.class, userHomeViewController);
        assertInstanceOf(Initializable.class, userHomeViewController);
    }

    @Test
    void testNotNul() {
        assertNotNull(userHomeViewController);
    }
}
