package bg.tu_varna.sit.library.presentation.controllers.base;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class UserController extends Controller {
    @FXML
    public void homeUser(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/user_home_view/pages/user-home-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void search(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/search_for_user/pages/search_for_user_view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void borrowBooks(ActionEvent actionEvent) throws IOException {

    }

    @FXML
    public void readerProfile(ActionEvent actionEvent) {

    }

    @FXML
    public void allBooks(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/user_all_books_view/pages/user-all-books-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void aboutUs(ActionEvent actionEvent) {

    }

    @FXML
    public void logout(ActionEvent actionEvent) {

    }

    @FXML
    public void myProfile(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/my_profile/pages/my_profile_view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void notifications(ActionEvent actionEvent) {

    }

    @FXML
    public void shoppingCart(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/borrow_cart/pages/borrow_cart_view.fxml");
        changeScene(actionEvent);
    }
}
