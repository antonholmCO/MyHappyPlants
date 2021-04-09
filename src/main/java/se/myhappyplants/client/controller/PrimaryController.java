package se.myhappyplants.client.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.model.LoginRequest;
import se.myhappyplants.client.model.RegisterRequest;
import se.myhappyplants.client.model.Request;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.server.model.LoginResponse;

/**
 * Controls the inputs from a user that hasn't logged in
 * */
public class PrimaryController {

    private ClientConnection connection;

    /**
     * Default constructor, probably unnecessary
     * @param connection
     */
    public void PrimaryController (ClientConnection connection){
        this.connection = connection;
    }

    @FXML
    TextField txtFldEmail;
    @FXML
    PasswordField passFldPassword;
    @FXML
    TextField txtFldNewEmail;
    @FXML
    TextField txtFldNewUsername;
    @FXML
    PasswordField passFldNewPassword;

    /**
     * Switches to 'logged in' scene
     * @throws IOException
     */
    @FXML
    private void switchToSecondary() throws IOException {
        StartClient.setRoot("secondary");
    }

    /**
     * Tries to log in user.
     * If successful, changes scene
     * @throws IOException
     */
    @FXML
    private void loginButtonPressed() throws IOException {

        //ToDo - Some code to handle what happens when user wants to log in
        // if successful, switch to logged in view

        Request loginRequest = new LoginRequest(txtFldEmail.getText(), passFldPassword.getText());
        LoginResponse loginResponse = (LoginResponse) ClientConnection.getInstance().makeRequest(loginRequest);

        if(loginResponse!=null) {
            if(loginResponse.isSuccess()) {
                LoggedInUser.getInstance().setUser(loginResponse.getUser());
                switchToSecondary();
            }
            else {
                MessageBox.display("Login failed", "Email and/or password incorrect");
            }
        }
        else {
            MessageBox.display("No response", "No response from server");
        }
    }
    @FXML
    private void registerButtonPressed() throws IOException {

        //todo - code that creates a registration request

        Request registrationRequest = new RegisterRequest(txtFldNewEmail.getText(), txtFldNewUsername.getText(), passFldNewPassword.getText());
        LoginResponse loginResponse = (LoginResponse) ClientConnection.getInstance().makeRequest(registrationRequest);

        if(loginResponse!=null) {
            if(loginResponse.isSuccess()) {
                LoggedInUser.getInstance().setUser(loginResponse.getUser());
                MessageBox.display("Success", "Account created successfully! Now logged in as " + LoggedInUser.getInstance().getUser().getFirstName());
                switchToSecondary();
            }
            else {
                MessageBox.display("Failed", "Account creation failed!");
            }
        }
        else {
            MessageBox.display("No response", "No response from server");
        }
    }

}
