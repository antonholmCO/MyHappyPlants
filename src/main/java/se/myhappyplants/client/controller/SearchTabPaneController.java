package se.myhappyplants.client.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import se.myhappyplants.client.model.BoxTitle;
import se.myhappyplants.client.model.ListSorter;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.model.SortingOption;
import se.myhappyplants.client.service.ClientConnection;
import se.myhappyplants.client.view.AutocompleteSearchField;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.client.view.SearchPlantPane;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.MessageType;
import se.myhappyplants.shared.Plant;
import se.myhappyplants.client.model.SetAvatar;
import se.myhappyplants.shared.PlantDetails;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by: Christopher O'Driscoll
 * Updated by: Christopher O'Driscoll, 2021-05-14
 */

public class SearchTabPaneController {

    @FXML
    private MainPaneController mainPaneController;
    @FXML
    private Circle imgUserPicture;
    @FXML
    private Label lblUsernamePlants;
    @FXML
    private Button btnSearch;
    @FXML
    private AutocompleteSearchField txtFldSearchText;
    @FXML
    private ComboBox<SortingOption> cmbSortOption;
    @FXML
    private ListView listViewResult;
    @FXML
    private ProgressIndicator progressIndicator;

    private ArrayList<Plant> searchResults;


    @FXML
    public void initialize() {
        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        lblUsernamePlants.setText(loggedInUser.getUser().getUsername());
        imgUserPicture.setFill(new ImagePattern(new Image(SetAvatar.setAvatarOnLogin(loggedInUser.getUser().getEmail()))));
        cmbSortOption.setItems(ListSorter.sortOptionsSearch());
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

    private void showResultsOnPane() {
        ObservableList<SearchPlantPane> searchPlantPanes = FXCollections.observableArrayList();
        for (Plant plant : searchResults) {
            searchPlantPanes.add(new SearchPlantPane(this, new File("resources/images/img.png").toURI().toString(), plant));
        }
        listViewResult.getItems().clear();
        listViewResult.setItems(searchPlantPanes);

        Task getImagesTask =
                new Task() {
                    @Override
                    protected Object call() {
                        long i = 1;
                        for (SearchPlantPane spp : searchPlantPanes) {
                            Plant Plant = spp.getPlant();
                            if (Plant.getImageURL().equals("")) {
                                spp.setDefaultImage(new File("resources/images/Grn_vxt.png").toURI().toString());
                            } else {
                                try {
                                    spp.updateImage();
                                } catch (IllegalArgumentException e) {
                                    spp.setDefaultImage(new File("resources/images/Grn_vxt.png").toURI().toString());
                                }
                            }
                            updateProgress(i++, searchPlantPanes.size());
                        }
                        Text text = (Text) progressIndicator.lookup(".percentage");
                        text.setText("Done");
                        return true;
                    }
                };
        Thread imageThread = new Thread(getImagesTask);
        progressIndicator.progressProperty().bind(getImagesTask.progressProperty());
        imageThread.start();
    }

    @FXML
    private void searchButtonPressed() {
        btnSearch.setDisable(true);
        txtFldSearchText.addToHistory();
        Thread searchThread = new Thread(() -> {
            Message apiRequest = new Message(MessageType.searchForPlant, txtFldSearchText.getText());
            ClientConnection connection = new ClientConnection();
            Message apiResponse = connection.makeRequest(apiRequest);

            if (apiResponse != null) {
                if (apiResponse.isSuccess()) {
                    searchResults = apiResponse.getPlantArray();
                    Platform.runLater(() -> showResultsOnPane());
                } else {
                    //TODO: skicka inget felmeddelande, visa label med sökresultat 0 istället
                }
            } else {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Error, "The connection to the server has failed. Check your connection and try again."));
            }
            btnSearch.setDisable(false);
        });
        searchThread.start();
    }

    @FXML
    private void logoutButtonPressed() throws IOException {
        mainPaneController.logoutButtonPressed();
    }

    public ObservableList<String> getMorePlantInfo(Plant plant) {
        Message getInfoSearchedPlant = new Message(MessageType.getMorePlantInfo, plant);
        Message response = new ClientConnection().makeRequest(getInfoSearchedPlant);
        ObservableList<String> waterLightInfo = FXCollections.observableArrayList();
        if (response != null) {
            for (int i = 0; i < response.getStringArray().length; i++) {
                waterLightInfo.add(response.getStringArray()[i]);
            }
        }
        return waterLightInfo;
    }

    public PlantDetails getPlantDetails(Plant plant) {
        PlantDetails plantDetails = null;
        Message getInfoSearchedPlant = new Message(MessageType.getPlantDetails, plant);
        Message response = new ClientConnection().makeRequest(getInfoSearchedPlant);
        if (response != null) {
            plantDetails = response.getPlantDetails();
        }
        return plantDetails;
    }

    /**
     * rearranges the results based on selected sorting option
     */
    @FXML
    public void sortResults() {
        SortingOption selectedOption;
        selectedOption = cmbSortOption.getValue();
        listViewResult.setItems(ListSorter.sort(selectedOption, listViewResult.getItems()));
    }

    public void updateAvatar() {
        imgUserPicture.setFill(new ImagePattern(new Image(LoggedInUser.getInstance().getUser().getAvatarURL())));
    }
}
