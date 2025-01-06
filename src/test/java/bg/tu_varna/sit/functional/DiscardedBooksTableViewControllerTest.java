package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.admin.DiscardedBooksTableViewController;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DiscardedBooksTableViewControllerTest {

  private DiscardedBooksTableViewController discardedBooksTableViewController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        discardedBooksTableViewController =new  DiscardedBooksTableViewController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, discardedBooksTableViewController);
        assertInstanceOf(AdminController.class, discardedBooksTableViewController);
        assertInstanceOf(Initializable.class, discardedBooksTableViewController);
    }

    @Test
    void testNotNul() {
        assertNotNull(discardedBooksTableViewController);
    }
}
