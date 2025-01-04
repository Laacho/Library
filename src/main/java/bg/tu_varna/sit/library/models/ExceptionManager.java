package bg.tu_varna.sit.library.models;

import bg.tu_varna.sit.library.exceptions.BaseException;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import bg.tu_varna.sit.library.utils.annotations.Singleton;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.log4j.Logger;

@Singleton
public class ExceptionManager {
    private static final Logger log = Logger.getLogger(ExceptionManager.class);

    public Exception handle(Throwable e) {
        log.info("Started handling exception!");
        if (e instanceof BaseException) {
            BaseException exception = (BaseException) e;
            AlertManager.showAlert(Alert.AlertType.ERROR, exception.getHeader(), exception.getBaseMessage(), ButtonType.OK);
            log.info("Finished handling exception!");
            return exception;
        }
        AlertManager.showAlert(Alert.AlertType.ERROR,"System Error",e.getMessage(),ButtonType.OK);
        log.info("Finished handling exception!");
        return null;
    }
}
