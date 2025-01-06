package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.admin.AddBookController;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AddBookControllerTest {

  private AddBookController addBookController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        addBookController =new AddBookController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, addBookController);
        assertInstanceOf(AdminController.class, addBookController);
        assertInstanceOf(Initializable.class, addBookController);
    }

    @Test
    void testNotNul() {
        assertNotNull(addBookController);
    }
}
