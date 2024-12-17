package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.core.approveProfiles.ApproveProfilesProcessor;
import bg.tu_varna.sit.library.core.approveProfiles.CreateReaderProfileProcessor;
import bg.tu_varna.sit.library.models.approve_profiles.ApproveProfilesInputModel;
import bg.tu_varna.sit.library.models.approve_profiles.ApproveProfilesOperationModel;
import bg.tu_varna.sit.library.models.approve_profiles.ApproveProfilesOutputModel;
import bg.tu_varna.sit.library.models.create_reader_profile.CreateReaderProfileInputModel;
import bg.tu_varna.sit.library.models.create_reader_profile.CreateReaderProfileOperationModel;
import bg.tu_varna.sit.library.models.create_reader_profile.CreateReaderProfileOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ApproveProfilesController extends Controller {
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
    public void approve(ActionEvent actionEvent) {
        if (userId == null) {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Empty id", "The user is not selected", ButtonType.OK);
            return;
        }
        Either<Exception, CreateReaderProfileOutputModel> process = createReaderProfileProcessor.process(CreateReaderProfileInputModel.builder().id(userId).build());
        if (process.isRight()) {
            String message = process.get().getMessage();
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Done ",message,ButtonType.OK);
            return;
        }
        AlertManager.showAlert(Alert.AlertType.ERROR,"Error","Error while trying to create reader profile.",ButtonType.OK);
    }
}
