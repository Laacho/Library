package bg.tu_varna.sit.library.presentation.controllers.user;

import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class BookDataController extends UserController {
    private String titleValue;
    private String priceValue;
    private String genreValue;
    private String publisherValue;
    private Set<String> authorsValue;
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
    public void change() {
        title.setText(title.getText() + " " + titleValue);
        publisher.setText(publisher.getText() + " " + publisherValue);
        price.setText(price.getText() + " " + priceValue);
        authors.setText(authors.getText() + " " + String.join(", ", authorsValue));
        genre.setText(genre.getText() +" "+genreValue);
    }
}
