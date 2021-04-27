package se.myhappyplants.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.client.view.SearchPlantPane;
import se.myhappyplants.shared.APIPlant;
import se.myhappyplants.shared.Message;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlantsTabController {

    @FXML private SecondaryController secondaryController;

    @FXML private Label lblUsernamePlants;
    @FXML private TextField txtFldSearchText;
    @FXML private ListView resultPane;
    @FXML private ProgressIndicator progressIndicator;

    @FXML public void initialize() {

        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        lblUsernamePlants.setText(loggedInUser.getUser().getUsername());
        //userAvatar.setImage(new Image(loggedInUser.getUser().getAvatarURL()));
    }

    public void setSecondaryController (SecondaryController secondaryController) {
        this.secondaryController = secondaryController;
    }

    @FXML
    public void addPlantToCurrentUserLibrary(APIPlant plantAdd) {

        String plantNickname = plantAdd.common_name;

        int answer = MessageBox.askYesNo("Want to add a nickname?", "Do you want to add a nickname for your plant?");
        if (answer == 1) {
            plantNickname = MessageBox.askForStringInput("Add a nickname", "What do you want to call your plant?");
        }
        secondaryController.getHomePaneController().addPlantToCurrentUserLibrary(plantAdd, plantNickname);
    }
    private void showResultsOnPane(Message apiResponse) {
        progressIndicator.setProgress(75);
        ArrayList<APIPlant> searchedPlant = apiResponse.getPlantList();

        ObservableList<SearchPlantPane> searchPlantPanes = FXCollections.observableArrayList();
        for(APIPlant plant: searchedPlant) {
            searchPlantPanes.add(new SearchPlantPane(this,new File("resources/images/img.png").toURI().toString(), plant));
        }
        resultPane.setItems(searchPlantPanes);
        progressIndicator.setProgress(100);
        Thread imageThread = new Thread(() -> {
            for(SearchPlantPane spp: searchPlantPanes) {
                APIPlant apiPlant = spp.getApiPlant();
                if (apiPlant.image_url == null) {
                    spp.setDefaultImage(new File("resources/images/Grn_vxt.png").toURI().toString());
                } else {
                    spp.updateImage();
                }
            }
        });
        imageThread.start();
    }

    @FXML
    private void searchButtonPressed() {
        Message apiRequest = new Message("search", txtFldSearchText.getText());
        progressIndicator.setProgress(25);
        Message apiResponse = ClientConnection.getInstance().makeRequest(apiRequest);

        if (apiResponse != null) {
            if (apiResponse.isSuccess()) {
                progressIndicator.setProgress(50);
                showResultsOnPane(apiResponse);
            } else {

                MessageBox.display("No results", "No results on " + txtFldSearchText.getText() + ", sorry!");

            }
        } else {
            MessageBox.display("No response", "No response from the server");
        }
    }

    @FXML
    private void logoutButtonPressed() throws IOException {

        secondaryController.logoutButtonPressed();

    }
}
