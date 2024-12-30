package bg.tu_varna.sit.library.presentation.controllers.user;

import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.Set;

@Getter
@Setter
public class BookDataController extends UserController {
    private CommonBooksProperties booksData;
    @FXML
    private Label title;
    @FXML
    private Label publisher;
    @FXML
    private Label isbn;
    @FXML
    private Label inventoryNumber;
    @FXML
    private Label authors;
    @FXML
    private Label genre;
    @FXML
    private ImageView imageView;
    @FXML
    private Button addToCart;
    @FXML
    private Button favourite;

    public void change() {
        title.setText(title.getText() + " " + booksData.getTitle());
        publisher.setText(publisher.getText() + " " + booksData.getPublisher());
        authors.setText(authors.getText() + " " + String.join(", ", booksData.getAuthors()));
        genre.setText(genre.getText() + " " + booksData.getGenre());
        File file = new File(booksData.getPathImage());
        imageView.setImage(new Image(file.toURI().toString()));
        imageView.setFitHeight(300);
        imageView.setFitWidth(400);
    }

    @FXML
    public void addToCart(ActionEvent actionEvent) {
        BorrowCartController.addBookInSet(booksData);
    }
}
