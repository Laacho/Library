package bg.tu_varna.sit.library.presentation.controllers.admin;

import bg.tu_varna.sit.library.core.admin.reader_profile.ReaderProfileTableViewProcessor;
import bg.tu_varna.sit.library.models.all_books.BooksData;
import bg.tu_varna.sit.library.models.reader_profile_table_view.ReaderProfileData;
import bg.tu_varna.sit.library.models.reader_profile_table_view.ReaderProfileTableViewInputModel;
import bg.tu_varna.sit.library.models.reader_profile_table_view.ReaderProfileTableViewOperationModel;
import bg.tu_varna.sit.library.models.reader_profile_table_view.ReaderProfileTableViewOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import io.vavr.control.Either;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ReaderProfileTableViewController extends AdminController implements Initializable {
    @FXML
    private TableView<ReaderProfileData> tableView;
    private TableColumn<ReaderProfileData, String> username;
    private TableColumn<ReaderProfileData, String> createdAt;
    private TableColumn<ReaderProfileData, String> updatedAt;
    private TableColumn<ReaderProfileData, Button> approve;
    private final ReaderProfileTableViewOperationModel readerProfileTableViewProcessor;

    public ReaderProfileTableViewController() {
        readerProfileTableViewProcessor = SingletonFactory.getSingletonInstance(ReaderProfileTableViewProcessor.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Either<Exception, ReaderProfileTableViewOutputModel> process = readerProfileTableViewProcessor.process(ReaderProfileTableViewInputModel.builder().build());
        if (process.isRight()) {
            List<ReaderProfileData> readerProfileData = process.get().getReaderProfileData();
            ObservableList<ReaderProfileData> observableList = FXCollections.observableList(readerProfileData);
            initializeColumns();
            tableView.getColumns().addAll(username, createdAt, updatedAt, approve);
            tableView.getColumns().remove(0);
            tableView.setItems(observableList);
        }
    }

    private void initializeColumns() {
        username = new TableColumn<>("Username");
        username.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getUsername()));
        // username.setPrefWidth(200);
        createdAt = new TableColumn<>("Created At");
        //createdAt.setPrefWidth(50);
        createdAt.setCellValueFactory(cell -> {
            String result = (cell.getValue().getCreatedAt() != null)
                    ? cell.getValue().getCreatedAt().toString()
                    : "Only requested";
            return new SimpleObjectProperty<>(result);
        });
        updatedAt = new TableColumn<>("Updated At");
        updatedAt.setCellValueFactory(cell -> {
            String result = (cell.getValue().getUpdatedAt() != null)
                    ? cell.getValue().getUpdatedAt().toString()
                    : "Only requested";;
            return new SimpleObjectProperty<>(result);
        });
        approve = new TableColumn<>("Approve");
        approve.setCellValueFactory(cell -> {
            boolean approved = cell.getValue().isApproved();
            if (!approved) {
                Button button = new Button("Approve");
                button.setOnAction(event -> {
                    setFunctionality(cell, event);
                });
                return new SimpleObjectProperty<>(button);
            }
            return new SimpleObjectProperty<>();
        });
    }

    private void setFunctionality(TableColumn.CellDataFeatures<ReaderProfileData, Button> cell, ActionEvent event) {
        ApproveProfilesController.usernameFromTable = cell.getValue().getUsername();
        try {
            approveProfiles(event);
        } catch (IOException e) {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage(), ButtonType.OK);
        }
    }
}
