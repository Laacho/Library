package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.admin.OverdueBooksController;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OverdueBooksControllerTest {

  private OverdueBooksController overdueBooksController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        overdueBooksController =new  OverdueBooksController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, overdueBooksController);
        assertInstanceOf(AdminController.class, overdueBooksController);
        assertInstanceOf(Initializable.class, overdueBooksController);
    }

    @Test
    void testNotNul() {
        assertNotNull(overdueBooksController);
    }
}
