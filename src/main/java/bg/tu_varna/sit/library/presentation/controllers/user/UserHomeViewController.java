package bg.tu_varna.sit.library.presentation.controllers.user;

import bg.tu_varna.sit.library.core.new_books.NewBooksProcessor;
import bg.tu_varna.sit.library.core.recommended_books.RecommendedBooksProcessor;
import bg.tu_varna.sit.library.models.new_books.NewBooksData;
import bg.tu_varna.sit.library.models.new_books.NewBooksInputModel;
import bg.tu_varna.sit.library.models.new_books.NewBooksOperationModel;
import bg.tu_varna.sit.library.models.new_books.NewBooksOutputModel;
import bg.tu_varna.sit.library.models.recommended_books.RecommendedBooksData;
import bg.tu_varna.sit.library.models.recommended_books.RecommendedBooksInputModel;
import bg.tu_varna.sit.library.models.recommended_books.RecommendedBooksOperationModel;
import bg.tu_varna.sit.library.models.recommended_books.RecommendedBooksOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class UserHomeViewController extends UserController implements Initializable {
    @FXML
    private GridPane newBooks;
    @FXML
    private GridPane recommendedBooks;
    private final NewBooksOperationModel newBooksProcessor;
    private final RecommendedBooksOperationModel recommendedBooksProcessor;

    public UserHomeViewController() {
        newBooksProcessor = SingletonFactory.getSingletonInstance(NewBooksProcessor.class);
        recommendedBooksProcessor = SingletonFactory.getSingletonInstance(RecommendedBooksProcessor.class);
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
                vBox.getChildren().addAll(
                        new Label(iterBook.getTitle()),
                        imageView,
                        new Button("Go")
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
        }else{
            AlertManager.showAlert(Alert.AlertType.ERROR,"Error","Error while finding new books", ButtonType.OK);
        }
        Either<Exception, RecommendedBooksOutputModel> recommendedBooksOutputModels = recommendedBooksProcessor.process(RecommendedBooksInputModel.builder().build());
        if(recommendedBooksOutputModels.isRight()){
            List<RecommendedBooksData> recommendedBooksList = recommendedBooksOutputModels.get().getRecommendedBooks();
            int i=0;
            for (RecommendedBooksData iterBook : recommendedBooksList) {
                VBox vBox = new VBox();
                File file = new File(iterBook.getPathToImage());
                ImageView imageView = new ImageView(new Image(file.toURI().toString()));
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                vBox.getChildren().addAll(
                        new Label(iterBook.getTitle()),
                        imageView,
                        new Button("Go")
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
}
