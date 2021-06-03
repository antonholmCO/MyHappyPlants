package se.myhappyplants.client.model;

import javafx.application.Platform;
import se.myhappyplants.client.controller.LoginPaneController;
import se.myhappyplants.client.controller.RegisterPaneController;
import se.myhappyplants.client.view.MessageBox;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to validate the registration
 */
public class Verifier {


    /**
     * Static method to validate the registration when a user register a new account
     * @return boolean if successful
     */
    public boolean validateRegistration(RegisterPaneController registerPaneController) {
        String[] loginInfoToCompare = registerPaneController.getComponentsToVerify();

            if (!validateEmail(loginInfoToCompare[0])) {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Error, "Please enter your email address in format: yourname@example.com"));
                return false;
            }
            if (loginInfoToCompare[2].isEmpty()) {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Error, "Please enter a username"));
                return false;
            }
            if (loginInfoToCompare[3].isEmpty()) {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Error, "Please enter a password"));
                return false;
            }
            if (!loginInfoToCompare[1].equals(loginInfoToCompare[0])) {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Error, "Please enter the same email twice"));
                return false;
            }
            if (!loginInfoToCompare[4].equals(loginInfoToCompare[3])) {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Error, "Please enter the same password twice"));
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
