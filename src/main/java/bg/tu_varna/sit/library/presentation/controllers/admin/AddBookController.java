package bg.tu_varna.sit.library.presentation.controllers.admin;

import bg.tu_varna.sit.library.core.admin.add_book.AddBookProcessor;
import bg.tu_varna.sit.library.core.admin.add_book.CheckGenreProcessor;
import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.models.add_book.AddBookInputModel;
import bg.tu_varna.sit.library.models.add_book.AddBookOperationModel;
import bg.tu_varna.sit.library.models.add_book.AddBookOutputModel;
import bg.tu_varna.sit.library.models.add_genre.CheckGenreInputModel;
import bg.tu_varna.sit.library.models.add_genre.CheckGenreOperationModel;
import bg.tu_varna.sit.library.models.add_genre.CheckGenreOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import io.vavr.control.Either;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import static bg.tu_varna.sit.library.utils.ConstantsPaths.ADMIN_PATH_TO_SAVE_IMAGES;

public class AddBookController extends AdminController implements Initializable {

    @FXML
    private Label shelfLabel;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField authorsEntry;
    @FXML
    private Button addButton;
    @FXML
    private ListView<String> listView;
    @FXML
    private TextField isbn;
    @FXML
    private TextField title;
    @FXML
    private TextField genre;
    @FXML
    private TextField publisher;
    @FXML
    private TextField inventoryNumber;
    @FXML
    private TextField price;
    @FXML
    private ComboBox<Long> locationRow;
    private final AddBookOperationModel addBookOperation;
    private final CheckGenreOperationModel checkGenreOperation;
    private AddBookInputModel.AddBookInputModelBuilder addBookInputModel;
    private String pathFromUser;

    public AddBookController() {
        this.checkGenreOperation = SingletonFactory.getSingletonInstance(CheckGenreProcessor.class);
        this.addBookOperation = SingletonFactory.getSingletonInstance(AddBookProcessor.class);
        this.addBookInputModel = AddBookInputModel.builder();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disableFocusOnButtons();
    }

    @FXML
    public void addAuthor(ActionEvent event) {
        listView.getItems().add(authorsEntry.getText());
        authorsEntry.setText("");
        authorsEntry.requestFocus();
    }

    @FXML
    public void removeAuthor(ActionEvent event) {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        listView.getItems().remove(selectedItem);
    }

    @FXML
    public void editAuthor(ActionEvent event) {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        authorsEntry.setText(selectedItem);
        listView.getItems().remove(selectedItem);
    }

    @FXML
    public void clearTextField(ActionEvent event) {
        authorsEntry.setText("");
    }
    private boolean isValidSpaceCount(String input) {
        if (input == null || input.isBlank()) {
            return true;
        }
        String[] words = input.trim().split("\\s+");
        return words.length >= 1 && words.length <= 2; // Return true if there are 2 or fewer words, false otherwise
    }

    @FXML
    public void addAuthorByKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER && !authorsEntry.getText().isEmpty()) {
            if(isValidSpaceCount(authorsEntry.getText())) {
                addAuthor(new ActionEvent());
            }
            else{
                AlertManager.showAlert(Alert.AlertType.INFORMATION, "Error!", "Author name must be 1 or 2 words!", ButtonType.CLOSE);
                authorsEntry.requestFocus();
            }
        }
    }

    @FXML
    public void addGenre(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !genre.getText().isEmpty()) {
            shelfLabel.setText("Shelf: ");
            CheckGenreInputModel input = CheckGenreInputModel.builder()
                    .genre(genre.getText())
                    .build();
            Either<Exception, CheckGenreOutputModel> process = checkGenreOperation.process(input);
            if (process.isRight()) {
                CheckGenreOutputModel result = process.get();
                if (result.getIsGenrePresent()) {
                    locationRow.setItems(FXCollections.observableList(process.get().getRowNums()));
                } else {
                    Alert alert = AlertManager.showAlert(Alert.AlertType.CONFIRMATION, "Choose!",
                            "This genre doesnt exists! Do you want to add it?", ButtonType.YES, ButtonType.NO);
                    ButtonType result1 = alert.getResult();
                    if (result1 == ButtonType.YES) {
                        addBookInputModel.genre(genre.getText());
                        locationRow.setItems(FXCollections.observableList(process.get().getRowNums()));
                    } else {
                        AlertManager.showAlert(Alert.AlertType.INFORMATION, "Change!",
                                "Change the genre to be a valid one!",
                                ButtonType.CLOSE);
                    }
                }
                String newText = shelfLabel.getText()+genre.getText().toUpperCase();
                shelfLabel.setText(newText);
                shelfLabel.setPrefWidth(computeTextWidth(shelfLabel,newText));
            }
        }
    }
    private double computeTextWidth(Label label, String text) {
        Text tempText = new Text(text);
        Font font = label.getFont();
        tempText.setFont(font);
        new javafx.scene.Group(tempText);
        return Math.ceil(tempText.getLayoutBounds().getWidth()) + 5;
    }
    @FXML
    public void addBookInDB(ActionEvent event) {
        validateInput();
        Set<Author> authors = getAuthors();
        AddBookInputModel inputModel = buildInput(authors);
        Either<Exception, AddBookOutputModel> process = addBookOperation.process(inputModel);
        if (process.isRight()) {
             AlertManager.showAlert(Alert.AlertType.INFORMATION, "Congrats!", "You added a book!", ButtonType.CLOSE);
             clearScene();
        } else {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Error!", "Error occurred while processing saving book",ButtonType.CLOSE);
        }
    }

    private void validateInput() {
        if (listView.getItems().isEmpty() || isbn.getText().isEmpty() || title.getText().isEmpty()
            || genre.getText().isEmpty() || publisher.getText().isEmpty()
            || inventoryNumber.getText().isEmpty() || price.getText().isEmpty()
            || pathFromUser == null || pathFromUser.isEmpty()
            || locationRow.getValue() == null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Validation Error!", "All fields must be filled, a location must be selected, and an image must be uploaded!", ButtonType.CLOSE);
        }
    }





    private void clearScene() {
        imageView.setImage(null);
        listView.getItems().clear();
        locationRow.getItems().clear();
        isbn.setText("");
        title.setText("");
        genre.setText("");
        publisher.setText("");
        inventoryNumber.setText("");
        price.setText("");
    }

    @NotNull
    private Set<Author> getAuthors() {
        ObservableList<String> items = listView.getItems();
        Set<Author> authors = new HashSet<>();
        for (String item : items) {
            String[] split = item.split("\\s++");
            if (split.length == 1) {
                Author build = Author.builder()
                        .firstName(split[0])
                        .build();
                authors.add(build);
            } else if (split.length == 2) {
                Author build = Author.builder()
                        .firstName(split[0])
                        .lastName(split[1])
                        .build();
                authors.add(build);
            }

        }
        return authors;
    }

    private AddBookInputModel buildInput(Set<Author> authors) {
        return addBookInputModel
                .authors(authors)
                .isbn(isbn.getText())
                .title(title.getText())
                .genre(genre.getText())
                .publisher(publisher.getText())
                .inventoryNumber(inventoryNumber.getText())
                .price(BigDecimal.valueOf(Double.parseDouble(price.getText())))
                .row(locationRow.getSelectionModel().getSelectedItem())
                .path(pathFromUser)
                .build();
    }


    @FXML
    public void chooseImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(addButton.getScene().getWindow());
        if (selectedFile!=null) {
            try {
                Path path = Path.of(ADMIN_PATH_TO_SAVE_IMAGES);
                Path targetPath = path.resolve(selectedFile.getName());
                Files.copy(selectedFile.toPath(),targetPath, StandardCopyOption.REPLACE_EXISTING);
                pathFromUser=targetPath.toString();
                imageView.setImage(new Image(selectedFile.toURI().toString()));
            } catch (IOException e) {
                AlertManager.showAlert(Alert.AlertType.INFORMATION, "Error!", "Error occurred while processing image",ButtonType.OK);
            }
        }

    }



}
