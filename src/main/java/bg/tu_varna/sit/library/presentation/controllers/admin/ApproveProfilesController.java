package bg.tu_varna.sit.library.presentation.controllers.admin;

import bg.tu_varna.sit.library.core.admin.approve_profiles.ApproveProfilesProcessor;
import bg.tu_varna.sit.library.core.admin.approve_profiles.CreateReaderProfileProcessor;
import bg.tu_varna.sit.library.models.approve_profiles.ApproveProfilesInputModel;
import bg.tu_varna.sit.library.models.approve_profiles.ApproveProfilesOperationModel;
import bg.tu_varna.sit.library.models.approve_profiles.ApproveProfilesOutputModel;
import bg.tu_varna.sit.library.models.create_reader_profile.CreateReaderProfileInputModel;
import bg.tu_varna.sit.library.models.create_reader_profile.CreateReaderProfileOperationModel;
import bg.tu_varna.sit.library.models.create_reader_profile.CreateReaderProfileOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ApproveProfilesController extends AdminController implements Initializable {
    @FXML
    private Button searchButton;
    @FXML
    private Label id;
    @FXML
    private Label username;
    @FXML
    private Label email;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label birthdate;
    @FXML
    private TextField searchTextField;
    public static String usernameFromTable;
    private Long userId;
    private final ApproveProfilesOperationModel approveProfilesProcessor;
    private final CreateReaderProfileOperationModel createReaderProfileProcessor;

    public ApproveProfilesController() {
        approveProfilesProcessor = SingletonFactory.getSingletonInstance(ApproveProfilesProcessor.class);
        createReaderProfileProcessor = SingletonFactory.getSingletonInstance(CreateReaderProfileProcessor.class);
    }


    @FXML
    public void searchUser(ActionEvent actionEvent) {
        if (searchTextField.getText().isEmpty()) {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Can't be empty", "The username can not be empty", ButtonType.OK);
            return;
        }
        search();

    }

    private void search() {
        Either<Exception, ApproveProfilesOutputModel> process = approveProfilesProcessor.process(ApproveProfilesInputModel.builder().username(searchTextField.getText()).build());
        if (process.isRight()) {
            ApproveProfilesOutputModel output = process.get();
            userId = output.getId();
            id.setText("Потребителско ID " + output.getId());
            firstName.setText("Име: " + output.getFirstName());
            lastName.setText("Фамилия: " + output.getLastName());
            username.setText("Потребителско име: " + output.getUsername());
            email.setText("Имейл: " + output.getEmail());
            birthdate.setText("Рожден ден: " + output.getBirthdate().toString());
        }
    }

    @FXML
    public void approve(ActionEvent actionEvent) throws IOException {
        if (userId == null) {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Empty id", "The user is not selected", ButtonType.OK);
            return;
        }
        Either<Exception, CreateReaderProfileOutputModel> process = createReaderProfileProcessor.process(CreateReaderProfileInputModel.builder().id(userId).build());
        if (process.isRight()) {
            String message = process.get().getMessage();
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Done ",message,ButtonType.OK);
        }
        approveProfiles(actionEvent);
    }
    @FXML
    public void searchWithEnter(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            searchButton.fire();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disableFocusOnButtons();
        if(usernameFromTable !=null && !usernameFromTable.isEmpty()) {
            searchTextField.setText(usernameFromTable);
            search();
        }
    }
}
