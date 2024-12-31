package bg.tu_varna.sit.library.presentation.controllers.user;

import bg.tu_varna.sit.library.core.find_all_notifications_for_user.FindAllNotificationsForUserProcessor;
import bg.tu_varna.sit.library.core.update_notifications_to_read.UpdateNotificationsToReadProcessor;
import bg.tu_varna.sit.library.models.find_all_notifications_for_user.FindAllNotificationsForUserInputModel;
import bg.tu_varna.sit.library.models.find_all_notifications_for_user.FindAllNotificationsForUserOperationModel;
import bg.tu_varna.sit.library.models.find_all_notifications_for_user.FindAllNotificationsForUserOutputModel;
import bg.tu_varna.sit.library.models.removeNotifications.UpdateNotificationsToReadInputModel;
import bg.tu_varna.sit.library.models.removeNotifications.UpdateNotificationsToReadOperationModel;
import bg.tu_varna.sit.library.models.removeNotifications.UpdateNotificationsToReadOutputModel;
import bg.tu_varna.sit.library.presentation.controllers.base.UserController;
import bg.tu_varna.sit.library.utils.SingletonFactory;
import bg.tu_varna.sit.library.utils.alerts.AlertManager;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

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
        for (String notification : notifications) {
            Label label = new Label(notification);
            gridPane.add(label, 0, red);
            gridPane.add(new CheckBox(), 1, red);
            red++;
        }
    }

    @FXML
    public void clearNotifications(ActionEvent event) {
        removeCheckedRows();
    }

//    public void removeCheckedRows() {
//        List<String> messagesToRemove = new ArrayList<>();
//        Set<Integer> rowsToRemove = new HashSet<>();
//        for (Node node : gridPane.getChildren()) {
//            if (node instanceof CheckBox) {
//                CheckBox checkBox = (CheckBox) node;
//                if (checkBox.isSelected()) {
//
//                    Integer rowIndex = GridPane.getRowIndex(checkBox);
//                    if (rowIndex == null) rowIndex = 0;
//                    rowsToRemove.add(rowIndex);
//
//                    for (Node sibling : gridPane.getChildren()) {
//                        if (!(sibling instanceof CheckBox)) {
//                            Integer siblingRowIndex = GridPane.getRowIndex(sibling);
//                            if (siblingRowIndex == null) siblingRowIndex = 0;
//                            if (siblingRowIndex.equals(rowIndex)) {
//                                if (sibling instanceof Label) {
//                                    messagesToRemove.add(((Label) sibling).getText());
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        for (String message : messagesToRemove) {
//            System.out.println("Removing message: " + message);
//        }
//        for (Integer rowIndex : rowsToRemove) {

    /// /            VBox vBox = (VBox) gridPane.getChildren().stream()
    /// /                    .filter(node -> GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == 1 && Objects.equals(GridPane.getRowIndex(node), rowIndex))
    /// /                    .findFirst()
    /// /                    .orElse(null);
    /// /            if(vBox != null) {
    /// /                Label first = (Label) vBox.getChildren().getFirst();
    /// /                String text = first.getText();
    /// /                System.out.println("Id to remove: "+text);
    /// /            }
//            gridPane.getChildren().removeIf(child -> {
//                Integer childRowIndex = GridPane.getRowIndex(child);
//                if (childRowIndex == null) childRowIndex = 0;
//                return childRowIndex.equals(rowIndex);
//            });
//        }
//
//        for (Node node : gridPane.getChildren()) {
//            Integer rowIndex = GridPane.getRowIndex(node);
//            if (rowIndex == null) rowIndex = 0;
//            GridPane.setRowIndex(node, rowIndex);
//        }
//    }
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


