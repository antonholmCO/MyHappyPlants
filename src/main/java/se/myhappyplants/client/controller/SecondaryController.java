package se.myhappyplants.client.controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import se.myhappyplants.client.model.APIRequest;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.model.Request;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.server.model.APIPlant;
import se.myhappyplants.server.model.APIResponse;
import se.myhappyplants.server.model.Response;

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
    @FXML
    TextField txtFldSearch;

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

    @FXML
    private void searchButtonPressed() {
        String searchWord = txtFldSearch.getText();
        System.out.println(searchWord);
        Request apiRequest = new APIRequest(searchWord);
        APIResponse apiResponse = (APIResponse)ClientConnection.getInstance().makeRequest(apiRequest);

        if (apiResponse != null) {
            if (apiResponse.isSuccess()) {
                System.out.println("det gick bra med att hämta api-växter");
                System.out.println(apiResponse);
                sendToSearchPlantPane(apiResponse);
            }
        } else {
            MessageBox.display("No response", "No response from the server");
        }


    }

    private void sendToSearchPlantPane(APIResponse apiResponse) {

        ArrayList<APIPlant> searchedPlant = apiResponse.getPlantList();
        for (APIPlant p : searchedPlant) {
            System.out.println(p.getCommon_name());
        }


    }
}