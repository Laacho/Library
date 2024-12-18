package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.core.admin_hello_view.AdminHelloViewProcessor;
import bg.tu_varna.sit.library.models.admin_home_view.AdminHomeViewInputModel;
import bg.tu_varna.sit.library.models.admin_home_view.AdminHomeViewOperationModel;
import bg.tu_varna.sit.library.models.admin_home_view.AdminHomeViewOutputModel;
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

public class AdminHomeViewController extends Controller implements Initializable {
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
    private Button homeButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button readersProfilesButton;
    private final AdminHomeViewOperationModel adminHomeViewProcessor;

    public AdminHomeViewController() {
        adminHomeViewProcessor = SingletonFactory.getSingletonInstance(AdminHelloViewProcessor.class);
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
    }

    @FXML
    public void allBooks(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/all_books_view/pages/all-books-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void discardedBooks(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/discarded_books_view/pages/discarded-books-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void archivedBooks(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/archived_books_view/pages/archived-books-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void usersTableView(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/users__table_view/pages/users--table-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void notification(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/notification_view/pages/notification-view.fxml");
        changeScene(actionEvent);
    }
}
