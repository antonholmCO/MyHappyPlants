package se.myhappyplants.client.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.client.view.SearchPlantPane;

import se.myhappyplants.shared.APIPlant;
import se.myhappyplants.shared.Message;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by: Christopher O'Driscoll
 * Updated by: Linn Borgström, Eric Simonson, Susanne Vikström
 */

public class PlantsTabController {

    @FXML private MainPaneController mainPaneController;
    @FXML private ImageView imgUserPicture;
    @FXML private Label lblUsernamePlants;
    @FXML private TextField txtFldSearchText;
    @FXML private ListView resultPane;
    @FXML private ProgressIndicator progressIndicator;

    @FXML
    public void initialize() {
        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        lblUsernamePlants.setText(loggedInUser.getUser().getUsername());
        imgUserPicture.setImage(new Image(loggedInUser.getUser().getAvatarURL()));
    }

    public void setMainController(MainPaneController mainPaneController) {
        this.mainPaneController = mainPaneController;
    }

    @FXML
    public void addPlantToCurrentUserLibrary(APIPlant plantAdd) {

        String plantNickname = plantAdd.common_name;

        int answer = MessageBox.askYesNo("Want to add a nickname?", "Do you want to add a nickname for your plant?");
        if (answer == 1) {
            plantNickname = MessageBox.askForStringInput("Add a nickname", "What do you want to call your plant?");
        }
        mainPaneController.getHomePaneController().addPlantToCurrentUserLibrary(plantAdd, plantNickname);
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
        Thread searchThread = new Thread(() -> {
        Message apiRequest = new Message("search", txtFldSearchText.getText());
        progressIndicator.setProgress(25);
        ClientConnection connection = new ClientConnection();
        Message apiResponse = connection.makeRequest(apiRequest);

        if (apiResponse != null) {
            if (apiResponse.isSuccess()) {
                progressIndicator.setProgress(50);
                showResultsOnPane(apiResponse);
            } else {
                Platform.runLater(() ->MessageBox.display("No results", "No results on " + txtFldSearchText.getText() + ", sorry!"));
            }
        } else {
            Platform.runLater(() ->MessageBox.display("No response", "No response from the server"));
        }
        });
        searchThread.start();
    }
    @FXML
    private void logoutButtonPressed() throws IOException {

        mainPaneController.logoutButtonPressed();
    }

    public ObservableList<String> getMorePlantInfo(APIPlant apiPlant) {
        Message getInfoSearchedPlant = new Message("getMorePlantInfoOnSearch", apiPlant);
        Message response = ClientConnection.getInstance().makeRequest(getInfoSearchedPlant);
        ObservableList<String> waterLightInfo = FXCollections.observableArrayList();
        if(response != null) {
            for (int i = 0; i < response.getStringArray().length; i++) {
                waterLightInfo.add(response.getStringArray()[i]);
            }
        }
        System.out.println("From PlantTabController: " + waterLightInfo.toString());
        return waterLightInfo;

    public void updateAvatar() {
        imgUserPicture.setImage(new Image(LoggedInUser.getInstance().getUser().getAvatarURL()));

    }
}
