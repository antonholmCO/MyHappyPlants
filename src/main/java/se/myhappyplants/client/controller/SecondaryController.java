package se.myhappyplants.client.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import se.myhappyplants.client.model.LoggedInUser;

/**
 * Controls the inputs from a 'logged in' user
 * @author Christopher O'Driscoll
 * @author Eric Simonsson
 * */
public class SecondaryController {

    private ClientConnection connection;

    @FXML
    Label lblUserName1;
    @FXML
    Label lblUserName2;
    @FXML
    Label lblUserName3;

    /**
     * Constructor that has access to FXML variables
     */
    @FXML
    public void initialize() {

        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        lblUserName1.setText(loggedInUser.getUser().getUsername());
        lblUserName2.setText(loggedInUser.getUser().getUsername());
        lblUserName3.setText(loggedInUser.getUser().getUsername());
        //userAvatar.setImage(new Image(loggedInUser.getUser().getAvatarURL()));

        //populateListView(testPlantArray());
    }

    /**
     * Default constructor, probably unnecessary
     * @param connection
     */
    public void SecondaryController (ClientConnection connection){
        this.connection = connection;
    }

    /**
     * Switches to 'logged out' scene
     * @throws IOException
     */
    @FXML
    private void switchToPrimary() throws IOException {
        StartClient.setRoot("primary");
    }

    /**
     * Logs out user, then switches scenes
     * @throws IOException
     */
    @FXML
    private void logoutButtonPressed() throws IOException {

        //ToDo - Some code to handle what happens when user wants to log out
        switchToPrimary();
    }
}