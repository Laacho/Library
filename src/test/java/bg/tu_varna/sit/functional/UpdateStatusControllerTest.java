package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.admin.UpdateStatusController;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UpdateStatusControllerTest {

  private UpdateStatusController updateStatusController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        updateStatusController =new  UpdateStatusController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, updateStatusController);
        assertInstanceOf(AdminController.class, updateStatusController);
        assertInstanceOf(Initializable.class, updateStatusController);
    }

    @Test
    void testNotNul() {
        assertNotNull(updateStatusController);
    }
}
