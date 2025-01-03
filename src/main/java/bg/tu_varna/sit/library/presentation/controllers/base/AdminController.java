package bg.tu_varna.sit.library.presentation.controllers.base;

import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.session.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AdminController extends Controller {
    @FXML
    private Button homeButton;
    @FXML
    private Button search;
    @FXML
    private Button addBook;
    @FXML
    private Button updateStatus;
    @FXML
    private Button approveBooks;
    @FXML
    private Button approveProfiles;
    @FXML
    private Button overdueBooks;
    @FXML
    private Button returnBooks;
    @FXML
    private Button sendEmail;

    public void disableFocusOnButtons(){
        homeButton.setFocusTraversable(false);
        search.setFocusTraversable(false);
        addBook.setFocusTraversable(false);
        updateStatus.setFocusTraversable(false);
        approveBooks.setFocusTraversable(false);
        approveProfiles.setFocusTraversable(false);
        overdueBooks.setFocusTraversable(false);
        returnBooks.setFocusTraversable(false);
        sendEmail.setFocusTraversable(false);
    }
    @FXML
    public void homeAdmin(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/home_view/pages/admin-home-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void addBook(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/add_book/pages/add-book-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void updateStatus(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/update_status/pages/update-status-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void approveBooks(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/approve_books/pages/approve-books-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void approveProfiles(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/approve_profiles_view/pages/approve-profiles.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void overdueBooks(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/overdue_books_view/pages/overdue-books.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void returnBooks(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/return_book_view/pages/return-books.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void sendEmail(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/send_email_view/pages/send-email-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void changeToSetting(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/settings/pages/settings-view.fxml");
        changeScene(actionEvent);
    }
    @FXML
    public void search(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/search/pages/search-view.fxml");
        changeScene(actionEvent);
    }
    @FXML
    public void logout(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/library_home_view/pages/library-home-view.fxml");
        UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
        userSession.setWantsToLogout(true);
        changeScene(actionEvent);
    }
}
