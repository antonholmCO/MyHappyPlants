package se.myhappyplants.client.model;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import se.myhappyplants.client.controller.LoginPaneController;
import se.myhappyplants.client.view.MessageBox;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verifier {

    private TextField txtFldNewEmail;
    @FXML
    private TextField txtFldNewEmail1;
    @FXML
    private TextField txtFldNewUsername;
    @FXML
    private PasswordField passFldNewPassword;
    @FXML
    private PasswordField passFldNewPassword1;


    /**
     * @return
     */
    public boolean validateRegistration(LoginPaneController loginPaneController) {
        ArrayList<String> loginInfoToCompare = loginPaneController.getComponentsToVerify();
        for (String s : loginInfoToCompare) {


            //String email = txtFldNewEmail.getText();
            if (!validateEmail(s)) {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Error, "Please enter your email address in format: yourname@example.com"));
                return false;
            }
            if (s.isEmpty()) {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Error, "Please enter a username"));
                return false;
            }
            if (passFldNewPassword.getText().isEmpty()) {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Error, "Please enter a password"));
                return false;
            }
            if (!txtFldNewEmail1.getText().equals(txtFldNewEmail.getText())) {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Error, "Please enter the same email twice"));
                return false;
            }
            if (!passFldNewPassword1.getText().equals(passFldNewPassword.getText())) {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Error, "Please enter the same password twice"));
                return false;
            }
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
