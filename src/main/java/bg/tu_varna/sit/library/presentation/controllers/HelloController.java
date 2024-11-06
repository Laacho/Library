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

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Button button1;
    private Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onButton1Click(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/bg/tu_varna/sit/library/presentation.views/login/pages/login-view.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setMaxHeight(600);
        stage.setMaxWidth(800);
        scene = new Scene(root,stage.getMaxWidth(),stage.getMaxHeight());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}