package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Setter
@Getter
public class Controller {
    protected Parent root;
    protected Scene scene;
    protected Stage stage;
    private String path;


    @FXML
    protected void changeScene(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource(path));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setMaxHeight(600);
        stage.setMaxWidth(1000);
        scene = new Scene(root, stage.getMaxWidth(), stage.getMaxHeight());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    protected FXMLLoader changeScene(Stage  st) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        root = loader.load();
        stage = st;
        stage.setMaxHeight(600);
        stage.setMaxWidth(1000);
        scene = new Scene(root, stage.getMaxWidth(), stage.getMaxHeight());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        return loader;
    }

    @FXML
    protected void changeToHomeView(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/hello_view/pages/hello-view.fxml");
        changeScene(actionEvent);
    }


    public void handleEither(Either<Exception, ? extends OperationOutput> result) {
        if (result.isLeft()) {
            throw new RuntimeException();
            //throw result.getLeft()
        }

    }
}
