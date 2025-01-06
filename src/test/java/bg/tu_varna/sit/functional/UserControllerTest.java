package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserControllerTest {

  private UserController userController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        userController =new  UserController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, userController);
    }

    @Test
    void testNotNul() {
        assertNotNull(userController);
    }
}
