package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.core.confirm_registration.ConfirmRegistrationProcessor;
import bg.tu_varna.sit.library.models.confirm_registration.ConfirmRegistrationInputModel;
import bg.tu_varna.sit.library.models.confirm_registration.ConfirmRegistrationOperationModel;
import bg.tu_varna.sit.library.models.confirm_registration.ConfirmRegistrationOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class ConfirmRegistrationController extends Controller {
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
            stringBuilder.append(source.getText());
            switchFocus(source);
        }
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
        ConfirmRegistrationInputModel build = ConfirmRegistrationInputModel.builder().
                verificationCode(stringBuilder.toString())
                .build();
        Either<Exception, ConfirmRegistrationOutputModel> process = confirmRegistrationProcessor.process(build);
        if(process.isRight()){
            setPath("/bg/tu_varna/sit/library/presentation.views/user_home_view/pages/user-home-view.fxml");
            changeScene(actionEvent);
        }
    }
    @FXML
    public void skip(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/user_home_view/pages/user-home-view.fxml");
        changeScene(actionEvent);
    }
}
