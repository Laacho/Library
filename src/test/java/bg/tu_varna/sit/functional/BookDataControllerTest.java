package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.presentation.controllers.user.BookDataController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BookDataControllerTest {

  private BookDataController bookDataController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        bookDataController =new  BookDataController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(UserController.class, bookDataController);
        assertInstanceOf(Controller.class, bookDataController);
        assertInstanceOf(Initializable.class, bookDataController);
    }

    @Test
    void testNotNul() {
        assertNotNull(bookDataController);
    }
}
