package se.myhappyplants.client.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import se.myhappyplants.client.controller.MyPlantsTabController;
import se.myhappyplants.client.model.MessageText;

public class ExecutionMessage {

    public static void updateLstViewExecuationMessage(MyPlantsTabController myPlantsTabController, MessageText messageText) {

        ListView lstViewMessages;
        lstViewMessages = myPlantsTabController.getLstViewMessages();
        ObservableList messageToMessageBox = FXCollections.observableArrayList();
        messageToMessageBox.add(messageText);
        lstViewMessages.setItems(messageToMessageBox);
    }
}
