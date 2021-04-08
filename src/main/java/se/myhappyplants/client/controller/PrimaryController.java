package se.myhappyplants.client.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.model.LoginRequest;
import se.myhappyplants.client.model.Request;
import se.myhappyplants.client.view.ErrorMessage;
import se.myhappyplants.server.model.LoginResponse;
import se.myhappyplants.server.model.Response;

public class PrimaryController {

    private ClientConnection connection;

    public void PrimaryController (ClientConnection connection){
        this.connection = connection;
    }

    @FXML
    TextField txtFldUserName;
    @FXML
    PasswordField passFldPassword;

    @FXML
    private void switchToSecondary() throws IOException {
        StartClient.setRoot("secondary");
    }
    @FXML
    private void loginButtonPressed() throws IOException {

        //ToDo - Some code to handle what happens when user wants to log in
        // if successful, switch to logged in view

        Request loginRequest = new LoginRequest(txtFldUserName.getText(), passFldPassword.getText());
        LoginResponse loginResponse = (LoginResponse) ClientConnection.getInstance().makeRequest(loginRequest);

        if(loginResponse!=null) {
            if(loginResponse.isSuccess()) {
                LoggedInUser.getInstance().setUser(loginResponse.getUser());
                switchToSecondary();
            }
            else {
                ErrorMessage.display("Login failed", "email and/or password incorrect");
            }
        }
        else {
            ErrorMessage.display(" No response", "No response from server");
        }



    }
}
