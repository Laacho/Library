package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.core.find_book_by_inventory_number.FindBookByInventoryNumberProcessor;
import bg.tu_varna.sit.library.core.save_in_archived.SaveInArchivedProcessor;
import bg.tu_varna.sit.library.core.save_to_discard.SaveInDiscardProcessor;
import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.find_book_by_inventory_number.FindBookByInventoryNumberInputModel;
import bg.tu_varna.sit.library.models.find_book_by_inventory_number.FindBookByInventoryNumberOperationModel;
import bg.tu_varna.sit.library.models.find_book_by_inventory_number.FindBookByInventoryNumberOutputModel;
import bg.tu_varna.sit.library.models.save_to_archived.SaveInArchivedInputModel;
import bg.tu_varna.sit.library.models.save_to_archived.SaveInArchivedOperationModel;
import bg.tu_varna.sit.library.models.save_to_archived.SaveToArchivedOutputModel;
import bg.tu_varna.sit.library.models.save_to_discard.SaveToDiscardInputModel;
import bg.tu_varna.sit.library.models.save_to_discard.SaveToDiscardOperationModel;
import bg.tu_varna.sit.library.models.save_to_discard.SaveToDiscardOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Set;

public class UpdateStatusController extends Controller {
    @FXML
    private Button addToArchivedDB;
    @FXML
    private Button addToDiscardDB;
    @FXML
    private RadioButton archiveRadioButton;
    @FXML
    private RadioButton discardRadioButton;
    @FXML
    private TextArea reasonTextArea;
    @FXML
    private TextField inventoryNumberField;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblInventoryNumber;
    @FXML
    private Label lblGenre;
    @FXML
    private Label lblPublisher;
    @FXML
    private Label lblStatusBefore;
    @FXML
    private Label lblStatusAfter;
    @FXML
    private Label lblAuthors;
    @FXML
    private ImageView imageView;
    private Boolean foundBook;
    private Book book;

    private final FindBookByInventoryNumberOperationModel findBookByInventoryNumberOperationModel;
    private final SaveInArchivedOperationModel saveInArchivedProcessor;
    private final SaveToDiscardOperationModel saveToDiscardProcessor;

    public UpdateStatusController( ) {
        this.saveInArchivedProcessor = SingletonFactory.getSingletonInstance(SaveInArchivedProcessor.class);
        this.findBookByInventoryNumberOperationModel = SingletonFactory.getSingletonInstance(FindBookByInventoryNumberProcessor.class);
        this.saveToDiscardProcessor=SingletonFactory.getSingletonInstance(SaveInDiscardProcessor.class);
        foundBook = false;
    }

    @FXML
    public void searchByInventoryNumber(ActionEvent actionEvent) {
        if(!inventoryNumberField.getText().isEmpty()) {
            FindBookByInventoryNumberInputModel input = FindBookByInventoryNumberInputModel.builder()
                    .inventoryNumber(inventoryNumberField.getText())
                    .build();
            Either<Exception, FindBookByInventoryNumberOutputModel> process = findBookByInventoryNumberOperationModel.process(input);
            if(process.isRight()) {
                Book book = process.get().getBook();
                lblTitle.setText("Title: " + book.getTitle());
                lblInventoryNumber.setText("Inventory Number: " + book.getInventoryNumber());
                lblGenre.setText("Genre: " + book.getGenre().getName());
                lblPublisher.setText("Publisher: " + book.getPublisher().getName());
                lblStatusBefore.setText("Status before\nborrowing: "+book.getBookStatusBeforeBorrow());
                lblStatusAfter.setText("Status after\nborrowing: "+book.getBookStatusAfterBorrow());
                StringBuilder sb=new StringBuilder();
                Set<Author> authors = book.getAuthors();
                for (Author author : authors) {
                    sb.append(author.getFirstName()).append(" ")
                            .append(author.getLastName()).append("\n");
                }
                lblAuthors.setText("Authors:\n"+sb);
                File file = new File(book.getPath());
                imageView.setImage(new Image(file.toURI().toString()));
                foundBook = true;
                this.book=book;
            }
            else{
                foundBook = false;
                AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","Error while processing entity",ButtonType.OK);
            }
        }
        else{
            foundBook=false;
            AlertManager.showAlert(Alert.AlertType.ERROR,"Empty field!","Enter an inventory number", ButtonType.OK);
        }
    }
    @FXML
    public void disableArchive(ActionEvent actionEvent) {
      addToArchivedDB.setDisable(true);
      addToDiscardDB.setDisable(false);
      archiveRadioButton.setSelected(false);
      reasonTextArea.setDisable(false);

    }
    @FXML
    public void disableDiscard(ActionEvent actionEvent) {
        addToDiscardDB.setDisable(true);
        reasonTextArea.setDisable(true);
        addToArchivedDB.setDisable(false);
        discardRadioButton.setSelected(false);
    }
    @FXML
    public void addToArchiveTable(ActionEvent actionEvent) {
        if(foundBook && archiveRadioButton.isSelected()) {
            SaveInArchivedInputModel input = SaveInArchivedInputModel.builder()
                    .book(this.book)
                    .build();
            Either<Exception, SaveToArchivedOutputModel> process = saveInArchivedProcessor.process(input);
            if (process.isRight()) {
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Congrats!","Successfully saved book!",ButtonType.OK);
            }
            else{
                AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","Error while saving book!", ButtonType.OK);
            }
        }
        else{
           AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","Please select a book!", ButtonType.OK);
        }
    }


    @FXML
    public void addToDiscardTable(ActionEvent actionEvent) {
        if(foundBook && discardRadioButton.isSelected() && !reasonTextArea.getText().isBlank()) {
            SaveToDiscardInputModel input = SaveToDiscardInputModel.builder()
                    .book(this.book)
                    .build();
            Either<Exception, SaveToDiscardOutputModel> process = saveToDiscardProcessor.process(input);
            if (process.isRight()){
               AlertManager.showAlert(Alert.AlertType.INFORMATION,"Congrats!","Successfully saved book!",ButtonType.OK);
            }
            else{
                AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","Error while saving book!", ButtonType.OK);
            }
        }
        else{
            AlertManager.showAlert(Alert.AlertType.ERROR,"Error!","Please select a book!", ButtonType.OK);
        }
    }
}
