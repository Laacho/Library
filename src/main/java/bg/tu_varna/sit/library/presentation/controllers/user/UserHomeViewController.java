package bg.tu_varna.sit.library.presentation.controllers.user;

import bg.tu_varna.sit.library.core.user.home_view.FindBookByIdProcessor;
import bg.tu_varna.sit.library.core.user.home_view.NewBooksProcessor;
import bg.tu_varna.sit.library.core.user.home_view.RecommendedBooksProcessor;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.find_book_by_id.FindBookByIdInputModel;
import bg.tu_varna.sit.library.models.find_book_by_id.FindBookByIdOperationModel;
import bg.tu_varna.sit.library.models.find_book_by_id.FindBookByIdOutputModel;
import bg.tu_varna.sit.library.models.new_books.NewBooksData;
import bg.tu_varna.sit.library.models.new_books.NewBooksInputModel;
import bg.tu_varna.sit.library.models.new_books.NewBooksOperationModel;
import bg.tu_varna.sit.library.models.new_books.NewBooksOutputModel;
import bg.tu_varna.sit.library.models.recommended_books.RecommendedBooksData;
import bg.tu_varna.sit.library.models.recommended_books.RecommendedBooksInputModel;
import bg.tu_varna.sit.library.models.recommended_books.RecommendedBooksOperationModel;
import bg.tu_varna.sit.library.models.recommended_books.RecommendedBooksOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import static bg.tu_varna.sit.library.utils.ConstantsPaths.USER_BOOK_DATA_FOR_USER;

public class UserHomeViewController extends UserController implements Initializable {
    @FXML
    private GridPane newBooks;
    @FXML
    private GridPane recommendedBooks;
    private final NewBooksOperationModel newBooksProcessor;
    private final RecommendedBooksOperationModel recommendedBooksProcessor;
    private final FindBookByIdOperationModel findBookByIdProcessor;

    public UserHomeViewController() {
        newBooksProcessor = SingletonFactory.getSingletonInstance(NewBooksProcessor.class);
        recommendedBooksProcessor = SingletonFactory.getSingletonInstance(RecommendedBooksProcessor.class);
        findBookByIdProcessor = SingletonFactory.getSingletonInstance(FindBookByIdProcessor.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Either<Exception, NewBooksOutputModel> process = newBooksProcessor.process(NewBooksInputModel.builder().build());
        if (process.isRight()) {
            int i = 0;
            List<NewBooksData> newBooksData = process.get().getNewBooksData();
            newBooksData.sort(Comparator.comparing(NewBooksData::getBookId).reversed());
            for (NewBooksData iterBook : newBooksData) {
                VBox vBox = new VBox();
                File file = new File(iterBook.getPathToImage());
                ImageView imageView = new ImageView(new Image(file.toURI().toString()));
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                Button button = new Button("Go");
                int finalI = i;
                button.setOnAction(e -> {
                    try {
                        setButtonFunctionality(newBooksData, finalI);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                Label label = new Label(iterBook.getTitle());
                        label.getStyleClass().add("smaller-text");
                vBox.getChildren().addAll(
                        label,
                        imageView,
                        button
                );
                newBooks.setHgap(100);
                vBox.setAlignment(Pos.CENTER);
                vBox.setSpacing(10);
                newBooks.getColumnConstraints().add(new ColumnConstraints());
                newBooks.add(vBox, i, 0);
                i++;
            }
            for (ColumnConstraints columnConstraint : newBooks.getColumnConstraints()) {
                columnConstraint.setHgrow(Priority.SOMETIMES);
            }
        } else {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Error", "Error while finding new books", ButtonType.OK);
        }
        Either<Exception, RecommendedBooksOutputModel> recommendedBooksOutputModels = recommendedBooksProcessor.process(RecommendedBooksInputModel.builder().build());
        if (recommendedBooksOutputModels.isRight()) {
            List<RecommendedBooksData> recommendedBooksList = recommendedBooksOutputModels.get().getRecommendedBooks();
            int i = 0;
            for (RecommendedBooksData iterBook : recommendedBooksList) {
                VBox vBox = new VBox();
                File file = new File(iterBook.getPathToImage());
                ImageView imageView = new ImageView(new Image(file.toURI().toString()));
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                Button button = new Button("Go");
                int finalI = i;
                button.setOnAction(e -> {
                    try {
                        setButtonFunctionality(finalI, recommendedBooksList);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                vBox.getChildren().addAll(
                        new Label(iterBook.getTitle()),
                        imageView,
                        button
                );
                recommendedBooks.setHgap(100);
                vBox.setAlignment(Pos.CENTER);
                vBox.setSpacing(10);
                recommendedBooks.getColumnConstraints().add(new ColumnConstraints());
                recommendedBooks.add(vBox, i, 0);
                i++;
            }
            for (ColumnConstraints columnConstraint : recommendedBooks.getColumnConstraints()) {
                columnConstraint.setHgrow(Priority.SOMETIMES);
            }
        }
    }

    private void setButtonFunctionality(List<NewBooksData> booksData, int finalI) throws IOException {
        NewBooksData book = booksData.get(finalI);
        Either<Exception, FindBookByIdOutputModel> process = findBookByIdProcessor.process(FindBookByIdInputModel.builder().id(book.getBookId()).build());
        if (process.isRight()) {
            changeToBookInfo(process);
        }
    }

    private void setButtonFunctionality(int finalI, List<RecommendedBooksData> booksData) throws IOException {
        RecommendedBooksData book = booksData.get(finalI);
        Either<Exception, FindBookByIdOutputModel> process = findBookByIdProcessor.process(FindBookByIdInputModel.builder().id(book.getId()).build());
        if (process.isRight()) {
            changeToBookInfo(process);
        }
    }

    private void changeToBookInfo(Either<Exception, FindBookByIdOutputModel> process) throws IOException {
        CommonBooksProperties result = process.get().getBook();
       // setPath("/bg/tu_varna/sit/library/presentation.views/user/book_data_for_user/pages/book-data-for-user-view.fxml");
        setPath(USER_BOOK_DATA_FOR_USER);
        BookDataController.booksData=result;
        FXMLLoader loader = changeScene((Stage) newBooks.getScene().getWindow());
        BookDataController controller = loader.getController();
        controller.change();
    }
}
