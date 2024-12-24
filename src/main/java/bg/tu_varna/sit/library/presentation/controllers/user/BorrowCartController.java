package bg.tu_varna.sit.library.presentation.controllers.user;

import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class BorrowCartController extends UserController implements Initializable {
    @FXML
    private DatePicker returnDate;
    @FXML
    private GridPane gridPaneLayout;

    private int bookCount = 0;
    private int columnCount = 2;
    private static Set<Book> borrowedBooks=new HashSet<>();

    public static void addBookInSet(Book book) {
        borrowedBooks.add(book);
    }
    public static void removeBookInSet(Book book) {
        borrowedBooks.remove(book);
    }
    @FXML
    public void borrowAllBooks() {
        //svurzano e s butona
        if(!borrowedBooks.isEmpty() && returnDate.valueProperty().getValue().isAfter(LocalDate.now())) {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Book book : borrowedBooks) {

            ImageView imageView = new ImageView(new Image(book.getPath()));
            //sig shte se promenq
            imageView.setFitWidth(100);
            imageView.setFitHeight(150);

            VBox bookBox = getVBox(book, imageView);
            int row = bookCount / columnCount;
            int col = bookCount % columnCount;
            gridPaneLayout.add(bookBox, col, row);
            bookCount++;
        }
    }

    @NotNull
    private VBox getVBox(Book book, ImageView imageView) {
        Label titleLabel = new Label(book.getTitle());

        List<Label> authorLabels = new ArrayList<>();
        Set<Author> authors = book.getAuthors();
        authors.forEach(author -> {
            Label label=new Label(author.toString());
            authorLabels.add(label);
        });
        Button removeButton = new Button("X");
        removeButton.setOnAction(e -> removeBook((VBox) removeButton.getParent()));
                                                        //tozi cast moje da ne raboti
        return new VBox(5, imageView,  titleLabel, (Node) authorLabels, removeButton);
    }

    public void removeBook(VBox bookBox) {
        gridPaneLayout.getChildren().remove(bookBox);
        bookCount--;
        rearrangeGrid();
    }
    private void rearrangeGrid() {
        gridPaneLayout.getChildren().clear();
        bookCount = 0;

        for (Node node : gridPaneLayout.getChildren()) {
            if (node instanceof VBox) {
                int row = bookCount / columnCount;
                int col = bookCount % columnCount;
                gridPaneLayout.add(node, col, row);
                bookCount++;
            }
        }
    }
}
