package bg.tu_varna.sit.library.presentation.controllers.admin;

import bg.tu_varna.sit.library.core.admin.return_books.ReturnBookProcessor;
import bg.tu_varna.sit.library.core.admin.return_books.UpdateReturnedBooksProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.enums.BookStatus;
import bg.tu_varna.sit.library.models.return_books.BooksForReturn;
import bg.tu_varna.sit.library.models.return_books.ReturnBooksInputModel;
import bg.tu_varna.sit.library.models.return_books.ReturnBooksOperationModel;
import bg.tu_varna.sit.library.models.return_books.ReturnBooksOutputModel;
import bg.tu_varna.sit.library.models.update_returned_books.UpdateReturnedBooksInputModel;
import bg.tu_varna.sit.library.models.update_returned_books.UpdateReturnedBooksOperationModel;
import bg.tu_varna.sit.library.models.update_returned_books.UpdateReturnedBooksOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import io.vavr.control.Either;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class ReturnBookController extends AdminController implements Initializable {
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<BooksForReturn> tableView;
    private TableColumn<BooksForReturn, String> borrowedDate;
    private TableColumn<BooksForReturn, String> returnDate;
    private TableColumn<BooksForReturn, String> returnDeadline;
    private TableColumn<BooksForReturn, String> user;
    private TableColumn<BooksForReturn, String> booksColumn;
    private TableColumn<BooksForReturn, Button> returnButton;
    private final ReturnBooksOperationModel returnBooksProcessor;
    private Long userId;
    private final UpdateReturnedBooksOperationModel updateReturnedBooksProcessor;

    public ReturnBookController() {
        returnBooksProcessor = SingletonFactory.getSingletonInstance(ReturnBookProcessor.class);
        updateReturnedBooksProcessor = SingletonFactory.getSingletonInstance(UpdateReturnedBooksProcessor.class);
    }

    @FXML
    public void searchUser(ActionEvent actionEvent) {
        String username = searchTextField.getText();
        if (username.isEmpty()) {
            AlertManager.showAlert(Alert.AlertType.ERROR, "It's empty", "The username can not be empty! Please enter valid username!", ButtonType.OK);
            return;
        }
        Either<Exception, ReturnBooksOutputModel> process = returnBooksProcessor.process(ReturnBooksInputModel.builder().username(username).build());
        if (process.isRight()) {
            ReturnBooksOutputModel returnBooksOutputModel = process.get();
            List<BooksForReturn> booksForReturns = returnBooksOutputModel.getBooksForReturns();
            userId = returnBooksOutputModel.getUserId();
            setTableData(username, booksForReturns);
        }

    }

    private void setTableData(String id, List<BooksForReturn> booksForReturns) {
        user = new TableColumn<>("Потребителско име");
        user.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(id));
        borrowedDate = new TableColumn<>("Дата на вземане");
        borrowedDate.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getBorrowingDate().toString()));
        returnDate = new TableColumn<>("Дата на връщане");
        returnDate.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getReturnDate().toString()));
        returnDeadline = new TableColumn<>("Краен срок");
        returnDeadline.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getDeadline().toString()));
        booksColumn = new TableColumn<>("Книги");
        booksColumn.setCellValueFactory(cell -> {
            Set<Book> books = cell.getValue().getBooks();
            String booksText = getBooksText(books);
            return new SimpleStringProperty(booksText);
        });
        returnButton = new TableColumn<>("Return");
        returnButton.setCellValueFactory(cell -> {
            Button button = new Button("Return");
            button.setOnAction(event -> {
                setActivity(id, booksForReturns, cell);
            });
            return new SimpleObjectProperty<>(button);
        });
        tableView.getColumns().addAll(user, borrowedDate, returnDate, returnDeadline, booksColumn, returnButton);
        tableView.getColumns().remove(0);
        ObservableList<BooksForReturn> observableList = FXCollections.observableList(booksForReturns);
        tableView.setItems(observableList);
    }

    private void setActivity(String id, List<BooksForReturn> booksForReturns, TableColumn.CellDataFeatures<BooksForReturn, Button> cell) {
        BooksForReturn bookSelected = cell.getValue();
        AlertManager.showAlert(Alert.AlertType.INFORMATION, "Choose status", "You have to choose the status for every book after returning", ButtonType.OK);
        addComboBoxForEveryBookForStatusAfterBorrow(bookSelected);
        Either<Exception, UpdateReturnedBooksOutputModel> processUpdate = updateReturnedBooksProcessor.process(UpdateReturnedBooksInputModel.builder().books(bookSelected).userId(userId).build());
        if (processUpdate.isRight()) {
            AlertManager.showAlert(Alert.AlertType.INFORMATION, "All updated", "The books are returned", ButtonType.OK);
        }
        booksForReturns.remove(bookSelected);
    }

    private void addComboBoxForEveryBookForStatusAfterBorrow(BooksForReturn bookSelected) {
        for (Book book : bookSelected.getBooks()) {
            Dialog<Void> dialog = new Dialog<>();
            dialog.setContentText(book.getTitle() + " " + book.getInventoryNumber());
            dialog.setTitle(book.getTitle());
            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.setValue("GOOD");
            comboBox.setItems(FXCollections.observableList(List.of("GOOD", "ARCHIVED", "DISCARDED")));
            VBox content = new VBox(50);
            content.getChildren().add(comboBox);
            dialog.getDialogPane().setContent(content);
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(okButton);
            dialog.showAndWait();
            String value = comboBox.getValue();
            book.setBookStatusAfterBorrow(BookStatus.valueOf(value));
        }
    }

    @NotNull
    private static String getBooksText(Set<Book> books) {
        String booksText = books.stream()
                .map(book -> String.format("Заглавие: %s, Инв. №: %s, Статус: %s",
                        book.getTitle(),
                        book.getInventoryNumber(),
                        book.getBookStatusBeforeBorrow()))
                .reduce((b1, b2) -> b1 + "\n" + b2)
                .orElse("");
        return booksText;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disableFocusOnButtons();
    }
}
