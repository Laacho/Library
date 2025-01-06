package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.presentation.controllers.user.UserAllBooksController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserAllBooksControllerTest {

  private UserAllBooksController userAllBooksController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        userAllBooksController =new UserAllBooksController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(UserController.class, userAllBooksController);
        assertInstanceOf(Controller.class, userAllBooksController);
        assertInstanceOf(Initializable.class, userAllBooksController);
    }

    @Test
    void testNotNul() {
        assertNotNull(userAllBooksController);
    }
}
