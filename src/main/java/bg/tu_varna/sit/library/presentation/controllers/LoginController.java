package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.core.register.LoginProcessor;
import bg.tu_varna.sit.library.models.login.LoginInputModel;
import bg.tu_varna.sit.library.models.login.LoginOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController extends Controller {
        private final LoginProcessor processor;

        @FXML
        private TextField username;
        @FXML
        private TextField password;

    public LoginController( ) {
        this.processor = SingletonFactory.getSingletonInstance(LoginProcessor.class);
    }
    @FXML
    public void login(){
        LoginInputModel input = LoginInputModel.builder()
                .username(username.getText())
                .password(password.getText())
                .build();
        Either<Exception, LoginOutputModel> process = processor.process(input);
        if(process.isRight()){
            LoginOutputModel outputModel = process.get();
            if(outputModel.getUserSession().getAdmin()){
                //open admin scene
            }
            else{
                //open user scene
            }
        }
    }

}
