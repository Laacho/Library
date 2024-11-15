package bg.tu_varna.sit.library.application;

import bg.tu_varna.sit.library.common.SingletonFactory;
import bg.tu_varna.sit.library.common.converters.base.ConversionService;
import bg.tu_varna.sit.library.common.converters.register.FromRegisterInputModelToUser;
import bg.tu_varna.sit.library.common.converters.register.FromRegisterInputModelToUserCredentials;
import bg.tu_varna.sit.library.common.converters.register.FromStringToRegisterOutputModel;
import bg.tu_varna.sit.library.data.access.Connection;
import bg.tu_varna.sit.library.data.entities.User;
import bg.tu_varna.sit.library.data.entities.UserCredentials;
import bg.tu_varna.sit.library.data.repositories.implementations.*;
import bg.tu_varna.sit.library.models.register.RegisterInputModel;
import bg.tu_varna.sit.library.models.register.RegisterOutputModel;
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
        stage.setMaxWidth(800);
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
        ConversionService singletonInstance = SingletonFactory.getSingletonInstance(ConversionService.class);
        singletonInstance.addConverter(RegisterInputModel.class,User.class,new FromRegisterInputModelToUser());
        singletonInstance.addConverter(RegisterInputModel.class, UserCredentials.class,new FromRegisterInputModelToUserCredentials());
        singletonInstance.addConverter(String.class, RegisterOutputModel.class,new FromStringToRegisterOutputModel());
    }
}