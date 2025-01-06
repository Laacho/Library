package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.presentation.controllers.user.BorrowCartController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BorrowCartControllerTest {

  private BorrowCartController borrowCartController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        borrowCartController =new   BorrowCartController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(UserController.class, borrowCartController);
        assertInstanceOf(Controller.class, borrowCartController);
        assertInstanceOf(Initializable.class, borrowCartController);
    }

    @Test
    void testNotNul() {
        assertNotNull(borrowCartController);
    }
}
