package bg.tu_varna.sit.functional;

import bg.tu_varna.sit.library.presentation.controllers.admin.BookDataViewController;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookDataViewControllerTest {

  private BookDataViewController bookDataViewController;

    @BeforeEach
    void setUp() throws Exception {
        SingletonFactory.init();
        bookDataViewController =new  BookDataViewController();
    }

    @Test
    void getInstance() {
        assertInstanceOf(Controller.class, bookDataViewController);
        assertInstanceOf(AdminController.class, bookDataViewController);
        assertFalse(bookDataViewController instanceof Initializable);
    }

    @Test
    void testNotNul() {
        assertNotNull(bookDataViewController);
    }
}
