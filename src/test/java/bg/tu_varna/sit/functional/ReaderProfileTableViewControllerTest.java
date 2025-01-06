package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.admin.ReaderProfileTableViewController;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReaderProfileTableViewControllerTest {

  private ReaderProfileTableViewController readerProfileTableViewController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        readerProfileTableViewController =new  ReaderProfileTableViewController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, readerProfileTableViewController);
        assertInstanceOf(AdminController.class, readerProfileTableViewController);
        assertInstanceOf(Initializable.class, readerProfileTableViewController);
    }

    @Test
    void testNotNul() {
        assertNotNull(readerProfileTableViewController);
    }
}
