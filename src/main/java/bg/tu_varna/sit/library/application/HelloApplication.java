package bg.tu_varna.sit.library.application;

import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.*;
import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.addBook.AddBookInputModel;
import bg.tu_varna.sit.library.models.addGenre.CheckGenreInputModel;
import bg.tu_varna.sit.library.models.all_books.BooksData;
import bg.tu_varna.sit.library.models.login.LoginOutputModel;
import bg.tu_varna.sit.library.models.register.RegisterInputModel;
import bg.tu_varna.sit.library.models.register.RegisterOutputModel;
import bg.tu_varna.sit.library.models.users_table_view.UsersData;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.converters.addBook.FromAddBookInputToBook;
import bg.tu_varna.sit.library.utils.converters.addGenre.FromAddGenreInputToGenre;
import bg.tu_varna.sit.library.utils.converters.base.ConversionService;
import bg.tu_varna.sit.library.utils.converters.books.FromBooksToBooksData;
import bg.tu_varna.sit.library.utils.converters.books.FromDiscardedBooksToBooksData;
import bg.tu_varna.sit.library.utils.converters.booksData.FromAllBooksDataToCommonBooksProperties;
import bg.tu_varna.sit.library.utils.converters.booksData.FromArchiveBooksDataToCommonBooksProperties;
import bg.tu_varna.sit.library.utils.converters.booksData.FromDiscardedBooksDataToCommonBooksProperties;
import bg.tu_varna.sit.library.utils.converters.login.FromUserSessionToLoginOutputModel;
import bg.tu_varna.sit.library.utils.converters.register.FromRegisterInputModelToUser;
import bg.tu_varna.sit.library.utils.converters.register.FromRegisterInputModelToUserCredentials;
import bg.tu_varna.sit.library.utils.converters.register.FromStringToRegisterOutputModel;
import bg.tu_varna.sit.library.utils.converters.user_session.FromUserCredentialsToUserSession;
import bg.tu_varna.sit.library.utils.converters.users.FromUserCredentialsToUsersData;
import bg.tu_varna.sit.library.utils.session.UserSession;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class
                .getResource("/bg/tu_varna/sit/library/presentation.views/hello_view/pages/hello-view.fxml"));
        stage.setResizable(false);
        stage.setMaxHeight(600);
        stage.setMaxWidth(1000);
        Scene scene = new Scene(fxmlLoader.load(), stage.getMaxWidth(), stage.getMaxHeight());
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        ConfigurationDatabase.config();
        Connection.openSession();
        launch();
    }

    @Override
    public void init() throws Exception {
        super.init();
        SingletonFactory.init();
        ConversionService conversionService = SingletonFactory.getSingletonInstance(ConversionService.class);
        conversionService.init();
    }
}