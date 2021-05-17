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
import se.myhappyplants.client.model.BoxTitle;
import se.myhappyplants.client.service.ClientConnection;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.client.view.SearchPlantPane;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.MessageType;
import se.myhappyplants.shared.Plant;
import se.myhappyplants.client.model.SetAvatar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by: Christopher O'Driscoll
 * Updated by: Linn Borgström, 2021-05-13
 */

public class SearchTabController {

    @FXML
    private MainPaneController mainPaneController;
    @FXML
    private ImageView imgUserPicture;
    @FXML
    private Label lblUsernamePlants;
    @FXML
    private TextField txtFldSearchText;
    @FXML
    private ListView listViewResult;
    @FXML
    private ProgressIndicator progressIndicator;


    @FXML
    public void initialize() {
        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        lblUsernamePlants.setText(loggedInUser.getUser().getUsername());
        //imgUserPicture.setImage(new Image(loggedInUser.getUser().getAvatarURL()));
        imgUserPicture.setImage(new Image(SetAvatar.setAvatarOnLogin(loggedInUser.getUser().getEmail())));
    }

    public void setMainController(MainPaneController mainPaneController) {
        this.mainPaneController = mainPaneController;
    }

    @FXML
    public void addPlantToCurrentUserLibrary(Plant plantAdd) {
        String plantNickname = plantAdd.getCommonName();

        int answer = MessageBox.askYesNo(BoxTitle.Add, "Do you want to add a nickname for your plant?");
        if (answer == 1) {
            plantNickname = MessageBox.askForStringInput("Add a nickname", "Nickname:");
        }
        mainPaneController.getHomePaneController().addPlantToCurrentUserLibrary(plantAdd, plantNickname);
    }

    private void showResultsOnPane(Message apiResponse) {
        progressIndicator.setProgress(75);
        ArrayList<Plant> searchedPlant = apiResponse.getPlantList();

        ObservableList<SearchPlantPane> searchPlantPanes = FXCollections.observableArrayList();
        for (Plant plant : searchedPlant) {
            searchPlantPanes.add(new SearchPlantPane(this, new File("resources/images/img.png").toURI().toString(), plant));
        }
        listViewResult.getItems().clear();
        listViewResult.setItems(searchPlantPanes);
        progressIndicator.setProgress(100);
        Thread imageThread = new Thread(() -> {
            for (SearchPlantPane spp : searchPlantPanes) {
                Plant Plant = spp.getApiPlant();
                if (Plant.getImageURL().equals("")) {
                    spp.setDefaultImage(new File("resources/images/Grn_vxt.png").toURI().toString());
                } else {
                    try {
                        spp.updateImage();
                    } catch (IllegalArgumentException e) {
                        spp.setDefaultImage(new File("resources/images/Grn_vxt.png").toURI().toString());
                    }
                }
            }
        });
        imageThread.start();
    }

    @FXML
    private void searchButtonPressed() {
        Thread searchThread = new Thread(() -> {
            Message apiRequest = new Message(MessageType.search, txtFldSearchText.getText());
            progressIndicator.setProgress(25);
            ClientConnection connection = new ClientConnection();
            Message apiResponse = connection.makeRequest(apiRequest);

            if (apiResponse != null) {
                if (apiResponse.isSuccess()) {
                    progressIndicator.setProgress(50);
                    Platform.runLater(() -> showResultsOnPane(apiResponse));
                } else {
                    //TODO: skicka inget felmeddelande, visa label med sökresultat 0 istället
                }
            } else {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Error, "The connection to the server has failed. Check your connection and try again."));
            }
        });
        searchThread.start();
    }

    @FXML
    private void logoutButtonPressed() throws IOException {
        mainPaneController.logoutButtonPressed();
    }

    public ObservableList<String> getMorePlantInfo(Plant plant) {
        Message getInfoSearchedPlant = new Message(MessageType.getMorePlantInfoOnSearch, plant);
        Message response = new ClientConnection().makeRequest(getInfoSearchedPlant);
        ObservableList<String> waterLightInfo = FXCollections.observableArrayList();
        if (response != null) {
            for (int i = 0; i < response.getStringArray().length; i++) {
                waterLightInfo.add(response.getStringArray()[i]);
            }
        }
        return waterLightInfo;
    }

    public void updateAvatar() {
        imgUserPicture.setImage(new Image(LoggedInUser.getInstance().getUser().getAvatarURL()));
    }
}
