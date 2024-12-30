package bg.tu_varna.sit.library.presentation.controllers.base;

import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.session.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class AdminController extends Controller {
    @FXML
    public void homeAdmin(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/admin_home_view/pages/admin-home-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void addBook(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/addBook/pages/addBook-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void updateStatus(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/update_status/pages/update_status_view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void approveBooks(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/approve_books/pages/approve -books-view.fxml");
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
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/send_email_view/pages/send_email_view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void changeToSetting(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/settings/pages/settings_view.fxml");
        changeScene(actionEvent);
    }
    @FXML
    public void search(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin/search/pages/search-view.fxml");
        changeScene(actionEvent);
    }
    @FXML
    public void logout(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/hello_view/pages/hello-view.fxml");
        UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
        userSession.setWantsToLogout(true);
        changeScene(actionEvent);
    }
}
