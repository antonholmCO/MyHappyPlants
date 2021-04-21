package se.myhappyplants.client.controller;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.shared.Message;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.shared.User;

/**
 * Controls the inputs from a user that hasn't logged in
 * Created by: Eric Simonsson, Christopher O'Driscoll
 * Updated by: Christopher, 2021-04-13
 *
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
    public void initialize(){
        String lastLoggedInUser;

        try(BufferedReader bw = new BufferedReader(new FileReader( "resources/lastLogin.txt"));){
            lastLoggedInUser = bw.readLine();
            txtFldEmail.setText(lastLoggedInUser);
        }
        catch (IOException e){
            System.out.println("No previous user logged in");
        }


    }
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

        Message loginMessage = new Message("login", new User(txtFldEmail.getText(), passFldPassword.getText()));
        Message loginResponse = ClientConnection.getInstance().makeRequest(loginMessage);

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
        if(!validateAndDisplayErrors()) {
            return;
        }
        Message registerRequest = new Message("register", new User(txtFldNewEmail.getText(), txtFldNewUsername.getText(), passFldNewPassword.getText(), true));
        Message registerResponse = ClientConnection.getInstance().makeRequest(registerRequest);

        if(registerResponse!=null) {
            if(registerResponse.isSuccess()) {
                LoggedInUser.getInstance().setUser(registerResponse.getUser());
                MessageBox.display("Success", "Account created successfully! Now logged in as " + LoggedInUser.getInstance().getUser().getUsername());
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

    /**
     *
     * @return
     */
    private boolean validateAndDisplayErrors() {
        String email = txtFldNewEmail.getText();
        if(!validateEmail(email)) {
            MessageBox.display("Error", "Not a valid email");
            return false;
        }
        if(txtFldNewUsername.getText().isEmpty()) {
            MessageBox.display("Failed", "Username is required");
            return false;
        }
        if(passFldNewPassword.getText().isEmpty()) {
            MessageBox.display("Failed", "Password is required");
            return false;
        }
        return true;
    }

    /**
     * Method for validating an email by checking that it contains @
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
