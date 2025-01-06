package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.admin.AllBooksTableController;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AllBooksTableControllerTest {

  private AllBooksTableController allBooksTableController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        allBooksTableController =new AllBooksTableController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, allBooksTableController);
        assertInstanceOf(AdminController.class, allBooksTableController);
        assertInstanceOf(Initializable.class, allBooksTableController);
    }

    @Test
    void testNotNul() {
        assertNotNull(allBooksTableController);
    }
}
