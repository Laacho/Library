package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.admin.ApproveProfilesController;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ApproveProfilesControllerTest {

  private ApproveProfilesController approveProfilesController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        approveProfilesController =new  ApproveProfilesController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, approveProfilesController);
        assertInstanceOf(AdminController.class, approveProfilesController);
        assertInstanceOf(Initializable.class, approveProfilesController);
    }

    @Test
    void testNotNul() {
        assertNotNull(approveProfilesController);
    }
}
