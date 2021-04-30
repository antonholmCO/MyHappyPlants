package se.myhappyplants.client.controller;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.view.SearchPlantPane;
import se.myhappyplants.shared.APIPlant;
import se.myhappyplants.shared.Message;
import se.myhappyplants.client.view.MessageBox;
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
    private TextField txtFldNewUsername;
    @FXML
    private PasswordField passFldNewPassword;

    /**
     * Switches to 'logged in' scene
     *
     * @throws IOException
     */
    @FXML
    public void initialize() {
        String lastLoggedInUser;

        try (BufferedReader br = new BufferedReader(new FileReader("resources/lastLogin.txt"));) {
            lastLoggedInUser = br.readLine();
            txtFldEmail.setText(lastLoggedInUser);
        }
        catch (IOException e) {
            System.out.println("No previous user logged in");
        }
    }

    @FXML
    private void switchToSecondary() throws IOException {
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
            Message loginMessage = new Message("login", new User(txtFldEmail.getText(), passFldPassword.getText()));
            ClientConnection connection = new ClientConnection();
            Message loginResponse = connection.makeRequest(loginMessage);

            if (loginResponse != null) {
                if (loginResponse.isSuccess()) {
                    LoggedInUser.getInstance().setUser(loginResponse.getUser());
                    try {
                        switchToSecondary();
                    }
                    catch (IOException e) {
                        System.out.println("no switch");
                        e.printStackTrace();
                    }
                } else {
                    Platform.runLater(() -> MessageBox.display("Login failed", "Email and/or password incorrect"));

                }
            } else {
                Platform.runLater(() -> MessageBox.display("No response", "No response from server"));
            }
        });
        loginThread.start();
    }

    @FXML
    private void registerButtonPressed() {
        Thread registerThread = new Thread(() -> {
            if (!validateAndDisplayErrors()) {
                return;
            }
            Message registerRequest = new Message("register", new User(txtFldNewEmail.getText(), txtFldNewUsername.getText(), passFldNewPassword.getText(), true));
            ClientConnection connection = new ClientConnection();
            Message registerResponse = connection.makeRequest(registerRequest);

            if (registerResponse != null) {
                if (registerResponse.isSuccess()) {
                    LoggedInUser.getInstance().setUser(registerResponse.getUser());
                    Platform.runLater(() -> MessageBox.display("Success", "Account created successfully! Now logged in as " + LoggedInUser.getInstance().getUser().getUsername()));
                    try {
                        switchToSecondary();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Platform.runLater(() -> MessageBox.display("Failed", "Account creation failed!"));
                }
            } else {
                Platform.runLater(() -> MessageBox.display("No response", "No response from server"));
            }
        });
        registerThread.start();
    }

    /**
     * @return
     */
    private boolean validateAndDisplayErrors() {
        String email = txtFldNewEmail.getText();
        if (!validateEmail(email)) {
            MessageBox.display("Error", "Not a valid email");
            return false;
        }
        if (txtFldNewUsername.getText().isEmpty()) {
            MessageBox.display("Failed", "Username is required");
            return false;
        }
        if (passFldNewPassword.getText().isEmpty()) {
            MessageBox.display("Failed", "Password is required");
            return false;
        }
        return true;
    }

    /**
     * Method for validating an email by checking that it contains @
     *
     * @param email input email from user in application
     * @return true if the email contains @, false if it is not valid
     */
    private boolean validateEmail(String email) {
        final String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
