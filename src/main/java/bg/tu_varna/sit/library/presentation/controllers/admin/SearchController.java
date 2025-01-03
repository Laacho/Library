package bg.tu_varna.sit.library.presentation.controllers.admin;

import bg.tu_varna.sit.library.core.search.SearchProcessor;
import bg.tu_varna.sit.library.data.entities.Author;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.search.SearchInputModel;
import bg.tu_varna.sit.library.models.search.SearchOperationModel;
import bg.tu_varna.sit.library.models.search.SearchOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import bg.tu_varna.sit.library.utils.converters.base.ConversionService;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;


public class SearchController extends AdminController implements Initializable {

    private final SearchOperationModel searchProcessor;
    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> listView;
    private List<Book> foundBooks;
    private final ConversionService conversionService;
    public SearchController( ) {
        this.conversionService = SingletonFactory.getSingletonInstance(ConversionService.class);
        this.searchProcessor = SingletonFactory.getSingletonInstance(SearchProcessor.class);
    }

    @FXML
    public void onKeyPressed(KeyEvent event) {
        if(!searchBar.getText().isBlank() ) {
            SearchInputModel input = SearchInputModel.builder()
                    .text(searchBar.getText())
                    .build();
            Either<Exception, SearchOutputModel> process = searchProcessor.process(input);
            if (process.isRight()) {
                listView.getItems().clear();
                listView.setVisible(true);
                listView.setPrefHeight(process.get().getBooks().size()*35);
                process.get().getBooks().forEach(book -> listView.getItems().add(book.getTitle()+" by "+
                            book.getAuthors().stream().map(Author::toString).collect(Collectors.joining(", "))                  ));
                foundBooks = process.get().getBooks();
            }
        }
        else {
            listView.setVisible(false);
            listView.getItems().clear();
        }
    }

    @FXML
    public void changeToBookView(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount()==2){
             setPath("/bg/tu_varna/sit/library/presentation.views/admin/book_data_view/pages/book-data-view.fxml");
            FXMLLoader loader = changeScene((Stage) listView.getScene().getWindow());
            BookDataViewController controller = loader.getController();
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            Book foundBook=findBook(selectedItem);
            if(foundBook==null){
                AlertManager.showAlert(Alert.AlertType.ERROR, "Error", "Book not found", ButtonType.OK);
                return;
            }
            controller.setBooksData(conversionService.convert(foundBook,CommonBooksProperties.class));
            controller.change();
        }
    }

    private Book findBook(String selectedItem) {
        //selectedItem=Test by ivan petrov, Kaloqn Grigorov
        String[] split = selectedItem.split("by");
        String title = split[0].trim();
        String[] authors = split[1].split(", ");
        for (Book foundBook : foundBooks) {
            String currentTitle = foundBook.getTitle().trim();
            Set<Author> currentAuthors = foundBook.getAuthors();
            if(currentTitle.equals(title)) {
                if(checkAuthors(currentAuthors,authors)){
                    return foundBook;
                }
            }
        }
        return null;
    }

    private boolean checkAuthors(Set<Author> currentAuthors, String[] authors) {
        if( currentAuthors.size() != authors.length){
            return false;
        }
        Set<String> authorNamesFromSet = new HashSet<>();
        for (Author author : currentAuthors) {
            authorNamesFromSet.add(author.toString());
        }
        Set<String> authorNamesFromArray = new HashSet<>();
        for (String author : authors) {
            authorNamesFromArray.add(author.trim());
        }
        return authorNamesFromSet.containsAll(authorNamesFromArray);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disableFocusOnButtons();
    }
}
