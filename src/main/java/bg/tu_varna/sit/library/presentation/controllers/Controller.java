package bg.tu_varna.sit.library.presentation.controllers;

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
        stage.setMaxHeight(640);
        stage.setMaxWidth(900);
        scene = new Scene(root, stage.getMaxWidth(), stage.getMaxHeight());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    protected FXMLLoader changeScene(Stage st) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        root = loader.load();
        stage = st;
        stage.setMaxHeight(640);
        stage.setMaxWidth(900);
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

    @FXML
    public void homeAdmin(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin_home_view/pages/admin-home-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void addBook(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/addBook/pages/addBook-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void search(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/search/pages/search-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void updateStatus(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/update_status/pages/update_status_view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void approveBooks(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/approve_books/pages/approve -books-view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void approveProfiles(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/approve_profiles_view/pages/approve-profiles.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void overdueBooks(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/overdue_books_view/pages/overdue-books.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void returnBooks(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/return_book_view/pages/return-books.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void sendEmail(ActionEvent actionEvent) throws IOException{
        setPath("/bg/tu_varna/sit/library/presentation.views/send_email_view/pages/send_email_view.fxml");
        changeScene(actionEvent);
    }

    @FXML
    public void changeToSetting(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/settings/pages/settings_view.fxml");
        changeScene(actionEvent);
    }
}
