package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.admin.ApproveBookController;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ApproveBookControllerTest {

  private ApproveBookController approveBookController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        approveBookController =new  ApproveBookController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, approveBookController);
        assertInstanceOf(AdminController.class, approveBookController);
        assertInstanceOf(Initializable.class, approveBookController);
    }

    @Test
    void testNotNul() {
        assertNotNull(approveBookController);
    }
}
