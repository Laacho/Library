package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.common.SingletonFactory;
import bg.tu_varna.sit.library.common.converters.base.ConversionService;
import bg.tu_varna.sit.library.core.register.RegisterProcessor;
import bg.tu_varna.sit.library.data.repositories.implementations.UserCredentialsRepositoryImpl;
import bg.tu_varna.sit.library.models.ExceptionManager;
import bg.tu_varna.sit.library.models.register.RegisterInputModel;
import bg.tu_varna.sit.library.models.register.RegisterOutputModel;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.lang.reflect.InvocationTargetException;

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
        this.registerProcessor = SingletonFactory.getSingletonInstance(RegisterProcessor.class);
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
