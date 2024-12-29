package bg.tu_varna.sit.library.presentation.controllers.user;

import bg.tu_varna.sit.library.core.all_books.AllBooksProcessor;
import bg.tu_varna.sit.library.models.all_books.AllBooksInputModel;
import bg.tu_varna.sit.library.models.all_books.AllBooksOperationModel;
import bg.tu_varna.sit.library.models.all_books.AllBooksOutputModel;
import bg.tu_varna.sit.library.models.all_books.BooksData;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserAllBooksController extends UserController implements Initializable {
    @FXML
    private GridPane newBooks;
    private AllBooksOperationModel allBooksProcessor;

    public UserAllBooksController() {
        this.allBooksProcessor = SingletonFactory.getSingletonInstance(AllBooksProcessor.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Either<Exception, AllBooksOutputModel> process = allBooksProcessor.process(AllBooksInputModel.builder().build());
        if (process.isRight()) {
            List<BooksData> booksData = process.get().getBooksData();
            int i = 0;
            int rows = booksData.size() / 2;
            for (int j = 0; j < rows; j++) {
                newBooks.getRowConstraints().add(new RowConstraints());
                for (int k = 0; k < 2; k++) {
                    if (i >= booksData.size()) {
                        break;
                    }
                    BooksData data = booksData.get(i);
                    VBox vBox = new VBox();
                    File file = new File(data.getPath());
                    ImageView imageView = new ImageView(new Image(file.toURI().toString()));
                    imageView.setFitHeight(200);
                    imageView.setFitWidth(200);
                    vBox.getChildren().addAll(
                            new Label(data.getTitle()),
                            imageView,
                            new Button("Go")
                    );
                    vBox.setAlignment(Pos.CENTER);
                    vBox.setSpacing(20);
                    newBooks.add(vBox, k, j);
                    i++;
                }
            }
            newBooks.setHgap(200);
            newBooks.setVgap(50);
            newBooks.setPadding(new Insets(20, 0, 0, 20));
        }
    }
}
