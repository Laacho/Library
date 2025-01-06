package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.presentation.controllers.user.SearchForUserController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SearchForUserControllerTest {

  private SearchForUserController searchForUserController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        searchForUserController =new SearchForUserController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(UserController.class, searchForUserController);
        assertInstanceOf(Controller.class, searchForUserController);
        assertInstanceOf(Initializable.class, searchForUserController);
    }

    @Test
    void testNotNul() {
        assertNotNull(searchForUserController);
    }
}
