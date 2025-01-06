package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.core.logging.logout.LogoutProcessor;
import bg.tu_varna.sit.library.models.logout.LogoutInputModel;
import bg.tu_varna.sit.library.models.logout.LogoutOperationModel;
import bg.tu_varna.sit.library.models.logout.LogoutOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static bg.tu_varna.sit.library.utils.ConstantsPaths.LOGGING_CHANGE_TO_LOGIN_VIEW;
import static bg.tu_varna.sit.library.utils.ConstantsPaths.LOGGING_CHANGE_TO_REGISTER_VIEW;

public class HelloController extends Controller implements Initializable {
    private final LogoutOperationModel logoutOperationModel;

    public HelloController() {
        this.logoutOperationModel = SingletonFactory.getSingletonInstance(LogoutProcessor.class);
    }

    @FXML
    protected void changeHomeViewToLoginView(ActionEvent actionEvent) throws IOException {
        //setPath("/bg/tu_varna/sit/library/presentation.views/logging/login/pages/login-view.fxml");
        setPath(LOGGING_CHANGE_TO_LOGIN_VIEW);
        changeScene(actionEvent);
    }
    @FXML
    protected void changeHomeViewToRegisterView(ActionEvent actionEvent) throws IOException {
        //setPath("/bg/tu_varna/sit/library/presentation.views/logging/register/pages/register-view.fxml");
        setPath(LOGGING_CHANGE_TO_REGISTER_VIEW);
        changeScene(actionEvent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
        if(userSession.getWantsToLogout()!=null && userSession.getWantsToLogout()){
                LogoutInputModel input = LogoutInputModel.builder().build();
                Either<Exception, LogoutOutputModel> process = logoutOperationModel.process(input);
                if (process.isRight()) {
                    AlertManager.showAlert(Alert.AlertType.INFORMATION, "Logout!", process.get().getMessage(), ButtonType.OK);
                } else {
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Error!", process.get().getMessage(), ButtonType.OK);
                }
        }
    }
}