package bg.tu_varna.sit.library.presentation.controllers.admin;

import bg.tu_varna.sit.library.core.users_table_view.UsersTableViewProcessor;
import bg.tu_varna.sit.library.models.users_table_view.UsersData;
import bg.tu_varna.sit.library.models.users_table_view.UsersTableViewInputModel;
import bg.tu_varna.sit.library.models.users_table_view.UsersTableViewOperationModel;
import bg.tu_varna.sit.library.models.users_table_view.UsersTableViewOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import io.vavr.control.Either;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class UsersTableViewController extends AdminController implements Initializable {
    @FXML
    private TableView<UsersData> tableView;
    private TableColumn<UsersData, String> firstName;
    private TableColumn<UsersData, String> lastName;
    private TableColumn<UsersData, String> username;
    private TableColumn<UsersData, String> email;
    private TableColumn<UsersData, String> rating;
    private TableColumn<UsersData, LocalDate> birthdate;
    private TableColumn<UsersData, String> dateOfVerification;
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
            tableView.getColumns().addAll(firstName, lastName, birthdate, username, email, dateOfVerification, rating);
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
        rating.setCellValueFactory(cell -> {
            if(cell.getValue().getRating() == null)
                return new SimpleObjectProperty<>("No reader profile");
            return new SimpleObjectProperty<>(cell.getValue().getRating().toString());
        });
        // price.setPrefWidth(35);
        email = new TableColumn<>("Email");
        email.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getEmail()));
        birthdate = new TableColumn<>("Дата на раждане");
        //birthdate.setPrefWidth(50);
        birthdate.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getBirthdate()));
        dateOfVerification = new TableColumn<>("Верифициран");
        // dateOfVerification.setPrefWidth(50);
        dateOfVerification.setCellValueFactory(cell -> {
            if (cell.getValue().getDateOfVerification() == null) {
                return new SimpleObjectProperty<>("Not verified yet!");
            }
            return new SimpleObjectProperty<>(cell.getValue().getDateOfVerification().toString());
        });
    }

}
