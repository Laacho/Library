package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.core.login.LoginProcessor;
import bg.tu_varna.sit.library.models.login.LoginInputModel;
import bg.tu_varna.sit.library.models.login.LoginOperationModel;
import bg.tu_varna.sit.library.models.login.LoginOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;

public class LoginController extends Controller {
        private final LoginOperationModel processor;
        @FXML
        private TextField username;
        @FXML
        private TextField password;

    public LoginController( ) {
        this.processor = SingletonFactory.getSingletonInstance(LoginProcessor.class);
    }
    @FXML
    public void login(ActionEvent event) throws IOException {
        LoginInputModel input = LoginInputModel.builder()
                .username(username.getText())
                .password(password.getText())
                .build();
        Either<Exception, LoginOutputModel> process = processor.process(input);
        if (process.isRight()) {
            LoginOutputModel outputModel = process.get();
            if (outputModel.getUserSession().getAdmin()) {
                //open admin scene
                setPath("/bg/tu_varna/sit/library/presentation.views/addBook/pages/addBook-view.fxml");
                changeScene(event);
            } else {
                //open client scene
               // setPath("/bg/tu_varna/sit/library/presentation.views/search/pages/search-view.fxml");
                setPath("/bg/tu_varna/sit/library/presentation.views/addBook/pages/addBook-view.fxml");
                changeScene(event);
            }
        }
    }

}
