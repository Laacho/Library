package bg.tu_varna.sit.library.presentation.controllers.user;

import bg.tu_varna.sit.library.core.all_books.AllBooksProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.all_books.AllBooksInputModel;
import bg.tu_varna.sit.library.models.all_books.AllBooksOperationModel;
import bg.tu_varna.sit.library.models.all_books.AllBooksOutputModel;
import bg.tu_varna.sit.library.models.all_books.BooksData;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.converters.base.ConversionService;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserAllBooksController extends UserController implements Initializable {
    @FXML
    private GridPane newBooks;
    private AllBooksOperationModel allBooksProcessor;
    private final ConversionService conversionService;

    public UserAllBooksController() {
        this.allBooksProcessor = SingletonFactory.getSingletonInstance(AllBooksProcessor.class);
        conversionService = SingletonFactory.getSingletonInstance(ConversionService.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Either<Exception, AllBooksOutputModel> process = allBooksProcessor.process(AllBooksInputModel.builder().build());
        if (process.isRight()) {
            List<BooksData> booksData = process.get().getBooksData();
            int i = 0;
            int j = 0;
            while (true) {
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
                    Button button = new Button("Go");
                    int finalI = i;
                    button.setOnAction(e -> {
                        try {
                            setButtonFunctionality(booksData, finalI);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                    vBox.getChildren().addAll(
                            new Label(data.getTitle()),
                            imageView,
                            button
                    );
                    vBox.setAlignment(Pos.CENTER);
                    vBox.setSpacing(20);
                    newBooks.add(vBox, k, j);
                    i++;
                }
                j++;
                if (i >= booksData.size()) {
                    break;
                }
            }
            newBooks.setHgap(200);
            newBooks.setVgap(50);
            newBooks.setPadding(new Insets(20, 0, 0, 20));
        }
    }

    private void setButtonFunctionality(List<BooksData> booksData, int finalI) throws IOException {
        BooksData book = booksData.get(finalI);
        CommonBooksProperties convert = conversionService.convert(book, CommonBooksProperties.class);
        setPath("/bg/tu_varna/sit/library/presentation.views/user/book_data_for_user/pages/book_data_for_user_view.fxml");
        FXMLLoader loader = loader = changeScene((Stage) newBooks.getScene().getWindow());
        BookDataController controller = loader.getController();
        controller.setBooksData(convert);
        controller.change();
    }
}
