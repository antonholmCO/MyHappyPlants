package se.myhappyplants.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.User;

import java.io.IOException;

public class SettingsTabController {

    @FXML private BorderPane settingsPane;
    @FXML private SecondaryController secondaryController;
    @FXML private TabPane secondaryPane;

    @FXML private Label lblUsernameSettings;
    @FXML private PasswordField deleteAccountPassField;


    @FXML public void initialize() {

        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        lblUsernameSettings.setText(loggedInUser.getUser().getUsername());
        //userAvatar.setImage(new Image(loggedInUser.getUser().getAvatarURL()));
    }

    public void setSecondaryController (SecondaryController secondaryController) {
        this.secondaryController = secondaryController;
    }

    /**
     * author: Frida Jacobsson
     * @throws IOException
     */
    @FXML private void deleteAccountButtonPressed() throws IOException {
        int answer = MessageBox.askYesNo("Delete account", "Are you sure you want to delete your account? \n All your personal information will be deleted. \nA deleted account can't be restored. ");
        if (answer == 1) {
            Message deleteMessage = new Message("delete account", new User(LoggedInUser.getInstance().getUser().getEmail(), deleteAccountPassField.getText()));
            Message deleteResponse = ClientConnection.getInstance().makeRequest(deleteMessage);
            if (deleteResponse != null) {
                if (deleteResponse.isSuccess()) {
                    MessageBox.display("Account deleted successfully", "Sorry to see you go");
                    logoutButtonPressed();
                } else {
                    MessageBox.display("Failed to delete account", "Invalid password");
                }
            } else {
                MessageBox.display("Failed to delete account", "No contact with server");
            }
        }
    }

    @FXML
    private void logoutButtonPressed() throws IOException {

        secondaryController.logoutButtonPressed();

    }
}
