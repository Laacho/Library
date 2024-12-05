package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.core.archived_books.ArchivedBooksProcessor;
import bg.tu_varna.sit.library.core.users_table_view.UsersTableViewProcessor;
import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.models.archived_books.ArchivedBooksInputModel;
import bg.tu_varna.sit.library.models.archived_books.ArchivedBooksOperationModel;
import bg.tu_varna.sit.library.models.archived_books.ArchivedBooksOutputModel;
import bg.tu_varna.sit.library.models.archived_books.BooksData;
import bg.tu_varna.sit.library.models.users_table_view.UsersData;
import bg.tu_varna.sit.library.models.users_table_view.UsersTableViewInputModel;
import bg.tu_varna.sit.library.models.users_table_view.UsersTableViewOperationModel;
import bg.tu_varna.sit.library.models.users_table_view.UsersTableViewOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import io.vavr.control.Either;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class UsersTableViewController extends Controller implements Initializable {
    @FXML
    private TableView<UsersData> tableView;
    private TableColumn<UsersData, String> firstName;
    private TableColumn<UsersData, String> lastName;
    private TableColumn<UsersData, String> username;
    private TableColumn<UsersData, String> email;
    private TableColumn<UsersData, Boolean> verified;
    private TableColumn<UsersData, Double> rating;
    private TableColumn<UsersData, LocalDate> birthdate;
    private TableColumn<UsersData, LocalDate> dateOfVerification;
    private final UsersTableViewOperationModel usersTableViewProcessor;

    public UsersTableViewController() {
        usersTableViewProcessor = SingletonFactory.getSingletonInstance(UsersTableViewProcessor.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Either<Exception, UsersTableViewOutputModel> process = usersTableViewProcessor.process(UsersTableViewInputModel.builder().build());
        if (process.isRight()) {
            List<UsersData> usersData = process.get().getUsersData();
            ObservableList<UsersData> observableList = FXCollections.observableList(usersData);
            initializeColumns();
            tableView.getColumns().addAll(firstName, lastName, birthdate, username, email, verified, dateOfVerification, rating);
            tableView.getColumns().remove(0);
            tableView.setItems(observableList);
        }
    }

    private void initializeColumns() {
        firstName = new TableColumn<>("Име");
        firstName.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getFirstName()));
        lastName = new TableColumn<>("Фамилия");
        lastName.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getLastName()));
        username = new TableColumn<>("Потр. име");
        username.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getUsername()));
        rating = new TableColumn<>("Рейтинг");
        rating.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getRating()));
        // price.setPrefWidth(35);
        email = new TableColumn<>("Email");
        email.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getEmail()));
        verified = new TableColumn<>("Верифициран");
        verified.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getVerified()));
        birthdate = new TableColumn<>("Дата на раждане");
        //birthdate.setPrefWidth(50);
        birthdate.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getBirthdate()));
        dateOfVerification = new TableColumn<>("Дата на верификация");
       // dateOfVerification.setPrefWidth(50);
        dateOfVerification.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getDateOfVerification()));
    }

    @FXML
    public void home(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin_home_view/pages/admin-home-view.fxml");
        changeScene(actionEvent);
    }
}
