package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.core.addBook.AddBookProcessor;
import bg.tu_varna.sit.library.core.addGenre.CheckGenreProcessor;
import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.models.addBook.AddBookInputModel;
import bg.tu_varna.sit.library.models.addBook.AddBookOperationModel;
import bg.tu_varna.sit.library.models.addBook.AddBookOutputModel;
import bg.tu_varna.sit.library.models.addGenre.CheckGenreInputModel;
import bg.tu_varna.sit.library.models.addGenre.CheckGenreOperationModel;
import bg.tu_varna.sit.library.models.addGenre.CheckGenreOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import io.vavr.control.Either;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class AddBookController extends Controller{
    private final AddBookOperationModel addBookOperation;
    private final CheckGenreOperationModel checkGenreOperation;
    @FXML
    private TextField authorsEntry;
    @FXML
    private Button editButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button removeButton;
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
    private TextField quantity;
    @FXML
    private ComboBox<Long> locationRow;
    private AddBookInputModel.AddBookInputModelBuilder addBookInputModel;

    public AddBookController() {
       // this.addBookOperation = SingletonFactory.getSingletonInstance(AddBookPro.class);
        this.checkGenreOperation=SingletonFactory.getSingletonInstance(CheckGenreProcessor.class);
        this.addBookOperation=SingletonFactory.getSingletonInstance(AddBookProcessor.class);
        this.addBookInputModel= AddBookInputModel.builder();
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
    @FXML
    public void addAuthorByKey(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER && !authorsEntry.getText().isEmpty()) {
            addAuthor(new ActionEvent());
        }
    }

    @FXML
    public void addGenre(KeyEvent event) {
        if(event.getCode()==KeyCode.ENTER && !genre.getText().isEmpty()) {
            CheckGenreInputModel input = CheckGenreInputModel.builder()
                    .genre(genre.getText())
                    .build();
            Either<Exception, CheckGenreOutputModel> process = checkGenreOperation.process(input);
            if(process.isRight()) {
                CheckGenreOutputModel result = process.get();
                        if(result.getIsGenrePresent()){
                            locationRow.setItems(FXCollections.observableList(process.get().getRowNums()));
                        }
                        else{
                            Alert alert = AlertManager.showAlert(Alert.AlertType.CONFIRMATION, "Choose!",
                                    "This genre doesnt exists! Do you want to add it?", ButtonType.YES, ButtonType.NO);
                            ButtonType result1 = alert.getResult();
                            if(result1 == ButtonType.YES) {
                                addBookInputModel.genre(genre.getText());
                                locationRow.setItems(FXCollections.observableList(process.get().getRowNums()));
                            }
                            else{
                                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Change!",
                                        "Change the genre to be a valid one!",
                                        ButtonType.CLOSE);
                            }
                        }
            }
            else{
                AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","Error occurred while processing genre");
            }
        }
    }

    @FXML
    public void addBook(ActionEvent event) {
        ObservableList<String> items = listView.getItems();
        Set<Author> authors=new HashSet<>();
        for (String item : items) {
            String[] split = item.split("\\s++");
            if(split.length == 1) {
                Author build = Author.builder()
                        .firstName(split[0])
                        .build();
                authors.add(build);
            }
            else if(split.length == 2) {
                Author build = Author.builder()
                        .firstName(split[0])
                        .lastName(split[1])
                        .build();
                authors.add(build);
            }

        }
        AddBookInputModel inputModel = addBookInputModel
                .authors(authors)
                .isbn(isbn.getText())
                .title(title.getText())
                .genre(genre.getText())
                .publisher(publisher.getText())
                .inventoryNumber(inventoryNumber.getText())
                .price(BigDecimal.valueOf(Double.parseDouble(price.getText())))
                .quantity(Long.valueOf(quantity.getText()))
                .row(locationRow.getSelectionModel().getSelectedItem())
                .build();
        Either<Exception, AddBookOutputModel> process = addBookOperation.process(inputModel);
        if(process.isRight()) {
            AddBookOutputModel result = process.get();
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Congrats!","You added a book! It only took 5 hours!",ButtonType.CLOSE);
        }
        else{
            AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","Error occurred while processing genre");
        }
    }


}
