package se.myhappyplants.client.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import se.myhappyplants.client.model.LoggedInUser;

public class SecondaryController {

    private ClientConnection connection;

    @FXML
    Label lblUserName1;
    @FXML
    Label lblUserName2;
    @FXML
    Label lblUserName3;

    @FXML
    public void initialize() {

        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        lblUserName1.setText(loggedInUser.getUser().getEmail());
        lblUserName2.setText(loggedInUser.getUser().getEmail());
        lblUserName3.setText(loggedInUser.getUser().getEmail());
        //userAvatar.setImage(new Image(loggedInUser.getUser().getAvatarURL()));

        //populateListView(testPlantArray());
    }

    public void SecondaryController (ClientConnection connection){
        this.connection = connection;
    }

    @FXML
    private void switchToPrimary() throws IOException {
        StartClient.setRoot("primary");
    }
    @FXML
    private void logoutButtonPressed() throws IOException {

        //ToDo - Some code to handle what happens when user wants to log out
        switchToPrimary();
    }
}