package bg.tu_varna.sit.library.presentation.controllers.base;

import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.session.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class UserController extends Controller {
    @FXML
    public void homeUser(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/user/user_home_view/pages/user-home-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void search(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/user/search_for_user/pages/search_for_user_view.fxml");
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
        setPath("/bg/tu_varna/sit/library/presentation.views/user/user_all_books_view/pages/user-all-books-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void aboutUs(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/user/about_us/pages/about_us_view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void logout(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/hello_view/pages/hello-view.fxml");
        UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
        userSession.setWantsToLogout(true);
        changeScene(actionEvent);
    }

    @FXML
    public void myProfile(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/user/my_profile/pages/my_profile_view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void notifications(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/user/notifications/pages/notifications_view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void shoppingCart(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/user/borrow_cart/pages/borrow_cart_view.fxml");
        changeScene(actionEvent);
    }
}
