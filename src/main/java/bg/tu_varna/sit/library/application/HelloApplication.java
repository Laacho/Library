package bg.tu_varna.sit.library.application;

import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.converters.base.ConversionService;
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
        stage.setMaxHeight(640);
        stage.setMaxWidth(900);
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