package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.application.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController extends Controller {
    @FXML
    protected void changeHomeViewToLoginView(ActionEvent actionEvent) throws IOException {
        super.setPath("/bg/tu_varna/sit/library/presentation.views/login/pages/login-view.fxml");
        changeScene(actionEvent);
    }
    @FXML
    protected void changeHomeViewToRegisterView(ActionEvent actionEvent) throws IOException {
        super.setPath("/bg/tu_varna/sit/library/presentation.views/register/pages/register-view.fxml");
        changeScene(actionEvent);
    }
}