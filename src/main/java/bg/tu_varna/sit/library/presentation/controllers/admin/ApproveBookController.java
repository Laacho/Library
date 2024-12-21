package bg.tu_varna.sit.library.presentation.controllers.admin;

import bg.tu_varna.sit.library.core.approve_books.ApproveBooksProcessor;
import bg.tu_varna.sit.library.core.approve_books.UpdateBorrowedBooksProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.approve_books.ApproveBooksInputModel;
import bg.tu_varna.sit.library.models.approve_books.ApproveBooksOperationModel;
import bg.tu_varna.sit.library.models.approve_books.ApproveBooksOutputModel;
import bg.tu_varna.sit.library.models.approve_books.BooksForApproveData;
import bg.tu_varna.sit.library.models.update_borrowed_books.UpdateBorrowedBooksInputModel;
import bg.tu_varna.sit.library.models.update_borrowed_books.UpdateBorrowedBooksOperationModel;
import bg.tu_varna.sit.library.models.update_borrowed_books.UpdateBorrowedBooksOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import io.vavr.control.Either;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ApproveBookController extends AdminController implements Initializable {
    @FXML
    private Label userData;
    @FXML
    private Label title;
    @FXML
    private Label isbn;
    @FXML
    private Label inventoryNumber;
    @FXML
    private Label bookStatus;
    @FXML
    private Button approveButton;
    @FXML
    private Button declineButton;
    @FXML
    private ImageView imageBook;
    private int indexForRequest = 0;
    private int indexForBook = 0;
    private final ApproveBooksOperationModel approveBooksProcessor;
    private final UpdateBorrowedBooksOperationModel updateBorrowedBooksProcessor;
    private List<BooksForApproveData> books;
    private Map<String, List<Book>> booksWithSameISBN;
    private Set<String> alreadyApprovedBooks;
    private Set<Book> setToSetInTheSet;

    public ApproveBookController() {
        setToSetInTheSet = new HashSet<>();
        alreadyApprovedBooks = new HashSet<>();
        approveBooksProcessor = SingletonFactory.getSingletonInstance(ApproveBooksProcessor.class);
        updateBorrowedBooksProcessor = SingletonFactory.getSingletonInstance(UpdateBorrowedBooksProcessor.class);
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Either<Exception, ApproveBooksOutputModel> process = approveBooksProcessor.process(ApproveBooksInputModel.builder().build());
        if (process.isRight()) {
            books = process.get().getBooks();
            booksWithSameISBN = process.get().getBooksWithSameISBN();
            BooksForApproveData booksForApproveData = books.get(indexForRequest);
            List<Book> tempList = new ArrayList<>(booksForApproveData.getBooks());
            if (!tempList.isEmpty()) {
                Book next = tempList.get(indexForBook);
                indexForBook++;
                if (alreadyApprovedBooks.contains(next.getInventoryNumber())) {
                    AlertManager.showAlert(Alert.AlertType.INFORMATION, "It's not free", "This book is already taken", ButtonType.OK);
                    decline();
                    return;
                }
                alreadyApprovedBooks.add(next.getInventoryNumber());
                setToSetInTheSet.add(next);
                if (booksWithSameISBN.containsKey(next.getIsbn())) {
                    booksWithSameISBN.get(next.getIsbn()).remove(next);
                }
                setData(next, booksForApproveData);

            }
        } else {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Empty", "There is nothing to approve", ButtonType.OK);
            Platform.runLater(() -> {
                Stage curStage = (Stage) (title.getScene().getWindow());
                setPath("/bg/tu_varna/sit/library/presentation.views/admin_home_view/pages/admin-home-view.fxml");
                try {
                    changeScene(curStage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @FXML
    public void accept() {
        if (books != null && indexForRequest < books.size()) {
            if (indexForBook < books.get(indexForRequest).getBooks().size()) {
                setDataForBook();
            } else {
                getDeadline();
                books.get(indexForRequest).setBooks(setToSetInTheSet);
                setToSetInTheSet = new HashSet<>();
                indexForRequest++;
                if (indexForRequest < books.size()) {
                    List<Book> tempList = new ArrayList<>(books.get(indexForRequest).getBooks());
                    indexForBook = 0;
                    accept();
                } else {
                    AlertManager.showAlert(Alert.AlertType.INFORMATION, "All done", "There's no more requests", ButtonType.OK);
                    Either<Exception, UpdateBorrowedBooksOutputModel> process = updateBorrowedBooksProcessor.process(UpdateBorrowedBooksInputModel.builder().books(books).build());
                    if (process.isRight()) {
                        AlertManager.showAlert(Alert.AlertType.INFORMATION, "Successful", process.get().getMeesage(), ButtonType.OK);
                    }
                }
            }
        }
    }

    @FXML
    public void decline() {
        Book book = books.get(indexForRequest).getBooks().stream().toList().get(indexForBook);
        books.get(indexForRequest).getBooks().remove(book);
        if (books != null && indexForRequest < books.size()) {
            if (indexForBook < books.get(indexForRequest).getBooks().size()) {
                setDataForBook();
            } else {
                getDeadline();
                books.get(indexForRequest).setBooks(setToSetInTheSet);
                setToSetInTheSet = new HashSet<>();
                indexForRequest++;
                if (indexForRequest < books.size()) {
                    List<Book> tempList = new ArrayList<>(books.get(indexForRequest).getBooks());
                    indexForBook = 0;
                    decline();
                } else {
                    AlertManager.showAlert(Alert.AlertType.INFORMATION, "All done", "There's no more requests", ButtonType.OK);
                    Either<Exception, UpdateBorrowedBooksOutputModel> process = updateBorrowedBooksProcessor.process(UpdateBorrowedBooksInputModel.builder().books(books).build());
                    if (process.isRight()) {
                        AlertManager.showAlert(Alert.AlertType.INFORMATION, "Successful", process.get().getMeesage(), ButtonType.OK);
                    }
                }
            }
        }
    }

    private void setDataForBook() {
        Book next = books.get(indexForRequest).getBooks().stream().toList().get(indexForBook);
        if (alreadyApprovedBooks.contains(next.getInventoryNumber())) {
            if (booksWithSameISBN.containsKey(next.getIsbn())) {
                List<Book> booksLocal = booksWithSameISBN.get(next.getIsbn());
                if (booksLocal.isEmpty()) {
                    AlertManager.showAlert(Alert.AlertType.INFORMATION, "It's not free", "This book is already taken", ButtonType.OK);
                    decline();
                    return;
                }
                Book bookLocal = booksLocal.get(0);
                booksWithSameISBN.get(next.getIsbn()).remove(0);
                next = bookLocal;
            } else {
                AlertManager.showAlert(Alert.AlertType.ERROR, "Already exists", "Nyama nalichnost", ButtonType.OK);
                decline();
                return;
            }
        }
        setToSetInTheSet.add(next);
        indexForBook++;
        alreadyApprovedBooks.add(next.getInventoryNumber());
        BooksForApproveData booksForApproveData = books.get(indexForRequest);
        setData(next, booksForApproveData);
    }

    private void setData(Book next, BooksForApproveData booksForApproveData) {
        userData.setText("Това са книгите,които потребител "
                + booksForApproveData.getUser().getFirstName()
                + " "
                + booksForApproveData.getUser().getLastName() + " с id: "
                + booksForApproveData.getUser().getUserCredentials().getId()
                + " желае да заеме.");
        title.setText("Title " + next.getTitle());
        isbn.setText("ISBN " + next.getIsbn());
        inventoryNumber.setText("Inventory number " + next.getInventoryNumber());
        bookStatus.setText("Book status " + next.getBookStatusBeforeBorrow());
        File file = new File(next.getPath());
        imageBook.setImage(new Image(file.toURI().toString()));
    }

    private void getDeadline() {
        if (books.get(indexForRequest).getBooks().isEmpty()) {
            AlertManager.showAlert(Alert.AlertType.INFORMATION, "All declined", "All books are decline", ButtonType.OK);
            return;
        }
        Alert alert = AlertManager.showAlert(Alert.AlertType.CONFIRMATION, "Deadline", "Please select date for deadline", ButtonType.OK);
        DatePicker datePicker = new DatePicker();
        VBox content = new VBox(datePicker);
        content.setSpacing(10);
        alert.getDialogPane().setContent(content);
        alert.showAndWait().ifPresent(response -> {
            LocalDate deadline = datePicker.getValue();
            books.get(indexForRequest).setDeadline(deadline);
        });
    }
}
