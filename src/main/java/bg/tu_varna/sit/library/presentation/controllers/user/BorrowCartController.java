package bg.tu_varna.sit.library.presentation.controllers.user;

import bg.tu_varna.sit.library.core.user.borrow_cart.BorrowBooksProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.borrow_books.BorrowBooksInputModel;
import bg.tu_varna.sit.library.models.borrow_books.BorrowBooksOperationModel;
import bg.tu_varna.sit.library.models.borrow_books.BorrowBooksOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import bg.tu_varna.sit.library.utils.converters.base.ConversionService;
import bg.tu_varna.sit.library.utils.session.UserSession;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.Getter;
import org.controlsfx.control.spreadsheet.Grid;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BorrowCartController extends UserController implements Initializable {
    @FXML
    private DatePicker returnDate;
    @FXML
    private DatePicker borrowDate;
    @FXML
    private GridPane gridPaneLayout;
    private final ConversionService conversionService;

    private static int rows = 0;
    @Getter
    private static Set<CommonBooksProperties> borrowedBooks = new HashSet<>();
    private final BorrowBooksOperationModel borrowBooksProcessor;

    public BorrowCartController() {
        borrowBooksProcessor = SingletonFactory.getSingletonInstance(BorrowBooksProcessor.class);
        conversionService = SingletonFactory.getSingletonInstance(ConversionService.class);
    }


    public static void addBookInSet(CommonBooksProperties book) {
        borrowedBooks.add(book);
    }

    public static void removeBookInSet(CommonBooksProperties book) {
        borrowedBooks.remove(book);
    }

    @FXML
    public void borrowAllBooks() {
        if (!borrowedBooks.isEmpty() && returnDate.valueProperty().getValue().isAfter(LocalDate.now()) && borrowDate.getValue() != null) {
            UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
            if (!userSession.getVerified().booleanValue()) {
                AlertManager.showAlert(Alert.AlertType.ERROR, "Error", "You should be verified before borrowing books", ButtonType.OK);
                return;
            }
            BorrowBooksInputModel inputModel = BorrowBooksInputModel.builder()
                    .books(borrowedBooks)
                    .borrowDate(borrowDate.getValue())
                    .returnDate(returnDate.getValue())
                    .username(userSession.getUsername())
                    .build();
            Either<Exception, BorrowBooksOutputModel> process = borrowBooksProcessor.process(inputModel);
            if (process.isRight()) {
                AlertManager.showAlert(Alert.AlertType.INFORMATION, "Success", process.get().getMessage(), ButtonType.OK);
                    gridPaneLayout.getChildren().clear();
                    userSession.getCartBooks().clear();
                    borrowedBooks.clear();
            } else {
                AlertManager.showAlert(Alert.AlertType.ERROR, "Error", "Error while request to borrow books", ButtonType.OK);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (borrowedBooks.isEmpty()) {
            rows = 0;
        }

        UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
        if(userSession.getCartBooks()!=null || !userSession.getCartBooks().isEmpty()) {
            for (Book cartBook : userSession.getCartBooks()) {
                boolean toAdd = true;
                for (CommonBooksProperties borrowedBook : borrowedBooks) {
                    if (cartBook.getInventoryNumber().equals(borrowedBook.getInventoryNumber())) {
                        toAdd = false;
                    }
                }
                if (toAdd)
                    borrowedBooks.add(conversionService.convert(cartBook, CommonBooksProperties.class));
            }
        }
        if(borrowedBooks!=null && !borrowedBooks.isEmpty()) {
            for (CommonBooksProperties book : borrowedBooks) {
                ImageView imageView = getImageView(book);
                gridPaneLayout.add(imageView, 0, rows);
                Label label = getText(book);
                label.getStyleClass().add("smaller-text");
                Pane pane = new Pane(label);
                gridPaneLayout.add(pane, 1, rows);
                Button button = new Button();
                button.getStyleClass().add("transparent-button");
                File file = new File("src/main/resources/bg/tu_varna/sit/library/presentation.views/admin/approve_books/images/x.png");
                ImageView forButton = new ImageView(new Image(file.toURI().toString()));
                button.setGraphic(forButton);
                HBox hBox = new HBox(button);
                hBox.setAlignment(Pos.CENTER_RIGHT);
                gridPaneLayout.add(hBox, 2, rows);
                button.setOnAction(e -> setButtonFunctionality(e));
                gridPaneLayout.setHgap(150);
                rows++;
            }
        }
    }

    private void setButtonFunctionality(ActionEvent e) {
        Button source = (Button) e.getSource();
        HBox parent = (HBox) source.getParent();
        Integer rowIndex = GridPane.getRowIndex(parent);
        List<Node> nodesToRemove = new ArrayList<>();
        Label saved = new Label();
        for (Node node : gridPaneLayout.getChildren()) {
            Integer nodeRowIndex = GridPane.getRowIndex(node);
            if (nodeRowIndex != null && nodeRowIndex.equals(rowIndex)) {
                if (node instanceof Pane) {
                    Pane pane = (Pane) node;
                    for (Node child : pane.getChildren()) {
                        if (child instanceof Label) {
                            saved = (Label) child;
                        }
                    }
                }
                nodesToRemove.add(node);
            }
        }
        gridPaneLayout.getChildren().removeAll(nodesToRemove);
        String labelFromSelectedRow = saved.getText();
        String inventoryNumber = labelFromSelectedRow.substring(0, labelFromSelectedRow.indexOf(" "));
        CommonBooksProperties toDelete = null;
        for (CommonBooksProperties borrowedBook : borrowedBooks) {
            if (checkIfItIsTheSameBook(borrowedBook, inventoryNumber)) {
                toDelete = borrowedBook;
            }
        }
        UserSession userSession = SingletonFactory.getSingletonInstance(UserSession.class);
        boolean toRemove = false;
        Book book = null;
        for (Book cartBook : userSession.getCartBooks()) {
            if (cartBook.getInventoryNumber().equals(toDelete.getInventoryNumber())) {
                toRemove = true;
                book = cartBook;
                break;
            }
        }
        if (toRemove)
            userSession.getCartBooks().remove(book);
        removeBookInSet(toDelete);
    }

    private boolean checkIfItIsTheSameBook(CommonBooksProperties borrowedBook, String inventoryNumber) {
        return borrowedBook.getInventoryNumber().equals(inventoryNumber);
    }

    @NotNull
    private Label getText(CommonBooksProperties book) {
        String text = book.getInventoryNumber() + " title: " + book.getTitle() + "\n authors: " + book.getAuthors().stream().collect(Collectors.joining(", "));
        Label label = new Label(text);
        return label;
    }

    @NotNull
    private ImageView getImageView(CommonBooksProperties book) {
        File file = new File(book.getPathImage());
        ImageView imageView = new ImageView(new Image(file.toURI().toString()));
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        return imageView;
    }
}
