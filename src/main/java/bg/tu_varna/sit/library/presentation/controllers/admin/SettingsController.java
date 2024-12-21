package bg.tu_varna.sit.library.presentation.controllers.admin;

import bg.tu_varna.sit.library.core.delete_user.DeleteUserProcessor;
import bg.tu_varna.sit.library.core.demote_user.DemoteUserProcessor;
import bg.tu_varna.sit.library.core.promote_user.PromoteUserProcessor;
import bg.tu_varna.sit.library.core.reset_password_for_user.ResetPasswordForUserProcessor;
import bg.tu_varna.sit.library.core.search_user_by_username.SearchUserByUsernameProcess;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.models.delete_user.DeleteUserInputModel;
import bg.tu_varna.sit.library.models.delete_user.DeleteUserOperationModel;
import bg.tu_varna.sit.library.models.delete_user.DeleteUserOutputModel;
import bg.tu_varna.sit.library.models.demote_user.DemoteUserInputModel;
import bg.tu_varna.sit.library.models.demote_user.DemoteUserOperationModel;
import bg.tu_varna.sit.library.models.demote_user.DemoteUserOutputModel;
import bg.tu_varna.sit.library.models.promote_user.PromoteUserInputModel;
import bg.tu_varna.sit.library.models.promote_user.PromoteUserOperationModel;
import bg.tu_varna.sit.library.models.promote_user.PromoteUserOutputModel;
import bg.tu_varna.sit.library.models.reset_password_for_user.ResetPasswordForUserInputModel;
import bg.tu_varna.sit.library.models.reset_password_for_user.ResetPasswordForUserOperationModel;
import bg.tu_varna.sit.library.models.reset_password_for_user.ResetPasswordForUserOutputModel;
import bg.tu_varna.sit.library.models.search_user_by_username.SearchUserByUsernameInputModel;
import bg.tu_varna.sit.library.models.search_user_by_username.SearchUserByUsernameOperationModel;
import bg.tu_varna.sit.library.models.search_user_by_username.SearchUserByUsernameOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SettingsController extends AdminController {
    @FXML
    private Button resetPasswordButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Button promoteButton;
    @FXML
    private Button demoteButton;
    @FXML
    private Button deleteUserButton;
    @FXML
    private Label firstNameResult;
    @FXML
    private Label lastNameResult;
    @FXML
    private Label EmailResult;
    @FXML
    private Label ratingResult;
    @FXML
    private Label verifiedResult;
    @FXML
    private Label roleResult;
    private boolean foundUser;
    private UserCredentials userCredentials;
    private final SearchUserByUsernameOperationModel searchUserByUsernameOperationModel;
    private final PromoteUserOperationModel promoteUserOperationModel;
    private final DemoteUserOperationModel demoteUserOperationModel;
    private final ResetPasswordForUserOperationModel resetPasswordForUserOperationModel;
    private final DeleteUserOperationModel deleteUserOperationModel;
    public SettingsController( ) {
        super();
        this.deleteUserOperationModel = SingletonFactory.getSingletonInstance(DeleteUserProcessor.class);
        this.resetPasswordForUserOperationModel = SingletonFactory.getSingletonInstance(ResetPasswordForUserProcessor.class);
        this.demoteUserOperationModel = SingletonFactory.getSingletonInstance(DemoteUserProcessor.class);
        this.promoteUserOperationModel = SingletonFactory.getSingletonInstance(PromoteUserProcessor.class);
        this.searchUserByUsernameOperationModel = SingletonFactory.getSingletonInstance(SearchUserByUsernameProcess.class);
        this.foundUser = false;
    }

    @FXML
    public void searchUser() {
        if(searchTextField.getText().isBlank()){
            AlertManager.showAlert(Alert.AlertType.ERROR,"Empty field!","Please enter a username!", ButtonType.OK);
        }
        else{
            SearchUserByUsernameInputModel input = SearchUserByUsernameInputModel.builder()
                    .username(searchTextField.getText())
                    .build();
            Either<Exception, SearchUserByUsernameOutputModel> process = searchUserByUsernameOperationModel.process(input);
            if(process.isRight()){
                UserCredentials userCredentials = process.get().getUserCredentials();
                this.userCredentials = userCredentials;
                firstNameResult.setText(userCredentials.getUser().getFirstName());
                lastNameResult.setText(userCredentials.getUser().getLastName());
                EmailResult.setText(userCredentials.getEmail());
                ratingResult.setText(String.valueOf(userCredentials.getRating()));
                verifiedResult.setText(userCredentials.getVerified() ? String.valueOf(userCredentials.getDateOfVerification()) : "Not verified yet");
                roleResult.setText(userCredentials.getAdmin() ? "Admin" : "User");
                foundUser = true;
            }
            else{
                foundUser = false;
            }
            
        }
    }
    @FXML
    public void searchWithEnter(KeyEvent keyEvent){
        if(!searchTextField.getText().isEmpty()){
            if(keyEvent.getCode()== KeyCode.ENTER){
                searchButton.fire();
            }
        }
    }
    @FXML
    public void promoteAction(ActionEvent actionEvent) {
        if(foundUser){
            UserSession singletonInstance = SingletonFactory.getSingletonInstance(UserSession.class);
            if(singletonInstance.getUsername().equals(this.userCredentials.getUsername())){
                AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","You can't promote yourself!", ButtonType.OK);
                return;
            }
            Boolean isAdmin = userCredentials.getAdmin();
                if(isAdmin){
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","User is already admin!", ButtonType.OK);
                }
                else{
                    PromoteUserInputModel input =
                            PromoteUserInputModel.builder()
                            .userCredentials(userCredentials)
                            .build();
                    Either<Exception, PromoteUserOutputModel> process = promoteUserOperationModel.process(input);
                    if(process.isRight()){
                        roleResult.setText("Admin");
                        AlertManager.showAlert(Alert.AlertType.INFORMATION,"Success!","User was promoted to admin!", ButtonType.OK);
                    }
                    else{
                        AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","User wasn't promoted!", ButtonType.OK);
                    }
                }
        }
        else{
            AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","User not found!", ButtonType.OK);
        }
    }
    @FXML
    public void demoteAction(ActionEvent actionEvent) {
        if(foundUser){
            UserSession singletonInstance = SingletonFactory.getSingletonInstance(UserSession.class);
            if(singletonInstance.getUsername().equals(this.userCredentials.getUsername())){
                AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","You can't demote yourself!", ButtonType.OK);
                return;
            }
            Boolean isAdmin = userCredentials.getAdmin();
            if(!isAdmin){
                AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","User is already user!", ButtonType.OK);
            }
            else{
                DemoteUserInputModel input = DemoteUserInputModel.builder().userCredentials(userCredentials).build();
                Either<Exception, DemoteUserOutputModel> process = demoteUserOperationModel.process(input);
                if(process.isRight()){
                    roleResult.setText("User");
                    AlertManager.showAlert(Alert.AlertType.INFORMATION,"Success!","User was demoted to user!", ButtonType.OK);
                }
                else{
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","User wasn't demoted!", ButtonType.OK);
                }
            }
        }
        else{
            AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","User not found!", ButtonType.OK);
        }
    }
    @FXML
    public void resetPasswordAction(ActionEvent actionEvent) {
        if(foundUser){
            UserSession singletonInstance = SingletonFactory.getSingletonInstance(UserSession.class);
            if(singletonInstance.getUsername().equals(this.userCredentials.getUsername())){
                AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","You can't reset your own password!", ButtonType.OK);
                return;
            }
            Alert alert = AlertManager.showAlert(Alert.AlertType.CONFIRMATION, "Reset password?", "Are you sure you want to reset password for user " + userCredentials.getUsername() + "?", ButtonType.YES, ButtonType.NO);
            if (alert.getResult() == ButtonType.YES) {
                ResetPasswordForUserInputModel build = ResetPasswordForUserInputModel.builder().userCredentials(userCredentials).build();
                Either<Exception, ResetPasswordForUserOutputModel> process = resetPasswordForUserOperationModel.process(build);
                if(process.isRight()){
                    AlertManager.showAlert(Alert.AlertType.INFORMATION,"Success!","Password was reset!", ButtonType.OK);
                }
            }
        }
        else{
            AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","User not found!", ButtonType.OK);
        }
    }
    @FXML
    public void deleteUserAction(ActionEvent actionEvent) {
        if(foundUser){
            UserSession singletonInstance = SingletonFactory.getSingletonInstance(UserSession.class);
            if(singletonInstance.getUsername().equals(this.userCredentials.getUsername())){
                AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","You can't delete yourself!", ButtonType.OK);
                return;
            }
            Alert alert = AlertManager.showAlert(Alert.AlertType.CONFIRMATION, "Delete user?", "Are you sure you want to delete user " + userCredentials.getUsername() + "?", ButtonType.YES, ButtonType.NO);
            if (alert.getResult() == ButtonType.YES) {
                DeleteUserInputModel input = DeleteUserInputModel.builder()
                        .userId(userCredentials.getId())
                        .email(userCredentials.getEmail())
                        .build();
                Either<Exception, DeleteUserOutputModel> process = deleteUserOperationModel.process(input);
                if(process.isRight()){
                    resetLabels();
                     searchTextField.setText("");
                     foundUser = false;
                    AlertManager.showAlert(Alert.AlertType.INFORMATION,"Success!","User was deleted!", ButtonType.OK);
                }
            }
        }
        else{
            AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","User not found!", ButtonType.OK);
        }
    }

    private void resetLabels() {
        firstNameResult.setText("");
        lastNameResult.setText("");
        EmailResult.setText("");
        ratingResult.setText("");
        verifiedResult.setText("");
        roleResult.setText("");
    }
}
