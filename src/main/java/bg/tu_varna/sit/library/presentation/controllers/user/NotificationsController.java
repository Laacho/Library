package bg.tu_varna.sit.library.presentation.controllers.user;

import bg.tu_varna.sit.library.core.user.notifications.FindAllNotificationsForUserProcessor;
import bg.tu_varna.sit.library.core.user.notifications.UpdateNotificationsToReadProcessor;
import bg.tu_varna.sit.library.models.find_all_notifications_for_user.FindAllNotificationsForUserInputModel;
import bg.tu_varna.sit.library.models.find_all_notifications_for_user.FindAllNotificationsForUserOperationModel;
import bg.tu_varna.sit.library.models.find_all_notifications_for_user.FindAllNotificationsForUserOutputModel;
import bg.tu_varna.sit.library.models.remove_notifications.UpdateNotificationsToReadInputModel;
import bg.tu_varna.sit.library.models.remove_notifications.UpdateNotificationsToReadOperationModel;
import bg.tu_varna.sit.library.models.remove_notifications.UpdateNotificationsToReadOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.*;

public class NotificationsController extends UserController implements Initializable {

    @FXML
    private GridPane gridPane;
    private final FindAllNotificationsForUserOperationModel findAllNotificationsForUserOperationModel;
    private final UpdateNotificationsToReadOperationModel updateNotificationsToReadOperationModel;

    public NotificationsController() {
        this.findAllNotificationsForUserOperationModel = SingletonFactory.getSingletonInstance(FindAllNotificationsForUserProcessor.class);
        this.updateNotificationsToReadOperationModel = SingletonFactory.getSingletonInstance(UpdateNotificationsToReadProcessor.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FindAllNotificationsForUserInputModel input = FindAllNotificationsForUserInputModel.builder().build();
        Either<Exception, FindAllNotificationsForUserOutputModel> process = findAllNotificationsForUserOperationModel.process(input);
        if (process.isRight()) {
            List<String> notifications = process.get().getNotifications();
            fillGridPane(notifications);
        } else {
            gridPane.add(new Label("No notifications"), 0, 0);
        }
    }

    private void fillGridPane(List<String> notifications) {
        int red = 0;
        gridPane.getColumnConstraints().get(0).setPercentWidth(80);
        for (String notification : notifications) {
            Label label = new Label(notification);
            Pane pane = new Pane(label);
            gridPane.add(pane, 0, red);
            CheckBox checkBox = new CheckBox();
            StackPane box = new StackPane();
            checkBox.setGraphic(box);
            gridPane.add(checkBox, 1, red);
            GridPane.setHalignment(checkBox, HPos.RIGHT);
            red++;
        }
    }

    @FXML
    public void clearNotifications(ActionEvent event) {
        removeCheckedRows();
    }

    public void removeCheckedRows() {
        List<String> messagesToRemove = getCheckedRowMessages();
        removeCheckedRowsFromGrid();
        removeNotificationsFromDB(messagesToRemove);
    }

    private List<String> getCheckedRowMessages() {
        List<String> messages = new ArrayList<>();
        Set<Integer> rowsToCheck = getCheckedRows();

        for (Integer rowIndex : rowsToCheck) {
            for (Node node : gridPane.getChildren()) {
                if (isInRow(node, rowIndex) && node instanceof Label) {
                    messages.add(getNodeText(node));
                }
            }
        }
        return messages;
    }

    private Set<Integer> getCheckedRows() {
        Set<Integer> rows = new HashSet<>();
        for (Node node : gridPane.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) {
                    rows.add(getRowIndex(node));
                }
            }
        }
        return rows;
    }

    private void removeCheckedRowsFromGrid() {
        Set<Integer> rowsToRemove = getCheckedRows();

        for (Integer rowIndex : rowsToRemove) {
            gridPane.getChildren().removeIf(child -> isInRow(child, rowIndex));
        }

        reassignRowIndices();
    }

    private void removeNotificationsFromDB(List<String> messages) {
        UpdateNotificationsToReadInputModel input = UpdateNotificationsToReadInputModel.builder().notifications(messages).build();
        Either<Exception, UpdateNotificationsToReadOutputModel> process = updateNotificationsToReadOperationModel.process(input);
        if (process.isRight()) {
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Success",process.get().getMessage(), ButtonType.OK);
        }
    }

    private boolean isInRow(Node node, Integer rowIndex) {
        return getRowIndex(node).equals(rowIndex);
    }

    private Integer getRowIndex(Node node) {
        Integer rowIndex = GridPane.getRowIndex(node);
        return (rowIndex != null) ? rowIndex : 0;
    }

    private String getNodeText(Node node) {
        if (node instanceof Label) {
            return ((Label) node).getText();
        }
        return "";
    }

    private void reassignRowIndices() {
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = getRowIndex(node);
            GridPane.setRowIndex(node, rowIndex);
        }
    }


}


