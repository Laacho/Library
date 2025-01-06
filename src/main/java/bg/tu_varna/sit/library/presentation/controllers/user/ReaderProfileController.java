package bg.tu_varna.sit.library.presentation.controllers.user;

import bg.tu_varna.sit.library.core.user.reader_profile.CheckIfReaderProfileExistsProcessor;
import bg.tu_varna.sit.library.core.user.reader_profile.GetReaderProfileProcessor;
import bg.tu_varna.sit.library.core.user.reader_profile.RequestReaderProfileProcessor;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.check_if_reader_profile_exists.CheckIfReaderProfileExistsInputModel;
import bg.tu_varna.sit.library.models.check_if_reader_profile_exists.CheckIfReaderProfileExistsOperationModel;
import bg.tu_varna.sit.library.models.check_if_reader_profile_exists.CheckIfReaderProfileExistsOutputModel;
import bg.tu_varna.sit.library.models.get_reader_profile.BookDataForReader;
import bg.tu_varna.sit.library.models.get_reader_profile.GetReaderProfileInputModel;
import bg.tu_varna.sit.library.models.get_reader_profile.GetReaderProfileOperationModel;
import bg.tu_varna.sit.library.models.get_reader_profile.GetReaderProfileOutputModel;
import bg.tu_varna.sit.library.models.request_reader_profile.RequestReaderProfileInputModel;
import bg.tu_varna.sit.library.models.request_reader_profile.RequestReaderProfileOperationModel;
import bg.tu_varna.sit.library.models.request_reader_profile.RequestReaderProfileOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import bg.tu_varna.sit.library.utils.converters.base.ConversionService;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import static bg.tu_varna.sit.library.utils.ConstantsPaths.USER_BOOK_DATA_FOR_USER;

public class ReaderProfileController extends UserController implements Initializable {
    @FXML
    private GridPane gridPane;
    private final CheckIfReaderProfileExistsOperationModel checkIfReaderProfileExistsProcessor;
    private final RequestReaderProfileOperationModel requestReaderProfileProcessor;
    private final GetReaderProfileOperationModel getReaderProfileProcessor;
    private List<BookDataForReader> wantsToRead;
    private List<BookDataForReader> favouriteBooks;
    private List<BookDataForReader> readBooks;
    private Map<String, List<BookDataForReader>> recommendedBooks;
    private final ConversionService conversionService;
    @FXML
    private Button favouriteBookButton;

    public ReaderProfileController() {
        checkIfReaderProfileExistsProcessor = SingletonFactory.getSingletonInstance(CheckIfReaderProfileExistsProcessor.class);
        requestReaderProfileProcessor = SingletonFactory.getSingletonInstance(RequestReaderProfileProcessor.class);
        getReaderProfileProcessor = SingletonFactory.getSingletonInstance(GetReaderProfileProcessor.class);
        conversionService = SingletonFactory.getSingletonInstance(ConversionService.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridPane.setAlignment(Pos.CENTER);
        UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
        Either<Exception, CheckIfReaderProfileExistsOutputModel> process = checkIfReaderProfileExistsProcessor.process(CheckIfReaderProfileExistsInputModel.builder().username(userSession.getUsername()).build());
        if (process.isRight()) {
            boolean doesExists = process.get().isDoesExists();
            if (!doesExists) {
                showAlertsIfTheReaderProfileDoesNotExist(userSession);
                return;
            }
            boolean approved = process.get().isApproved();
            if (!approved) {
                AlertManager.showAlert(Alert.AlertType.INFORMATION, "Wait", "Your profile needs to be approved by administrator. You have to wait.", ButtonType.OK);
            }
            if (doesExists && approved) {
                Either<Exception, GetReaderProfileOutputModel> outputModels = getReaderProfileProcessor.process(GetReaderProfileInputModel.builder().username(userSession.getUsername()).build());
                if (outputModels.isRight()) {
                    GetReaderProfileOutputModel output = outputModels.get();
                    wantsToRead = output.getWantsToRead();
                    favouriteBooks = output.getFavouriteBook();
                    recommendedBooks = output.getRecommendedGenres();
                    readBooks = output.getReadBooks();
                    favouriteBookButton.fire();
                }
            }
        }
    }

    private void showAlertsIfTheReaderProfileDoesNotExist(UserSession userSession) {
        ButtonType createProfileButton = new ButtonType("Create profile");
        ButtonType cancelButton = new ButtonType("Cancel");
        Alert alert = AlertManager.showAlert(Alert.AlertType.INFORMATION, "No Reader Profile Found", "You currently don't have a reader profile. Would you like to create one?", cancelButton, createProfileButton);
        ButtonType result = alert.getResult();
        if (result == createProfileButton) {
            requestProfile(userSession);
            return;
        }
        AlertManager.showAlert(Alert.AlertType.INFORMATION, "Rejected", "You won't have a reader profile,chill", ButtonType.OK);
    }

    private void requestProfile(UserSession userSession) {
        Either<Exception, RequestReaderProfileOutputModel> profileOutput = requestReaderProfileProcessor.process(RequestReaderProfileInputModel.builder().username(userSession.getUsername()).build());
        if (profileOutput.isRight()) {
            AlertManager.showAlert(Alert.AlertType.INFORMATION, "Information", "Your request to create a reader profile has been sent to the administrator.", ButtonType.OK);
            return;
        }
        AlertManager.showAlert(Alert.AlertType.ERROR, "ERROR", "There was error while trying to sent the request please try again", ButtonType.OK);
    }

    @FXML
    public void favouriteBooks(ActionEvent actionEvent) {
        gridPane.getChildren().clear();
        if (favouriteBooks != null && !favouriteBooks.isEmpty()) {
            showBooks(favouriteBooks);
            return;
        }
        gridPane.add(new Label("No favourite books"), 0, 0);
    }

    @FXML
    public void recommendedBooks(ActionEvent actionEvent) {
        gridPane.getChildren().clear();
        if (recommendedBooks != null && !recommendedBooks.isEmpty()) {
            int index = 0;
            for (Map.Entry<String, List<BookDataForReader>> map : recommendedBooks.entrySet()) {
                String genreName = map.getKey();
                VBox forGenre = new VBox();
                List<BookDataForReader> books = map.getValue();
                HBox mainBox = new HBox();
                AtomicInteger j = new AtomicInteger(0);
                Button leftArrow = new Button("<");
                Button rightArrow = new Button(">");
                setActionOnLeftArrow(leftArrow, j, forGenre, genreName, books, mainBox, rightArrow);
                setActionOnRightArrow(rightArrow, j, books, forGenre, genreName, mainBox, leftArrow);
                mapButtonsAndVbox(forGenre, genreName, books, j, mainBox, leftArrow, rightArrow);
                gridPane.add(forGenre, 0, index);
                index++;

            }
            return;
        }
        gridPane.add(new Label("Don't have recommended genres"), 0, 0);
    }

    private void setActionOnRightArrow(Button rightArrow, AtomicInteger j, List<BookDataForReader> books, VBox forGenre, String genreName, HBox mainBox, Button leftArrow) {
        rightArrow.setOnAction(event -> {
            j.getAndIncrement();
            if (j.get() >= books.size()) {
                j.set(books.size() - 1);
            }
            mapButtonsAndVbox(forGenre, genreName, books, j, mainBox, leftArrow, rightArrow);
        });
    }

    private void setActionOnLeftArrow(Button leftArrow, AtomicInteger j, VBox forGenre, String genreName, List<BookDataForReader> books, HBox mainBox, Button rightArrow) {
        leftArrow.setOnAction(event -> {
            j.getAndDecrement();
            if (j.get() < 0) {
                j.set(0);
            }
            mapButtonsAndVbox(forGenre, genreName, books, j, mainBox, leftArrow, rightArrow);
        });
    }

    private void mapButtonsAndVbox(VBox forGenre, String genreName, List<BookDataForReader> books, AtomicInteger j, HBox mainBox, Button leftArrow, Button rightArrow) {
        mainBox.getChildren().clear();
        forGenre.getChildren().clear();
        BookDataForReader data = books.get(j.get());
        VBox vBox = getBookData(data);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setSpacing(10);
        mainBox.getChildren().addAll(
                leftArrow, vBox, rightArrow
        );
        forGenre.setSpacing(10);
        Label label = new Label(genreName);
        label.setAlignment(Pos.CENTER);
        //  forGenre.setAlignment(Pos.CENTER);
        forGenre.getChildren().addAll(
                label,
                mainBox,
                new Separator()
        );
    }

    @NotNull
    private VBox getBookData(BookDataForReader data) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        File file = new File(data.getPathToImage());
        ImageView imageView = new ImageView(new Image(file.toURI().toString()));
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        Button button = new Button("Go");
        Label label = new Label(data.getTitle());
        label.setAlignment(Pos.TOP_CENTER);
        button.setOnAction(e -> {
            try {
                setButtonFunctionality(data);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        vBox.getChildren().addAll(
                label,
                imageView,
                button
        );
        return vBox;
    }

    @FXML
    public void toRead(ActionEvent actionEvent) {
        gridPane.getChildren().clear();
        if (wantsToRead != null && !wantsToRead.isEmpty()) {
            showBooks(wantsToRead);
            return;
        }
        gridPane.add(new Label("No books in wants to read"), 0, 0);
        // AlertManager.showAlert(Alert.AlertType.ERROR, "Error", "You don't have books in wants to read", ButtonType.OK);

    }

    @FXML
    public void readBooks(ActionEvent actionEvent) {
        gridPane.getChildren().clear();
        if (readBooks != null && !readBooks.isEmpty()) {
            showBooks(readBooks);
            return;
        }
        gridPane.add(new Label("No read books"), 0, 0);
        // AlertManager.showAlert(Alert.AlertType.ERROR, "Error", "You don't have books in already read books", ButtonType.OK);
    }

    private void showBooks(List<BookDataForReader> books) {
        int j = 0;
        int index = 0;
        while (true) {
            gridPane.getRowConstraints().add(new RowConstraints());
            for (int k = 0; k < 2; k++) {
                if (index >= books.size()) {
                    break;
                }
                BookDataForReader data = books.get(index);
                VBox vBox = new VBox();
                File file = new File(data.getPathToImage());
                ImageView imageView = new ImageView(new Image(file.toURI().toString()));
                imageView.setFitHeight(200);
                imageView.setFitWidth(200);
                Button button = new Button("Go");
                int finalI = index;
                Label label = new Label(data.getTitle());
                label.setAlignment(Pos.CENTER);
                button.setOnAction(e -> {
                    try {
                        setButtonFunctionality(books, finalI);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                vBox.getChildren().addAll(
                        label,
                        imageView,
                        button
                );
                vBox.setAlignment(Pos.CENTER);
                vBox.setSpacing(20);
                gridPane.add(vBox, k, j);
                index++;
            }
            if (index >= books.size()) {
                break;
            }
            j++;
        }
        gridPane.setHgap(200);
        gridPane.setVgap(50);
        gridPane.setPadding(new Insets(20, 0, 0, 20));
    }

    private void setButtonFunctionality(List<BookDataForReader> booksData, int finalI) throws IOException {
        BookDataForReader book = booksData.get(finalI);
        BookDataController.booksData = conversionService.convert(book, CommonBooksProperties.class);
        //setPath("/bg/tu_varna/sit/library/presentation.views/user/book_data_for_user/pages/book-data-for-user-view.fxml");
        setPath(USER_BOOK_DATA_FOR_USER);
        FXMLLoader loader = changeScene((Stage) gridPane.getScene().getWindow());
        BookDataController controller = loader.getController();
        controller.change();
    }

    private void setButtonFunctionality(BookDataForReader book) throws IOException {
       // setPath("/bg/tu_varna/sit/library/presentation.views/user/book_data_for_user/pages/book-data-for-user-view.fxml");
        setPath(USER_BOOK_DATA_FOR_USER);
        BookDataController.booksData = conversionService.convert(book, CommonBooksProperties.class);
        FXMLLoader loader = changeScene((Stage) gridPane.getScene().getWindow());
        BookDataController controller = loader.getController();
        controller.change();
    }
}
