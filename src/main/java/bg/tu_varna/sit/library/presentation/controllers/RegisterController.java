package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.core.register.RegisterProcessor;
import bg.tu_varna.sit.library.models.register.RegisterInputModel;
import bg.tu_varna.sit.library.models.register.RegisterOperationModel;
import bg.tu_varna.sit.library.models.register.RegisterOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class RegisterController extends Controller {

    private final RegisterOperationModel registerProcessor;
    @FXML
    private TextField email;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField username;
    @FXML
    private DatePicker birthdate;
    @FXML
    private PasswordField password;
    @FXML
    private Button registerButton;

    public RegisterController() {
        this.registerProcessor = SingletonFactory.getSingletonInstance(RegisterProcessor.class);
    }

    @FXML
    public void register(ActionEvent actionEvent) throws IOException {
        RegisterInputModel build = RegisterInputModel.builder()
                .birthdate(birthdate.getValue())
                .password(password.getText())
                .firstName(firstName.getText())
                .lastName(lastName.getText())
                .email(email.getText())
                .username(username.getText())
                .build();
        Either<Exception, RegisterOutputModel> process = registerProcessor.process(build);
        if(process.isRight()){
            setPath("/bg/tu_varna/sit/library/presentation.views/confirm_registration/pages/confirm-registration.fxml");
            changeScene(actionEvent);
        }
    }

    @FXML
    public void registerWithEnter(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            registerButton.fire();
        }
    }
}
