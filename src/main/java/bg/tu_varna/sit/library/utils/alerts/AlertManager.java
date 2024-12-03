package bg.tu_varna.sit.library.utils.alerts;

import bg.tu_varna.sit.library.utils.annotations.Singleton;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

@Singleton
public final class AlertManager {
    private static final Logger log = Logger.getLogger(AlertManager.class);

    private AlertManager(){}

    public static Alert showAlert(Alert.AlertType type, String header, String message, ButtonType... buttons) {
        log.info("Started creating alert");
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setTitle(header);
        alert.setContentText(message);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getButtonTypes().setAll(buttons);
        alert.showAndWait();
        log.info("Finished creating alert");
        return alert;
    }
}
