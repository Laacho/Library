package bg.tu_varna.sit.library.presentation.controllers.base;

import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.session.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import static bg.tu_varna.sit.library.utils.ConstantsPaths.*;
public class UserController extends Controller {

    @FXML
    private Button homeButton;
    @FXML
    private Button search;
    @FXML
    private Button borrowBooks;
    @FXML
    private Button readerProfile;
    @FXML
    private Button allBooks;
    @FXML
    private Button aboutUs;
    @FXML
    private Button logout;

    public void disableFocusOnButtons(){
        homeButton.setFocusTraversable(false);
        search.setFocusTraversable(false);
        borrowBooks.setFocusTraversable(false);
        readerProfile.setFocusTraversable(false);
        allBooks.setFocusTraversable(false);
        aboutUs.setFocusTraversable(false);
        logout.setFocusTraversable(false);
    }
    @FXML
    public void homeUser(ActionEvent actionEvent) throws IOException {
      //  setPath("/bg/tu_varna/sit/library/presentation.views/user/user_home_view/pages/user-home-view.fxml");
        setPath(USER_HOME_VIEW);
        changeScene(actionEvent);
    }

    @FXML
    public void search(ActionEvent actionEvent) throws IOException {
       // setPath("/bg/tu_varna/sit/library/presentation.views/user/search_for_user/pages/search-for-user-view.fxml");
        setPath(USER_SEARCH_FOR_USER_VIEW);
        changeScene(actionEvent);
    }

    @FXML
    public void borrowBooks(ActionEvent actionEvent) throws IOException {

    }

    @FXML
    public void readerProfile(ActionEvent actionEvent) throws IOException {
       // setPath("/bg/tu_varna/sit/library/presentation.views/user/reader_profile/pages/reader-profile-view.fxml");
        setPath(USER_READER_PROFILE_VIEW);
        changeScene(actionEvent);
    }

    @FXML
    public void allBooks(ActionEvent actionEvent) throws IOException {
      //  setPath("/bg/tu_varna/sit/library/presentation.views/user/user_all_books_view/pages/user-all-books-view.fxml");
        setPath(USER_ALL_BOOKS_VIEW);
        changeScene(actionEvent);
    }

    @FXML
    public void aboutUs(ActionEvent actionEvent) throws IOException {
       // setPath("/bg/tu_varna/sit/library/presentation.views/user/about_us/pages/about-us-view.fxml");
        setPath(USER_ABOUT_US_VIEW);
        changeScene(actionEvent);
    }

    @FXML
    public void logout(ActionEvent actionEvent) throws IOException {
        //setPath("/bg/tu_varna/sit/library/presentation.views/library_home_view/pages/library-home-view.fxml");
        setPath(LIBRARY_HOME_VIEW);
        UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
        userSession.setWantsToLogout(true);
        changeScene(actionEvent);
    }

    @FXML
    public void myProfile(ActionEvent actionEvent) throws IOException {
        //setPath("/bg/tu_varna/sit/library/presentation.views/user/my_profile/pages/my-profile-view.fxml");
        setPath(USER_MY_PROFILE_VIEW);
        changeScene(actionEvent);
    }

    @FXML
    public void notifications(ActionEvent actionEvent) throws IOException {
       // setPath("/bg/tu_varna/sit/library/presentation.views/user/notifications/pages/notifications-view.fxml");
        setPath(USER_NOTIFICATIONS_VIEW);
        changeScene(actionEvent);
    }

    @FXML
    public void shoppingCart(ActionEvent actionEvent) throws IOException {
        //setPath("/bg/tu_varna/sit/library/presentation.views/user/borrow_cart/pages/borrow-cart-view.fxml");
        setPath(USER_BORROW_CART_VIEW);
        changeScene(actionEvent);
    }
}
