import client.src.model.LoggedInUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.src.model.User;

import java.io.IOException;

public class LoginController {

    @FXML
    public TextField tfUserName;
    public PasswordField pfPassword;

    /**
     * Constructor with access to FXML attributes
     */
    @FXML
    public void initialize() {

    }

    public void logInButtonPressed(javafx.event.ActionEvent actionEvent) throws IOException {

        if(verifyLoginDetails()) {
            StartClient.setRoot("HomeParent");
        }
    }

    private boolean verifyLoginDetails() {
        String userName = tfUserName.getText();
        String password = pfPassword.getText();

        //some code to call a method that authenticates user login with database

        User user = new User(userName);
        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        loggedInUser.setUser(user); //stores the logged in user object in a singleton class, to give other stages access to its details
        return true;
    }
}
