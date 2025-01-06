package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.admin.SearchController;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SearchControllerTest {

  private SearchController searchController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        searchController =new  SearchController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, searchController);
        assertInstanceOf(AdminController.class, searchController);
        assertInstanceOf(Initializable.class, searchController);
    }

    @Test
    void testNotNul() {
        assertNotNull(searchController);
    }
}
