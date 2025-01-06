package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.admin.SettingsController;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SettingsControllerTest {

  private SettingsController settingsController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        settingsController =new  SettingsController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, settingsController);
        assertInstanceOf(AdminController.class, settingsController);
        assertInstanceOf(Initializable.class, settingsController);
    }

    @Test
    void testNotNul() {
        assertNotNull(settingsController);
    }
}
