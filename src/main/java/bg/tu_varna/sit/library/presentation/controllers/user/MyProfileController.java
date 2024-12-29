package bg.tu_varna.sit.library.presentation.controllers.user;

import bg.tu_varna.sit.library.core.change_password.ChangePasswordProcessor;
import bg.tu_varna.sit.library.core.change_username.ChangeUsernameProcessor;
import bg.tu_varna.sit.library.core.check_if_code_matches.CheckIfCodesMatchesProcessor;
import bg.tu_varna.sit.library.core.confirm_registration.ConfirmRegistrationProcessor;
import bg.tu_varna.sit.library.core.send_email_with_code.SendEmailWithCodeProcessor;
import bg.tu_varna.sit.library.core.send_verification_code_for_new_email.SendVerificationCodeForNewEmailProcessor;
import bg.tu_varna.sit.library.models.changeUsername.ChangeUsernameInputModel;
import bg.tu_varna.sit.library.models.changeUsername.ChangeUsernameOperationModel;
import bg.tu_varna.sit.library.models.changeUsername.ChangeUsernameOutputModel;
import bg.tu_varna.sit.library.models.change_password.ChangePasswordInputModel;
import bg.tu_varna.sit.library.models.change_password.ChangePasswordOperationModel;
import bg.tu_varna.sit.library.models.change_password.ChangePasswordOutputModel;
import bg.tu_varna.sit.library.models.check_if_codes_matches.CheckIfCodesMatchesInputModel;
import bg.tu_varna.sit.library.models.check_if_codes_matches.CheckIfCodesMatchesOperationModel;
import bg.tu_varna.sit.library.models.check_if_codes_matches.CheckIfCodesMatchesOutputModel;
import bg.tu_varna.sit.library.models.confirm_registration.ConfirmRegistrationInputModel;
import bg.tu_varna.sit.library.models.confirm_registration.ConfirmRegistrationOperationModel;
import bg.tu_varna.sit.library.models.confirm_registration.ConfirmRegistrationOutputModel;
import bg.tu_varna.sit.library.models.send_email_with_code.SendEmailWithCodeInputModel;
import bg.tu_varna.sit.library.models.send_email_with_code.SendEmailWithCodeOperationModel;
import bg.tu_varna.sit.library.models.send_email_with_code.SendEmailWithCodeOutputModel;
import bg.tu_varna.sit.library.models.send_verification_code_for_new_email.SendVerificationCodeForNewEmailInputModel;
import bg.tu_varna.sit.library.models.send_verification_code_for_new_email.SendVerificationCodeForNewEmailOperationModel;
import bg.tu_varna.sit.library.models.send_verification_code_for_new_email.SendVerificationCodeForNewEmailOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MyProfileController extends UserController implements Initializable {
    @FXML
    private Label usernameValue;
    @FXML
    private Label emailValue;
    @FXML
    private Label isVerifiedValue;
    @FXML
    private Label ratingValue;
    @FXML
    private PasswordField oldPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmNewPasswordField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    private final ChangeUsernameOperationModel changeUsernameOperationModel;
    private final ChangePasswordOperationModel changePasswordOperationModel;
    private final SendVerificationCodeForNewEmailOperationModel sendVerificationCodeForNewEmailOperationModel;
    private final CheckIfCodesMatchesOperationModel checkIfCodesMatchesOperationModel;
    private final ConfirmRegistrationOperationModel confirmRegistrationOperationModel;
    private final SendEmailWithCodeOperationModel sendEmailWithCodeOperationModel;

    public MyProfileController() {
        this.sendEmailWithCodeOperationModel = SingletonFactory.getSingletonInstance(SendEmailWithCodeProcessor.class);
        this.confirmRegistrationOperationModel = SingletonFactory.getSingletonInstance(ConfirmRegistrationProcessor.class);
        this.changeUsernameOperationModel = SingletonFactory.getSingletonInstance(ChangeUsernameProcessor.class);
        this.changePasswordOperationModel = SingletonFactory.getSingletonInstance(ChangePasswordProcessor.class);
        this.sendVerificationCodeForNewEmailOperationModel = SingletonFactory.getSingletonInstance(SendVerificationCodeForNewEmailProcessor.class);
        this.checkIfCodesMatchesOperationModel = SingletonFactory.getSingletonInstance(CheckIfCodesMatchesProcessor.class);
    }

    @FXML
    public void updateUsername(ActionEvent event) {
        if (usernameTextField.getText().isBlank()) {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Error", "Please enter a username!", ButtonType.OK);
            return;
        }
        ChangeUsernameInputModel build = ChangeUsernameInputModel.builder().username(usernameTextField.getText()).build();
        Either<Exception, ChangeUsernameOutputModel> process = changeUsernameOperationModel.process(build);
        if (process.isRight()) {
            ChangeUsernameOutputModel output = process.get();
            AlertManager.showAlert(Alert.AlertType.INFORMATION, "Success", output.getMessage(), ButtonType.OK);
        } else {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Error", process.get().getMessage(), ButtonType.OK);
        }
        clearFields();
    }

    @FXML
    public void sendVerificationCodeForNewEmail(ActionEvent event) {
        if (emailTextField.getText().isBlank()) {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Error", "Email cant be empty!", ButtonType.OK);
            return;
        }
        SendVerificationCodeForNewEmailInputModel input = SendVerificationCodeForNewEmailInputModel
                .builder()
                .newEmail(emailTextField.getText())
                .build();
        Either<Exception, SendVerificationCodeForNewEmailOutputModel> process =
                sendVerificationCodeForNewEmailOperationModel.process(input);
        if (process.isLeft()) {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Error", process.get().getMessage(), ButtonType.OK);
        } else {
            checkIfCodeMatches();
        }

    }

    private void checkIfCodeMatches() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Verification Code");
        Label instructionLabel = new Label("Enter the code:");

        TextField[] codeFields = new TextField[6];
        HBox codeBox = setUpTextFields(codeFields);
        Button checkButton = new Button("Check Code");
        checkButton.setDefaultButton(true);
        checkButton.setOnAction(actionEvent -> {
            StringBuilder code = new StringBuilder();
            for (TextField field : codeFields) {
                code.append(field.getText());
            }
            CheckIfCodesMatchesInputModel input = CheckIfCodesMatchesInputModel.builder()
                    .inputVerificationCode(code.toString())
                    .newEmail(emailTextField.getText())
                    .build();
            Either<Exception, CheckIfCodesMatchesOutputModel> process =
                    checkIfCodesMatchesOperationModel.process(input);
            if (process.isRight()) {
                AlertManager.showAlert(Alert.AlertType.INFORMATION, "Success", process.get().getMessage(), ButtonType.OK);
                dialog.close();
                emailTextField.clear();
            } else {
                AlertManager.showAlert(Alert.AlertType.ERROR, "Error", process.get().getMessage(), ButtonType.OK);
            }

        });

        VBox vbox = new VBox(10, instructionLabel, codeBox, checkButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    private HBox setUpTextFields(TextField[] codeFields) {
        HBox codeBox = new HBox(5);
        codeBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < 6; i++) {
            TextField textField = new TextField();
            textField.setPrefWidth(40);
            setupTextFieldNavigation(textField, codeFields, i);
            codeFields[i] = textField;
            codeBox.getChildren().add(textField);
        }
        return codeBox;
    }

    private void setupTextFieldNavigation(TextField textField, TextField[] fields, int index) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1) {
                textField.setText(newValue.substring(0, 1)); // Limit to 1 character
            }
        });

        textField.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case RIGHT:
                    if (index < fields.length - 1) {
                        fields[index + 1].requestFocus();
                    }
                    break;
                case LEFT:
                    if (index > 0) {
                        fields[index - 1].requestFocus();
                    }
                    break;
                default:
                    break;
            }
        });
    }

    @FXML
    public void changePassword(ActionEvent event) {
        if (oldPasswordField.getText().isBlank() ||
                newPasswordField.getText().isBlank() ||
                confirmNewPasswordField.getText().isBlank()) {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Error", "Please enter a password!", ButtonType.OK);
            return;
        }
        ChangePasswordInputModel input = ChangePasswordInputModel.builder()
                .oldPassword(oldPasswordField.getText())
                .newPassword(newPasswordField.getText())
                .confirmPassword(confirmNewPasswordField.getText())
                .build();
        Either<Exception, ChangePasswordOutputModel> process = changePasswordOperationModel.process(input);
        if (process.isRight()) {
            ChangePasswordOutputModel output = process.get();
            AlertManager.showAlert(Alert.AlertType.INFORMATION, "Success", output.getMessage(), ButtonType.OK);
        } else {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Error", process.get().getMessage(), ButtonType.OK);
        }
        clearFields();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
        usernameValue.setText(userSession.getUsername());
        String email = userSession.getEmail();
        emailValue.setText(email);
        emailValue.setPrefWidth(computeTextWidth(emailValue, email));
        isVerifiedValue.setText(userSession.getVerified() ? "Verified" : "Not Verified");
        Double rating = userSession.getRating();
        if (rating == null) {
            rating = 0.0;
        }
        ratingValue.setText(String.valueOf(rating));
        clearFields();
    }

    private double computeTextWidth(Label label, String text) {
        Text tempText = new Text(text);
        tempText.setFont(label.getFont());
        return tempText.getLayoutBounds().getWidth();
    }

    private void clearFields() {
        oldPasswordField.clear();
        newPasswordField.clear();
        confirmNewPasswordField.clear();
        usernameTextField.clear();
        emailTextField.clear();
    }

    @FXML
    public void sendEmail(ActionEvent event) {
        UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
        if (userSession.getVerified()) {
            AlertManager.showAlert(Alert.AlertType.INFORMATION, "Impossible!", "You are already verified!", ButtonType.OK);
            return;
        }
        SendEmailWithCodeInputModel inputEmail = SendEmailWithCodeInputModel.builder().toEmail(emailValue.getText()).build();
        Either<Exception, SendEmailWithCodeOutputModel> process1 = sendEmailWithCodeOperationModel.process(inputEmail);
        if (process1.isLeft()) {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Error", process1.get().getMessage(), ButtonType.OK);
        }
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Verification Code");
        Label instructionLabel = new Label("Enter the code:");

        TextField[] codeFields = new TextField[6];
        HBox codeBox = setUpTextFields(codeFields);
        Button checkButton = new Button("Check Code");
        checkButton.setDefaultButton(true);
        checkButton.setOnAction(actionEvent -> {
            StringBuilder code = new StringBuilder();
            for (TextField field : codeFields) {
                code.append(field.getText());
            }

            ConfirmRegistrationInputModel input = ConfirmRegistrationInputModel.builder()
                    .verificationCode(code.toString())
                    .build();
            Either<Exception, ConfirmRegistrationOutputModel> process = confirmRegistrationOperationModel.process(input);
            if (process.isRight()) {
                AlertManager.showAlert(Alert.AlertType.INFORMATION, "Success", "Successfully verified!", ButtonType.OK);
                dialog.close();
                isVerifiedValue.setText("Verified");
            } else {
                AlertManager.showAlert(Alert.AlertType.ERROR, "Error", process.getLeft().getMessage(), ButtonType.OK);
            }

        });

        VBox vbox = new VBox(10, instructionLabel, codeBox, checkButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        dialog.showAndWait();
    }


}
