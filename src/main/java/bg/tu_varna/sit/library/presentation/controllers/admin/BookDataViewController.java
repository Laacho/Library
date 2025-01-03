package bg.tu_varna.sit.library.presentation.controllers.admin;

import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.stream.Collectors;

@Getter
@Setter
public class BookDataViewController extends AdminController {
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
    private Label price;
    @FXML
    private ImageView image;

    public void change() {
        title.setText(title.getText() + " " + booksData.getTitle());
        publisher.setText(publisher.getText() + " " + booksData.getPublisher());
        isbn.setText(isbn.getText() + " " + booksData.getIsbn());
        price.setText(price.getText() + " " + booksData.getPrice());
        authors.setText(authors.getText() + " " + booksData.getAuthors().stream()
                .collect(Collectors.joining(", ")));
        inventoryNumber.setText(inventoryNumber.getText() + " " + booksData.getInventoryNumber());
        genre.setText(genre.getText() + " " + booksData.getGenre());
        File file = new File(booksData.getPathImage());
        image.setImage(new Image(file.toURI().toString()));
        image.setFitHeight(300);
        image.setFitWidth(400);
    }

}
