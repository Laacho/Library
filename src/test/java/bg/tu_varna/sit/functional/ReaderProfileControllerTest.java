package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.presentation.controllers.user.ReaderProfileController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReaderProfileControllerTest {

  private ReaderProfileController readerProfileController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        readerProfileController =new    ReaderProfileController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(UserController.class, readerProfileController);
        assertInstanceOf(Controller.class, readerProfileController);
        assertInstanceOf(Initializable.class, readerProfileController);
    }

    @Test
    void testNotNul() {
        assertNotNull(readerProfileController);
    }
}
