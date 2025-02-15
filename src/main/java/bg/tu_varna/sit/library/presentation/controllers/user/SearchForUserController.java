package bg.tu_varna.sit.library.presentation.controllers.user;

import bg.tu_varna.sit.library.core.user.search.GetAllAuthorsProcessor;
import bg.tu_varna.sit.library.core.user.search.GetAllGenresProcessor;
import bg.tu_varna.sit.library.core.user.search.GetAllPublishersProcessor;
import bg.tu_varna.sit.library.core.user.search.SearchForUserProcessor;
import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.data.entities.Genre;
import bg.tu_varna.sit.library.data.entities.Publisher;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.get_all_authors.GetAllAuthorsInputModel;
import bg.tu_varna.sit.library.models.get_all_authors.GetAllAuthorsOperationModel;
import bg.tu_varna.sit.library.models.get_all_authors.GetAllAuthorsOutputModel;
import bg.tu_varna.sit.library.models.get_all_genres.GetAllGenresInputModel;
import bg.tu_varna.sit.library.models.get_all_genres.GetAllGenresOperationModel;
import bg.tu_varna.sit.library.models.get_all_genres.GetAllGenresOutputModel;
import bg.tu_varna.sit.library.models.get_all_publishers.GetAllPublishersInputModel;
import bg.tu_varna.sit.library.models.get_all_publishers.GetAllPublishersOperationModel;
import bg.tu_varna.sit.library.models.get_all_publishers.GetAllPublishersOutputModel;
import bg.tu_varna.sit.library.models.search_for_user.SearchForUserInputModel;
import bg.tu_varna.sit.library.models.search_for_user.SearchForUserOperationModel;
import bg.tu_varna.sit.library.models.search_for_user.SearchForUserOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.converters.base.ConversionService;
import io.vavr.control.Either;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static bg.tu_varna.sit.library.utils.ConstantsPaths.USER_BOOK_DATA_FOR_USER;

public class SearchForUserController extends UserController implements Initializable {
    @FXML
    private ComboBox<String> authorComboBox;
    @FXML
    private ComboBox<String> publisherComboBox;
    @FXML
    private ComboBox<String> genreComboBox;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private ListView<String> resultListView;
    private List<Book> result;
    private final ConversionService conversionService;

    private final GetAllPublishersOperationModel getAllPublishersOperationModel;
    private final GetAllGenresOperationModel getAllGenresOperationModel;
    private final GetAllAuthorsOperationModel getAllAuthorsOperationModel;
    private final SearchForUserOperationModel searchForUserOperationModel;

    public SearchForUserController() {
        this.searchForUserOperationModel = SingletonFactory.getSingletonInstance(SearchForUserProcessor.class);
        this.getAllPublishersOperationModel = SingletonFactory.getSingletonInstance(GetAllPublishersProcessor.class);
        this.getAllGenresOperationModel = SingletonFactory.getSingletonInstance(GetAllGenresProcessor.class);
        this.getAllAuthorsOperationModel = SingletonFactory.getSingletonInstance(GetAllAuthorsProcessor.class);
        conversionService = SingletonFactory.getSingletonInstance(ConversionService.class);
    }

    @FXML
    public void searchButton(ActionEvent event) {
        SearchForUserInputModel.SearchForUserInputModelBuilder builder = SearchForUserInputModel.builder().title(searchTextField.getText());
        if (!authorComboBox.getValue().equals("None")) {
            builder.filterAuthor(authorComboBox.getValue());
        }
        if (!publisherComboBox.getValue().equals("None")) {
            builder.filterPublisher(publisherComboBox.getValue());
        }
        if (!genreComboBox.getValue().equals("None")) {
            builder.filterGenre(genreComboBox.getValue());
        }
        SearchForUserInputModel input = builder.build();
        Either<Exception, SearchForUserOutputModel> process = searchForUserOperationModel.process(input);
        if (process.isRight()) {
            resultListView.setVisible(true);
            result = process.get().getResult();
            List<String> formattedResult = new ArrayList<>();
            for (Book book : result) {
                String sb = formatBook(book);
                formattedResult.add(sb);
            }
            if (formattedResult.isEmpty()) {
                resultListView.setVisible(true);
                resultListView.setItems(FXCollections.observableList(List.of("No books found")));
            } else {
                resultListView.setPrefHeight(formattedResult.size() * 100);
                resultListView.setItems(FXCollections.observableArrayList(formattedResult));
            }
        }
    }
    private String formatBook(Book book) {
        return book.getTitle() +
                " by " +
                book.getAuthors().stream().map(Author::toString).collect(Collectors.joining(", ")) +
                " - " +
                book.getGenre().getName() +
                ", " +
                book.getPublisher().getName();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadGenres();
        loadPublishers();
        loadAuthors();
        formatComboBox();
        disableFocusOnButtons();
    }
    public void formatComboBox(){
        genreComboBox.getItems().add("None");
        publisherComboBox.getItems().add("None");
        authorComboBox.getItems().add("None");
        genreComboBox.setValue("None");
        publisherComboBox.setValue("None");
        authorComboBox.setValue("None");
    }

    private void loadGenres() {
        GetAllGenresInputModel input = GetAllGenresInputModel.builder().build();
        Either<Exception, GetAllGenresOutputModel> process = getAllGenresOperationModel.process(input);
        if (process.isRight()) {
            List<Genre> genres = process.get().getGenres();
            genres
                    .stream()
                    .map(Genre::getName)
                    .forEach(genreComboBox.getItems()::add);

        }
    }

    private void loadPublishers() {
        GetAllPublishersInputModel input = GetAllPublishersInputModel.builder().build();
        Either<Exception, GetAllPublishersOutputModel> process = getAllPublishersOperationModel.process(input);
        if (process.isRight()) {
            List<Publisher> publishers = process.get().getPublishers();
            publishers
                    .stream()
                    .map(Publisher::getName)
                    .forEach(publisherComboBox.getItems()::add);
        }
    }

    private void loadAuthors() {
        GetAllAuthorsInputModel input = GetAllAuthorsInputModel.builder().build();
        Either<Exception, GetAllAuthorsOutputModel> process = getAllAuthorsOperationModel.process(input);
        if (process.isRight()) {
            List<Author> authors = process.get().getAuthors();
            authors
                    .stream()
                    .map(Author::toString)
                    .forEach(authorComboBox.getItems()::add);
        }
    }

    @FXML
    public void searchWithEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            searchButton.fire();
        }
    }

    public void changeToBookView(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            //setPath("/bg/tu_varna/sit/library/presentation.views/user/book_data_for_user/pages/book-data-for-user-view.fxml");
            setPath(USER_BOOK_DATA_FOR_USER);
            int selectedIndex = resultListView.getSelectionModel().getSelectedIndex();
            Book book = result.get(selectedIndex);
            BookDataController.booksData= conversionService.convert(book, CommonBooksProperties.class);
            FXMLLoader loader = changeScene((Stage) resultListView.getScene().getWindow());
            BookDataController controller = loader.getController();
            controller.change();
        }
    }
}
