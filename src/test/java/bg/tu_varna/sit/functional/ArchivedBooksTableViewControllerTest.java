package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.admin.ArchivedBooksTableViewController;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ArchivedBooksTableViewControllerTest {

  private ArchivedBooksTableViewController archivedBooksTableViewController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        archivedBooksTableViewController =new  ArchivedBooksTableViewController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, archivedBooksTableViewController);
        assertInstanceOf(AdminController.class, archivedBooksTableViewController);
        assertInstanceOf(Initializable.class, archivedBooksTableViewController);
    }

    @Test
    void testNotNul() {
        assertNotNull(archivedBooksTableViewController);
    }
}
