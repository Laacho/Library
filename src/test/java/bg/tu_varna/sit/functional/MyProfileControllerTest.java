package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.presentation.controllers.user.MyProfileController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MyProfileControllerTest {

  private MyProfileController myProfileController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        myProfileController =new   MyProfileController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(UserController.class, myProfileController);
        assertInstanceOf(Controller.class, myProfileController);
        assertInstanceOf(Initializable.class, myProfileController);
    }

    @Test
    void testNotNul() {
        assertNotNull(myProfileController);
    }
}
