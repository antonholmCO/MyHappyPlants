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
 * Updated by: Christopher, 2021-04-13
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
    private MessageType messageType;
    private BoxTitle boxTitle;

    /**
     * Switches to 'logged in' scene
     *
     * @throws IOException
     */
    @FXML
    public void initialize() {
        String lastLoggedInUser;
        verifier = new Verifier();
        try (BufferedReader br = new BufferedReader(new FileReader("resources/lastLogin.txt"));) {
            lastLoggedInUser = br.readLine();
            txtFldEmail.setText(lastLoggedInUser);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToMainPane() throws IOException {
        StartClient.setRoot("mainPane");
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
            Message loginMessage = new Message(messageType.login, new User(txtFldEmail.getText(), passFldPassword.getText()));
            ClientConnection connection = new ClientConnection();
            Message loginResponse = connection.makeRequest(loginMessage);

            if (loginResponse != null) {
                if (loginResponse.isSuccess()) {
                    LoggedInUser.getInstance().setUser(loginResponse.getUser());
                    PopupBox popupBox = new PopupBox();
                    Platform.runLater(() -> popupBox.display("Now logged in as\n" + LoggedInUser.getInstance().getUser().getUsername()));
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
        Verifier verifier = new Verifier();
        boolean verifiedRegistartion = verifier.validateRegistration(this);
        Thread registerThread = new Thread(() -> {
            if (!verifiedRegistartion) {
                return;
            }
            Message registerRequest = new Message(messageType.register, new User(txtFldNewEmail.getText(), txtFldNewUsername.getText(), passFldNewPassword.getText(), true));
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

    //TODO: Ta upp med gruppen om flyttad logik till klassen Verifier. Denna ger nullvärden på de varibaler som är kopplade till FXML.
    /**
     * @return
     */
    /*private boolean validateRegistration() {
        String email = txtFldNewEmail.getText();
        if (!validateEmail(email)) {
            Platform.runLater(() -> MessageBox.display("Couldn’t create account", "Please enter your email address in format: yourname@example.com"));
            return false;
        }
        if (txtFldNewUsername.getText().isEmpty()) {
            Platform.runLater(() -> MessageBox.display("Couldn’t create account", "Please enter a username"));
            return false;
        }
        if (passFldNewPassword.getText().isEmpty()) {
            Platform.runLater(() -> MessageBox.display("Couldn’t create account", "Please enter a password"));
            return false;
        }
        if (!txtFldNewEmail1.getText().equals(txtFldNewEmail.getText())) {
            Platform.runLater(() -> MessageBox.display("Couldn’t create account", "Please enter the same email twice"));
            return false;
        }
        if (!passFldNewPassword1.getText().equals(passFldNewPassword.getText())) {
            Platform.runLater(() -> MessageBox.display("Couldn’t create account", "Please enter the same password twice"));
            return false;
        }
        return true;
    }*/

    /**
     * Method for validating an email by checking that it contains @
     *
     * @param email input email from user in application
     * @return true if the email contains @, false if it is not valid
     */
    /*private boolean validateEmail(String email) {
        final String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }*/
}
