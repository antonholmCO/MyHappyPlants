package se.myhappyplants.client.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import se.myhappyplants.client.model.BoxTitle;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.model.RootName;
import se.myhappyplants.client.model.Verifier;
import se.myhappyplants.client.service.ClientConnection;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.MessageType;
import se.myhappyplants.shared.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RegisterPaneController {
    @FXML public TextField txtFldNewEmail;
    @FXML public TextField txtFldNewEmail1;
    @FXML public TextField txtFldNewUsername;
    @FXML public PasswordField passFldNewPassword;
    @FXML public PasswordField passFldNewPassword1;
    @FXML public Label goBackIcon;

    private Verifier verifier;

    @FXML
    public void initialize() {
        verifier = new Verifier();
    }

    @FXML
    private void registerButtonPressed() {
        boolean verifiedRegistration = verifier.validateRegistration(this);
        Thread registerThread = new Thread(() -> {
            if (!verifiedRegistration) {
                return;
            }
            Message registerRequest = new Message(MessageType.register, new User(txtFldNewEmail.getText(), txtFldNewUsername.getText(), passFldNewPassword.getText(), true));
            ClientConnection connection = new ClientConnection();
            Message registerResponse = connection.makeRequest(registerRequest);

            if (registerResponse != null) {
                if (registerResponse.isSuccess()) {
                    LoggedInUser.getInstance().setUser(registerResponse.getUser());
                    Platform.runLater(() -> MessageBox.display(BoxTitle.Success, "Account created successfully! Now logged in as " + LoggedInUser.getInstance().getUser().getUsername()));
                    try {
                        switchToMainPane();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Platform.runLater(() -> MessageBox.display(BoxTitle.Failed, "An account with this email address already exists here at My Happy Plants."));
                }
            } else {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Failed, "The connection to the server has failed. Check your connection and try again."));
            }
        });
        registerThread.start();
    }

    @FXML
    private void switchToMainPane() throws IOException {
        StartClient.setRoot(String.valueOf(RootName.mainPane));
    }

    public void swapToLogin(MouseEvent mouseEvent) {
        try {
            StartClient.setRoot("loginPane");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String[] getComponentsToVerify() {
        String[] loginInfoToCompare = new String[5];
        loginInfoToCompare[0] = txtFldNewEmail.getText();
        loginInfoToCompare[1] = txtFldNewEmail1.getText();
        loginInfoToCompare[2] = txtFldNewUsername.getText();
        loginInfoToCompare[3] = passFldNewPassword.getText();
        loginInfoToCompare[4] = passFldNewPassword1.getText();
        return loginInfoToCompare;
    }
}
