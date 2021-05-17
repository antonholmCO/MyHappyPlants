package se.myhappyplants.client.controller;

import java.io.*;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import se.myhappyplants.client.model.BoxTitle;
import se.myhappyplants.client.model.RootName;
import se.myhappyplants.client.service.ClientConnection;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.model.Verifier;
import se.myhappyplants.client.view.PopupBox;
import se.myhappyplants.shared.Message;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.shared.MessageType;
import se.myhappyplants.shared.User;

/**
 * Controls the inputs from a user that hasn't logged in
 * Created by: Eric Simonsson, Christopher O'Driscoll
 * Updated by: Linn Borgström, 2021-05-13
 */
public class LoginPaneController {

    @FXML
    private TextField txtFldEmail;
    @FXML
    private PasswordField passFldPassword;
    @FXML
    private TextField txtFldNewEmail;
    @FXML
    private TextField txtFldNewEmail1;
    @FXML
    private TextField txtFldNewUsername;
    @FXML
    private PasswordField passFldNewPassword;
    @FXML
    private PasswordField passFldNewPassword1;

    private Verifier verifier;


    /**
     * Switches to 'logged in' scene
     *
     * @throws IOException
     */
    @FXML
    public void initialize() throws IOException {
        String lastLoggedInUser;
        verifier = new Verifier();

        File file = new File("resources/lastLogin.txt");
        if(!file.exists()) {
            file.createNewFile();
        }
        else if(file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader("resources/lastLogin.txt"));) {
                lastLoggedInUser = br.readLine();
                txtFldEmail.setText(lastLoggedInUser);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToMainPane() throws IOException {
        StartClient.setRoot(String.valueOf(RootName.mainPane));
    }

    /**
     * Tries to log in user.
     * If successful, changes scene
     *
     * @throws IOException
     */
    @FXML
    private void loginButtonPressed() {
        Thread loginThread = new Thread(() -> {
            Message loginMessage = new Message(MessageType.login, new User(txtFldEmail.getText(), passFldPassword.getText()));
            ClientConnection connection = new ClientConnection();
            Message loginResponse = connection.makeRequest(loginMessage);

            if (loginResponse != null) {
                if (loginResponse.isSuccess()) {
                    LoggedInUser.getInstance().setUser(loginResponse.getUser());
                    Platform.runLater(() -> PopupBox.display("Now logged in as\n" + LoggedInUser.getInstance().getUser().getUsername()));
                    try {
                        switchToMainPane();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Platform.runLater(() -> MessageBox.display(BoxTitle.Failed, "Sorry, we couldn't find an account with that email or you typed the password wrong. Try again or create a new account."));

                }
            } else {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Failed, "The connection to the server has failed. Check your connection and try again."));
            }
        });
        loginThread.start();
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


}
