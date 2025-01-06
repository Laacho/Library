package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.HelloController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HelloControllerTest {

  private HelloController helloController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        helloController =new HelloController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, helloController);
        assertInstanceOf(Initializable.class, helloController);
    }

    @Test
    void testNotNul() {
        assertNotNull(helloController);
    }
}
