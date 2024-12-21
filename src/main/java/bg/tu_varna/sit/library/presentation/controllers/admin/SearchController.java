package bg.tu_varna.sit.library.presentation.controllers.admin;

import bg.tu_varna.sit.library.core.search.SearchProcessor;
import bg.tu_varna.sit.library.models.search.SearchInputModel;
import bg.tu_varna.sit.library.models.search.SearchOperationModel;
import bg.tu_varna.sit.library.models.search.SearchOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class SearchController extends AdminController {

    private final SearchOperationModel searchProcessor;
    @FXML
    private TextField searchBar;

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


}
