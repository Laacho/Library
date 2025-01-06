package bg.tu_varna.sit.library.presentation.controllers.admin;

import bg.tu_varna.sit.library.core.admin.home_view.AdminHomeViewProcessor;
import bg.tu_varna.sit.library.models.admin_home_view.AdminHomeViewInputModel;
import bg.tu_varna.sit.library.models.admin_home_view.AdminHomeViewOperationModel;
import bg.tu_varna.sit.library.models.admin_home_view.AdminHomeViewOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static bg.tu_varna.sit.library.utils.ConstantsPaths.*;

public class AdminHomeViewController extends AdminController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private Button usersButton;
    @FXML
    private Button notificationButton;
    @FXML
    private Button allBooksButton;
    @FXML
    private Button archivedBooksButton;
    @FXML
    private Button discardedBooksButton;
    @FXML
    private Button readersProfilesButton;
    private final AdminHomeViewOperationModel adminHomeViewProcessor;

    public AdminHomeViewController() {
        adminHomeViewProcessor = SingletonFactory.getSingletonInstance(AdminHomeViewProcessor.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
        welcomeText.setText("Welcome, " + userSession.getUsername());
        Either<Exception, AdminHomeViewOutputModel> process = adminHomeViewProcessor.process(AdminHomeViewInputModel.builder().build());
        if (process.isRight()) {
            AdminHomeViewOutputModel output = process.get();
            usersButton.setText("(" + output.getCountUsers() + ") " + usersButton.getText());
            notificationButton.setText("(" + output.getCountNotifications() + ") " + notificationButton.getText());
            archivedBooksButton.setText("(" + output.getCountArchivedBooks() + ") " + archivedBooksButton.getText());
            discardedBooksButton.setText("(" + output.getCountDiscardedBooks() + ") " + discardedBooksButton.getText());
            allBooksButton.setText("(" + output.getCountAllBooks() + ") " + allBooksButton.getText());
            readersProfilesButton.setText("(" + output.getCountReadersProfiles() + ") " + readersProfilesButton.getText());
        }
        disableFocusOnButtons();
    }

    @FXML
    public void allBooks(ActionEvent actionEvent) throws IOException {
       // setPath("/bg/tu_varna/sit/library/presentation.views/admin/all_books_view/pages/all-books-view.fxml");
        setPath(ADMIN_ALL_BOOKS_VIEW);
        changeScene(actionEvent);
    }

    @FXML
    public void discardedBooks(ActionEvent actionEvent) throws IOException {
       // setPath("/bg/tu_varna/sit/library/presentation.views/admin/discarded_books_view/pages/discarded-books-view.fxml");
        setPath(ADMIN_DISCARDED_BOOKS);
        changeScene(actionEvent);
    }

    @FXML
    public void archivedBooks(ActionEvent actionEvent) throws IOException {
        //setPath("/bg/tu_varna/sit/library/presentation.views/admin/archived_books_view/pages/archived-books-view.fxml");
        setPath(ADMIN_ARCHIVED_BOOKS);
        changeScene(actionEvent);
    }

    @FXML
    public void usersTableView(ActionEvent actionEvent) throws IOException {
      //  setPath("/bg/tu_varna/sit/library/presentation.views/admin/users_table_view/pages/users-table-view.fxml");
        setPath(ADMIN_USERS_TABLE_VIEW);
        changeScene(actionEvent);
    }

    @FXML
    public void notification(ActionEvent actionEvent) throws IOException {
       // setPath("/bg/tu_varna/sit/library/presentation.views/admin/notification_view/pages/notification-view.fxml");
        setPath(ADMIN_NOTIFICATIONS);
        changeScene(actionEvent);
    }
    @FXML
    public void readersTableView(ActionEvent actionEvent) throws IOException {
        //setPath("/bg/tu_varna/sit/library/presentation.views/admin/reader_profile_view/pages/reader-profile-view.fxml");
        setPath(ADMIN_READERS_TABLE_VIEW);
        changeScene(actionEvent);
    }
}
