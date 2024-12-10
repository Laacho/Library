package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.all_books.BooksData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

@Getter
@Setter
public class BookDataViewController extends Controller {
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

    public void change() {
        title.setText(title.getText() + " " + booksData.getTitle());
        publisher.setText(publisher.getText() + " " + booksData.getPublisher().getName());
        isbn.setText(isbn.getText() + " " + booksData.getIsbn());
        price.setText(price.getText() + " " + booksData.getPrice());
        authors.setText(authors.getText() + " " + booksData.getAuthors().stream()
                .map(Author::toString)
                .collect(Collectors.joining(", ")));
        inventoryNumber.setText(inventoryNumber.getText() +" "+booksData.getInventoryNumber());
        genre.setText(genre.getText() +" "+booksData.getGenre().getName());
    }

}
