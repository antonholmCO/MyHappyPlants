package se.myhappyplants.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.view.MessageBox;

import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.User;

import java.io.IOException;

public class SettingsTabController {

    @FXML private MainPaneController mainPaneController;

    @FXML private Label lblUsernameSettings;
    @FXML private PasswordField deleteAccountPassField;

    @FXML public void initialize() {

        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        lblUsernameSettings.setText(loggedInUser.getUser().getUsername());
        //userAvatar.setImage(new Image(loggedInUser.getUser().getAvatarURL()));
    }

    public void setMainController(MainPaneController mainPaneController) {
        this.mainPaneController = mainPaneController;
    }

    /**
     * author: Frida Jacobsson
     */
    @FXML private void deleteAccountButtonPressed() {
        int answer = MessageBox.askYesNo("Delete account", "Are you sure you want to delete your account? \n All your personal information will be deleted. \nA deleted account can't be restored. ");
        if (answer == 1) {
            Thread deleteAccountThread = new Thread(() -> {
            Message deleteMessage = new Message("delete account", new User(LoggedInUser.getInstance().getUser().getEmail(), deleteAccountPassField.getText()));
            ClientConnection connection = new ClientConnection();
            Message deleteResponse = connection.makeRequest(deleteMessage);
            if (deleteResponse != null) {
                if (deleteResponse.isSuccess()) {
                    MessageBox.display("Account deleted successfully", "Sorry to see you go");
                    try {
                        logoutButtonPressed();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    MessageBox.display("Failed to delete account", "Invalid password");
                }
            } else {
                MessageBox.display("Failed to delete account", "No contact with server");
            }
            });
            deleteAccountThread.start();
        }
    }

    @FXML
    private void logoutButtonPressed() throws IOException {

        mainPaneController.logoutButtonPressed();

    }
}
