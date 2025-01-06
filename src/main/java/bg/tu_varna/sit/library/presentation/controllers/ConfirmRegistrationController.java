package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.core.logging.confirm_registration.ConfirmRegistrationProcessor;
import bg.tu_varna.sit.library.models.confirm_registration.ConfirmRegistrationInputModel;
import bg.tu_varna.sit.library.models.confirm_registration.ConfirmRegistrationOperationModel;
import bg.tu_varna.sit.library.models.confirm_registration.ConfirmRegistrationOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static bg.tu_varna.sit.library.utils.ConstantsPaths.USER_HOME_VIEW;

public class ConfirmRegistrationController extends Controller implements Initializable {


    @FXML
    private TextField textFieldFirst;
    @FXML
    private TextField textFieldSecond;
    @FXML
    private TextField textFieldThird;
    @FXML
    private TextField textFieldForth;
    @FXML
    private TextField textFieldFifth;
    @FXML
    private TextField textFieldSixth;
    @FXML
    private Button button;
    private StringBuilder stringBuilder;
    private final ConfirmRegistrationOperationModel confirmRegistrationProcessor;

    public ConfirmRegistrationController() {
        stringBuilder = new StringBuilder();
        confirmRegistrationProcessor = SingletonFactory.getSingletonInstance(ConfirmRegistrationProcessor.class);
    }

    @FXML
    public void handleKeyReleased(KeyEvent event) {
        TextField source = (TextField) event.getSource();
        if (!source.getText().isEmpty()) {
            switchFocus(source);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        limitOneCharacter(textFieldFirst);
        limitOneCharacter(textFieldSecond);
        limitOneCharacter(textFieldThird);
        limitOneCharacter(textFieldForth);
        limitOneCharacter(textFieldFifth);
        limitOneCharacter(textFieldSixth);
    }

    private void limitOneCharacter(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() > 1) {
                return null;
            }
            return change;
        }));
    }

    private void switchFocus(TextField current) {
        if (current == textFieldFirst) {
            textFieldSecond.requestFocus();
        } else if (current == textFieldSecond) {
            textFieldThird.requestFocus();
        } else if (current == textFieldThird) {
            textFieldForth.requestFocus();
        } else if (current == textFieldForth) {
            textFieldFifth.requestFocus();
        } else if (current == textFieldFifth) {
            textFieldSixth.requestFocus();
        }
    }

    @FXML
    public void confirm(ActionEvent actionEvent) throws IOException {
        stringBuilder.append(textFieldFirst.getText())
                .append(textFieldSecond.getText())
                .append(textFieldThird.getText())
                .append(textFieldForth.getText())
                .append(textFieldFifth.getText())
                .append(textFieldSixth.getText());
        ConfirmRegistrationInputModel build = ConfirmRegistrationInputModel.builder().
                verificationCode(stringBuilder.toString())
                .build();
        Either<Exception, ConfirmRegistrationOutputModel> process = confirmRegistrationProcessor.process(build);
        if(process.isRight()){
            //setPath("/bg/tu_varna/sit/library/presentation.views/user/user_home_view/pages/user-home-view.fxml");
            setPath(USER_HOME_VIEW);
            changeScene(actionEvent);
        }
    }
    @FXML
    public void skip(ActionEvent actionEvent) throws IOException {
       // setPath("/bg/tu_varna/sit/library/presentation.views/user/user_home_view/pages/user-home-view.fxml");
        setPath(USER_HOME_VIEW);
        changeScene(actionEvent);
    }
}
