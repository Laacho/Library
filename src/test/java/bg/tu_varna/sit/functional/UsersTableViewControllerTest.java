package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.admin.UsersTableViewController;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UsersTableViewControllerTest {

  private UsersTableViewController usersTableViewController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        usersTableViewController =new  UsersTableViewController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, usersTableViewController);
        assertInstanceOf(AdminController.class, usersTableViewController);
        assertInstanceOf(Initializable.class, usersTableViewController);
    }

    @Test
    void testNotNul() {
        assertNotNull(usersTableViewController);
    }
}
