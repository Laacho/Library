package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.core.discarded_books.DiscardedBooksProcessor;
import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.discarded_books.BooksData;
import bg.tu_varna.sit.library.models.discarded_books.DiscardedBooksInputModel;
import bg.tu_varna.sit.library.models.discarded_books.DiscardedBooksOperationModel;
import bg.tu_varna.sit.library.models.discarded_books.DiscardedBooksOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.converters.base.ConversionService;
import io.vavr.control.Either;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class DiscardedBooksTableViewController extends Controller implements Initializable {
    @FXML
    private TableView<BooksData> tableView;
    private TableColumn<BooksData, String> title;
    private TableColumn<BooksData, Long> quantity;
    private TableColumn<BooksData, String> reason;
    private TableColumn<BooksData, LocalDate> discardingDate;
    private TableColumn<BooksData, String> authors;
    private final DiscardedBooksOperationModel discardedBooksProcessor;
    private final ConversionService conversionService;

    public DiscardedBooksTableViewController() {
        discardedBooksProcessor = SingletonFactory.getSingletonInstance(DiscardedBooksProcessor.class);
        conversionService = SingletonFactory.getSingletonInstance(ConversionService.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Either<Exception, DiscardedBooksOutputModel> process = discardedBooksProcessor.process(DiscardedBooksInputModel.builder().build());
        if (process.isRight()) {
            List<BooksData> booksData = process.get().getBooksData();
            ObservableList<BooksData> observableList = FXCollections.observableList(booksData);
            initializeColumns();
            tableView.getColumns().addAll(title, authors, quantity, reason, discardingDate, authors);
            tableView.getColumns().remove(0);
            tableView.setItems(observableList);
        }
    }

    private void initializeColumns() {
        title = new TableColumn<>("Заглавие");
        title.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getTitle()));
        title.setPrefWidth(200);
        quantity = new TableColumn<>("Количество");
        quantity.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getQuantity()));
        reason = new TableColumn<>("Рафт");
        reason.setPrefWidth(400);
        reason.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getReason()));
        discardingDate = new TableColumn<>("Ред");
        discardingDate.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getDiscardingDate()));
        authors = new TableColumn<>("Автор");
        authors.setPrefWidth(200);
        authors.setCellValueFactory(cell -> {
            Set<Author> authors = cell.getValue().getAuthors();
            String authorsText = authors.stream()
                    .map(Author::toString)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(authorsText);
        });
    }

    @FXML
    public void home(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/admin_home_view/pages/admin-home-view.fxml");
        changeScene(actionEvent);
    }
    @FXML
    public void doubleClick(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            setPath("/bg/tu_varna/sit/library/presentation.views/book_data_view/pages/book-data-view.fxml");
            FXMLLoader loader = changeScene((Stage) tableView.getScene().getWindow());
            BookDataViewController controller = loader.getController();
            BooksData selectedItem = tableView.getSelectionModel().getSelectedItem();
            controller.setBooksData(conversionService.convert(selectedItem, CommonBooksProperties.class));
            controller.change();

        }
    }
}
