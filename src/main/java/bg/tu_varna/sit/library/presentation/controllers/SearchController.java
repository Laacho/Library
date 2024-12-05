package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.core.client.SearchProcessor;
import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.search.SearchInputModel;
import bg.tu_varna.sit.library.models.search.SearchOperationModel;
import bg.tu_varna.sit.library.models.search.SearchOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;


public class SearchController extends Controller {

    private final SearchOperationModel searchProcessor;
    @FXML
    private TextField searchBar;
    @FXML
    private Button backButton;
    @FXML
    private ListView<String> listView;

    public SearchController() {
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
                listView.setPrefHeight(process.get().getBooks().size()*24);
                process.get().getBooks().forEach(book -> listView.getItems().add(book.getTitle()));
            }
        }
        else {
            listView.setVisible(false);
            listView.getItems().clear();
        }
    }

    @FXML
    public void addBook(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/addBook/pages/addBook-view.fxml");
        changeScene(actionEvent);
    }
    @FXML
    public void search(ActionEvent actionEvent) throws IOException {
        setPath("/bg/tu_varna/sit/library/presentation.views/search/pages/search-view.fxml");
        changeScene(actionEvent);
    }
}
