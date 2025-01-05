package bg.tu_varna.sit.library.presentation.controllers.admin;

import bg.tu_varna.sit.library.core.admin.overdue_books.OverdueBooksProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.overdue_books.OverdueBooks;
import bg.tu_varna.sit.library.models.overdue_books.OverdueBooksInputModel;
import bg.tu_varna.sit.library.models.overdue_books.OverdueBooksOperationModel;
import bg.tu_varna.sit.library.models.overdue_books.OverdueBooksOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.session.EmailSession;
import io.vavr.control.Either;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class OverdueBooksController extends AdminController implements Initializable {
    @FXML
    private TableView<OverdueBooks> tableView;
    private TableColumn<OverdueBooks, String> borrowedDate;
    private TableColumn<OverdueBooks, String> returnDate;
    private TableColumn<OverdueBooks, String> returnDeadline;
    private TableColumn<OverdueBooks, Long> user;
    private TableColumn<OverdueBooks, String> booksColumn;
    private TableColumn<OverdueBooks, Button> returnButton;
    private final OverdueBooksOperationModel overdueBooksProcessor;

    public OverdueBooksController() {
        overdueBooksProcessor = SingletonFactory.getSingletonInstance(OverdueBooksProcessor.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Either<Exception, OverdueBooksOutputModel> process = overdueBooksProcessor.process(OverdueBooksInputModel.builder().build());
        if(process.isRight()){
            OverdueBooksOutputModel overdueBooksOutputModel = process.get();
            List<OverdueBooks> overdueBooks = overdueBooksOutputModel.getOverdueBooks();
            setTableData(overdueBooks);
        }
    }

    private void setTableData(List<OverdueBooks> overdueBooks) {
        user = new TableColumn<>("Потребител No");
        user.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getUser().getId()));
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
        returnButton = new TableColumn<>("Send Email");
        returnButton.setCellValueFactory(cell -> {
            Button button = new Button("Send");
            button.setOnAction(event -> {
                EmailSession.toEmail=(cell.getValue().getUser().getUserCredentials().getEmail());
                try {
                    sendEmail(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            return new SimpleObjectProperty<>(button);
        });
        tableView.getColumns().addAll(user, borrowedDate, returnDate, returnDeadline, booksColumn, returnButton);
        tableView.getColumns().remove(0);
        ObservableList<OverdueBooks> observableList = FXCollections.observableList(overdueBooks);
        tableView.setItems(observableList);
        user.getStyleClass().add("table-column-header");
        borrowedDate.getStyleClass().add("table-column-header");
        returnDate.getStyleClass().add("table-column-header");
        returnDeadline.getStyleClass().add("table-column-header");
        booksColumn.getStyleClass().add("table-column-header");
        returnButton.getStyleClass().add("table-column-header");
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

    private boolean checkForValidId(String id) {
        try {
            Long.parseLong(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
