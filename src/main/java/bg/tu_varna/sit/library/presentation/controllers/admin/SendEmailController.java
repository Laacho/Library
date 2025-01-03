package bg.tu_varna.sit.library.presentation.controllers.admin;


import bg.tu_varna.sit.library.core.admin.send_email.SendEmailProcessor;
import bg.tu_varna.sit.library.models.send_email.SendEmailOperationInput;
import bg.tu_varna.sit.library.models.send_email.SendEmailOperationModel;
import bg.tu_varna.sit.library.models.send_email.SendEmailOperationOutput;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import bg.tu_varna.sit.library.utils.session.EmailSession;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SendEmailController extends AdminController implements Initializable {
    @FXML
    private TextField fromTextField;
    @FXML
    private Button sendButton;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextArea messageTextArea;

    private final String COMPANY_EMAIL = System.getProperty("EMAIL");
    private final SendEmailOperationModel sendEmailOperationModel;

    public SendEmailController() {
        super();
        this.sendEmailOperationModel = SingletonFactory.getSingletonInstance(SendEmailProcessor.class);
    }

    @FXML
    public void sendEmailWithButton() {
        if (fromTextField.getText().isBlank() ||
                titleTextField.getText().isBlank() ||
                emailTextField.getText().isBlank() ||
                messageTextArea.getText().isBlank()) {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Empty fields!", "Please fill all fields!", ButtonType.OK);
        } else {
            SendEmailOperationInput input = buildInput();
            Either<Exception, SendEmailOperationOutput> process = sendEmailOperationModel.process(input);
            if (process.isRight()) {
                String message = process.get().getMessage();
                AlertManager.showAlert(Alert.AlertType.INFORMATION,
                        message,
                        "Email was sent successfully to " + emailTextField.getText(),
                        ButtonType.OK);
            } else {
                AlertManager.showAlert(Alert.AlertType.ERROR, "Error!", "Email wasn't sent!", ButtonType.OK);
            }
        }

    }

    private SendEmailOperationInput buildInput() {
        return SendEmailOperationInput.builder()
                .toEmail(emailTextField.getText())
                .title(titleTextField.getText())
                .body(messageTextArea.getText())
                .build();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fromTextField.setText(COMPANY_EMAIL);
        if (EmailSession.toEmail != null) {
            emailTextField.setText(EmailSession.toEmail);
            EmailSession.toEmail = null;
        }
        disableFocusOnButtons();
        titleTextField.setFocusTraversable(false);
        emailTextField.requestFocus();
    }
}
