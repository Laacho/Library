package bg.tu_varna.sit.library.presentation.controllers;

import bg.tu_varna.sit.library.core.notification_view.AdminNotificationViewProcessor;
import bg.tu_varna.sit.library.models.notifications_view.AdminNotificationViewInputModel;
import bg.tu_varna.sit.library.models.notifications_view.AdminNotificationViewOperationModel;
import bg.tu_varna.sit.library.models.notifications_view.AdminNotificationViewOutputModel;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminNotificationViewController extends Controller implements Initializable {
    private final AdminNotificationViewOperationModel adminNotificationViewProcessor;
    @FXML
    private ListView<String> listView;

    public AdminNotificationViewController() {
        adminNotificationViewProcessor = SingletonFactory.getSingletonInstance(AdminNotificationViewProcessor.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Either<Exception, AdminNotificationViewOutputModel> process = adminNotificationViewProcessor.process(AdminNotificationViewInputModel.builder().build());
        if(process.isRight()){
            AdminNotificationViewOutputModel output = process.get();
            listView.getItems().addAll(output.getMessages());
        }else{
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Empty","You don't have notifications.", ButtonType.OK);
        }
    }
}
