package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.admin.ReturnBookController;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReturnBookControllerTest {

  private ReturnBookController returnBookController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        returnBookController =new  ReturnBookController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, returnBookController);
        assertInstanceOf(AdminController.class, returnBookController);
        assertInstanceOf(Initializable.class, returnBookController);
    }

    @Test
    void testNotNul() {
        assertNotNull(returnBookController);
    }
}
