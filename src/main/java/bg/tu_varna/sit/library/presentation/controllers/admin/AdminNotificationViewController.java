package bg.tu_varna.sit.library.presentation.controllers.admin;

import bg.tu_varna.sit.library.core.notification_view.AdminNotificationViewProcessor;
import bg.tu_varna.sit.library.core.update_notifications_for_admin.UpdateNotificationsForAdminProcessor;
import bg.tu_varna.sit.library.models.notifications_view.AdminNotificationViewInputModel;
import bg.tu_varna.sit.library.models.notifications_view.AdminNotificationViewOperationModel;
import bg.tu_varna.sit.library.models.notifications_view.AdminNotificationViewOutputModel;
import bg.tu_varna.sit.library.models.removeNotifications.UpdateNotificationsToReadInputModel;
import bg.tu_varna.sit.library.models.removeNotifications.UpdateNotificationsToReadOutputModel;
import bg.tu_varna.sit.library.models.update_notifications_for_admin.UpdateNotificationsForAdminInputModel;
import bg.tu_varna.sit.library.models.update_notifications_for_admin.UpdateNotificationsForAdminOperationModel;
import bg.tu_varna.sit.library.models.update_notifications_for_admin.UpdateNotificationsForAdminOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.AdminController;
import bg.tu_varna.sit.library.presentation.controllers.base.Controller;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.*;

public class AdminNotificationViewController extends AdminController implements Initializable {
    @FXML
    private Button clearButton;
    @FXML
    private GridPane gridPane;
    private final AdminNotificationViewOperationModel adminNotificationViewProcessor;
    private final UpdateNotificationsForAdminOperationModel updateNotificationsForAdminOperationModel;
    public AdminNotificationViewController() {
        adminNotificationViewProcessor = SingletonFactory.getSingletonInstance(AdminNotificationViewProcessor.class);
        this.updateNotificationsForAdminOperationModel = SingletonFactory.getSingletonInstance(UpdateNotificationsForAdminProcessor.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Either<Exception, AdminNotificationViewOutputModel> process = adminNotificationViewProcessor.process(AdminNotificationViewInputModel.builder().build());
        if(process.isRight()  ) {

            List<String> notifications = process.get().getMessages();
            if (notifications.isEmpty()) {
                gridPane.add(new Label("No notifications"), 0, 0);
                clearButton.setDisable(true);
            } else {
                fillGridPane(notifications);
                clearButton.setDisable(false);
            }
        }
    }
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
        UpdateNotificationsForAdminInputModel input = UpdateNotificationsForAdminInputModel.builder().messages(messages).build();
        Either<Exception, UpdateNotificationsForAdminOutputModel> process = updateNotificationsForAdminOperationModel.process(input);
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

    private void fillGridPane(List<String> notifications) {
        int red = 0;
        for (String notification : notifications) {
            Label label = new Label(notification);
            gridPane.add(label, 0, red);
            gridPane.add(new CheckBox(), 1, red);
            red++;
        }
    }

}
