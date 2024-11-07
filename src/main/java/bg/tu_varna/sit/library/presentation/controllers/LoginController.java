package bg.tu_varna.sit.library.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Parent root;
    private Scene scene;
    private Stage stage;
    @FXML
    private Button backButton;


    @FXML
    protected void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/bg/tu_varna/sit/library/presentation.views/hello_view/pages/hello-view.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setMaxHeight(600);
        stage.setMaxWidth(800);
        scene = new Scene(root,stage.getMaxWidth(),stage.getMaxHeight());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
