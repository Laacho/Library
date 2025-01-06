package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.presentation.controllers.user.AboutUsController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AboutUsControllerTest {

  private AboutUsController aboutUsController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        aboutUsController =new  AboutUsController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(UserController.class, aboutUsController);
        assertInstanceOf(Controller.class, aboutUsController);
    }

    @Test
    void testNotNul() {
        assertNotNull(aboutUsController);
    }
}
