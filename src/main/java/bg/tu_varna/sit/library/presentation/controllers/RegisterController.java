package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.common.converters.common.ConversionService;
import bg.tu_varna.sit.library.core.register.RegisterProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.data.repositories.implementations.UserRepositoryImpl;
import bg.tu_varna.sit.library.models.ExceptionManager;
import bg.tu_varna.sit.library.models.register.RegisterInputModel;
import bg.tu_varna.sit.library.models.register.RegisterOutputModel;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController extends Controller {

    private final RegisterProcessor registerProcessor;
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

    public RegisterController() {
        this.registerProcessor = new RegisterProcessor(new ConversionService(),new UserCredentialsRepositoryImpl(),new UserRepositoryImpl(),new ExceptionManager());
    }

    @FXML
    public void register() {
        RegisterInputModel build = RegisterInputModel.builder()
                .birthdate(birthdate.getValue())
                .password(password.getText())
                .firstName(firstName.getText())
                .lastName(lastName.getText())
                .email(email.getText())
                .username(username.getText())
                .build();
        Either<Exception, RegisterOutputModel> process = registerProcessor.process(build);
        System.out.println(process.get());
    }

}
