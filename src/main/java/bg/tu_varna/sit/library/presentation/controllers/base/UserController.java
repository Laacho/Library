package bg.tu_varna.sit.library.presentation.controllers.base;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class UserController extends Controller {
    @FXML
    public void homeUser(ActionEvent actionEvent) {

    }

    @FXML
    public void search(ActionEvent actionEvent) {

    }

    @FXML
    public void borrowBooks(ActionEvent actionEvent) throws IOException {
            setPath("/bg/tu_varna/sit/library/presentation.views/borrow_cart/pages/borrow_cart_view.fxml");
            changeScene(actionEvent);
    }

    @FXML
    public void readerProfile(ActionEvent actionEvent) {

    }

    @FXML
    public void allBooks(ActionEvent actionEvent) {

    }

    @FXML
    public void aboutUs(ActionEvent actionEvent) {

    }

    @FXML
    public void logout(ActionEvent actionEvent) {

    }

    @FXML
    public void myProfile(ActionEvent actionEvent) {

    }

    @FXML
    public void notifications(ActionEvent actionEvent) {

    }

    @FXML
    public void shoppingCart(ActionEvent actionEvent) {

    }
}
