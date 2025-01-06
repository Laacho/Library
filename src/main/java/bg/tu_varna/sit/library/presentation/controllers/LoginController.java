package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.core.logging.login.LoginProcessor;
import bg.tu_varna.sit.library.models.login.LoginInputModel;
import bg.tu_varna.sit.library.models.login.LoginOperationModel;
import bg.tu_varna.sit.library.models.login.LoginOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

import static bg.tu_varna.sit.library.utils.ConstantsPaths.ADMIN_HOME_VIEW;
import static bg.tu_varna.sit.library.utils.ConstantsPaths.USER_HOME_VIEW;

public class LoginController extends Controller {
        private final LoginOperationModel processor;

        @FXML
        private TextField username;
        @FXML
        private TextField password;
        @FXML
        private Button loginButton;

    public LoginController( ) {
        this.processor = SingletonFactory.getSingletonInstance(LoginProcessor.class);
    }
    @FXML
    public void login(ActionEvent actionEvent) throws IOException {
        LoginInputModel input = LoginInputModel.builder()
                .username(username.getText())
                .password(password.getText())
                .build();
        Either<Exception, LoginOutputModel> process = processor.process(input);
        if(process.isRight()){
            LoginOutputModel outputModel = process.get();
            if(outputModel.getUserSession().getAdmin()){
                //open admin scene
                //setPath("/bg/tu_varna/sit/library/presentation.views/admin/home_view/pages/admin-home-view.fxml");
                setPath(ADMIN_HOME_VIEW);
                changeScene(actionEvent);
            }
            else{
                //open user scene
               // setPath("/bg/tu_varna/sit/library/presentation.views/user/user_home_view/pages/user-home-view.fxml");
                setPath(USER_HOME_VIEW);
                changeScene(actionEvent);
            }
        }
    }
    @FXML
    public void loginWithEnter(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER){
            loginButton.fire();
        }
    }
}
