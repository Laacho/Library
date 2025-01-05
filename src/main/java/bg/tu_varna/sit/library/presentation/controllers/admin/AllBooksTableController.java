package bg.tu_varna.sit.library.presentation.controllers.admin;

import bg.tu_varna.sit.library.core.user.all_books.AllBooksProcessor;
import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.all_books.AllBooksInputModel;
import bg.tu_varna.sit.library.models.all_books.AllBooksOperationModel;
import bg.tu_varna.sit.library.models.all_books.AllBooksOutputModel;
import bg.tu_varna.sit.library.models.all_books.BooksData;
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

public class AllBooksTableController extends AdminController implements Initializable {
    @FXML
    private TableView<BooksData> tableView;
    private TableColumn<BooksData, String> title;
    private TableColumn<BooksData, String> shelfName;
    private TableColumn<BooksData, Long> rowNum;
    private TableColumn<BooksData, String> authors;
    private final AllBooksOperationModel allBooksProcessor;
    private List<BooksData> booksData;
    private final ConversionService conversionService;


    public AllBooksTableController() {
        conversionService = SingletonFactory.getSingletonInstance(ConversionService.class);
        allBooksProcessor = SingletonFactory.getSingletonInstance(AllBooksProcessor.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Either<Exception, AllBooksOutputModel> process = allBooksProcessor.process(AllBooksInputModel.builder().build());
        if (process.isRight()) {
            booksData = process.get().getBooksData();
            ObservableList<BooksData> observableList = FXCollections.observableList(booksData);
            initializeColumns();
            tableView.getColumns().addAll(title, authors, shelfName, rowNum);
            tableView.getColumns().remove(0);
            tableView.setItems(observableList);
        }
    }

    private void initializeColumns() {
        title = new TableColumn<>("Заглавие");
        title.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getTitle()));
        title.setPrefWidth(200);
        shelfName = new TableColumn<>("Рафт");
        shelfName.setPrefWidth(154);
        shelfName.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getLocation().getShelf()));
        rowNum = new TableColumn<>("Ред");
        rowNum.setPrefWidth(100);
        rowNum.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getLocation().getRowNum()));
        authors = new TableColumn<>("Автор");
        authors.setPrefWidth(200);
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

    }



    @FXML
    public void doubleClick(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            setPath("/bg/tu_varna/sit/library/presentation.views/admin/book_data_view/pages/book-data-view.fxml");
            FXMLLoader loader = changeScene((Stage) tableView.getScene().getWindow());
            BookDataViewController controller = loader.getController();
            BooksData selectedItem = tableView.getSelectionModel().getSelectedItem();
            controller.setBooksData(conversionService.convert(selectedItem, CommonBooksProperties.class));
            controller.change();

        }
    }


}
