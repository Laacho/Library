package bg.tu_varna.sit.library.presentation.controllers.admin;

import bg.tu_varna.sit.library.core.admin.archived_books.ArchivedBooksProcessor;
import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.archived_books.ArchivedBooksInputModel;
import bg.tu_varna.sit.library.models.archived_books.ArchivedBooksOperationModel;
import bg.tu_varna.sit.library.models.archived_books.ArchivedBooksOutputModel;
import bg.tu_varna.sit.library.models.archived_books.BooksData;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.converters.base.ConversionService;
import io.vavr.control.Either;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.tu_varna.sit.library.utils.ConstantsPaths.ADMIN_CHANGE_TO_BOOK_VIEW;
import static bg.tu_varna.sit.library.utils.ConstantsPaths.ADMIN_DOUBLE_CLICK_FOR_BOOK_VIEW;

public class ArchivedBooksTableViewController extends AdminController implements Initializable {
    @FXML
    private TableView<BooksData> tableView;
    private TableColumn<BooksData, String> title;
    private TableColumn<BooksData, String> shelfName;
    private TableColumn<BooksData, String> rowNum;
    private TableColumn<BooksData, String> archivedDate;
    private TableColumn<BooksData, String> authors;
    private final ArchivedBooksOperationModel archivedBooksProcessor;
    private final ConversionService conversionService;

    public ArchivedBooksTableViewController() {
        conversionService = SingletonFactory.getSingletonInstance(ConversionService.class);
        archivedBooksProcessor = SingletonFactory.getSingletonInstance(ArchivedBooksProcessor.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Either<Exception, ArchivedBooksOutputModel> process = archivedBooksProcessor.process(ArchivedBooksInputModel.builder().build());
        if (process.isRight()) {
            List<BooksData> booksData = process.get().getBooksData();
            ObservableList<BooksData> observableList = FXCollections.observableList(booksData);
            initializeColumns();
            tableView.getColumns().addAll(title, authors, shelfName, rowNum, archivedDate);
            tableView.getColumns().remove(0);
            tableView.setItems(observableList);
        }
    }

    private void initializeColumns() {
        title = new TableColumn<>("Заглавие");
        title.setPrefWidth(120);
        title.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getTitle()));
        shelfName = new TableColumn<>("Рафт");
        shelfName.setPrefWidth(154);
        shelfName.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getLocation().getShelf()));
        rowNum = new TableColumn<>("Ред");
        rowNum.setPrefWidth(100);
        rowNum.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getLocation().getRowNum().toString()));
        archivedDate = new TableColumn<>("Дата");
        archivedDate.setPrefWidth(130);
        archivedDate.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getArchivedDate().toString()));
        authors = new TableColumn<>("Автор");
      authors.setPrefWidth(170);
        authors.setCellValueFactory(cell -> {
            Set<Author> authors = cell.getValue().getAuthors();
            String authorsText = authors.stream()
                    .map(Author::toString)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(authorsText);
        });
        title.getStyleClass().add("table-column-header");
        shelfName.getStyleClass().add("table-column-header");
        rowNum.getStyleClass().add("table-column-header");
        authors.getStyleClass().add("table-column-header");
        archivedDate.getStyleClass().add("table-column-header");
    }


    @FXML
    public void doubleClick(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            setPath(ADMIN_CHANGE_TO_BOOK_VIEW);
           // setPath("/bg/tu_varna/sit/library/presentation.views/admin/book_data_view/pages/book-data-view.fxml");
            FXMLLoader loader = changeScene((Stage) tableView.getScene().getWindow());
            BookDataViewController controller = loader.getController();
            BooksData selectedItem = tableView.getSelectionModel().getSelectedItem();
            controller.setBooksData(conversionService.convert(selectedItem, CommonBooksProperties.class));
            controller.change();

        }
    }
}
