package se.myhappyplants.client.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import se.myhappyplants.client.model.*;
import se.myhappyplants.client.service.ServerConnection;
import se.myhappyplants.client.view.AutocompleteSearchField;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.client.view.PopupBox;
import se.myhappyplants.client.view.SearchPlantPane;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.MessageType;
import se.myhappyplants.shared.Plant;
import se.myhappyplants.client.model.SetAvatar;
import se.myhappyplants.shared.PlantDetails;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that controls the logic of the "search"-tab
 * Created by: Christopher O'Driscoll
 * Updated by: Christopher O'Driscoll, 2021-05-14
 */

public class SearchTabPaneController {
    @FXML
    public ListView lstFunFacts;
    @FXML
    private MainPaneController mainPaneController;
    @FXML
    private Circle imgUserAvatar;
    @FXML
    private Label lblUsername;
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
    @FXML
    public ImageView imgFunFactTitle;
    @FXML
    public TextField txtNbrOfResults;

    private ArrayList<Plant> searchResults;

    /**
     * Method to initialize the GUI
     * @throws IOException
     */
    @FXML
    public void initialize() {
        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        lblUsername.setText(loggedInUser.getUser().getUsername());
        imgUserAvatar.setFill(new ImagePattern(new Image(SetAvatar.setAvatarOnLogin(loggedInUser.getUser().getEmail()))));
        cmbSortOption.setItems(ListSorter.sortOptionsSearch());
        showFunFact(LoggedInUser.getInstance().getUser().areFunFactsActivated());
    }

    /**
     * Method to message the right controller-class that the log out-button has been pressed
     * @throws IOException
     */
    public void setMainController(MainPaneController mainPaneController) {
        this.mainPaneController = mainPaneController;
    }
    /**
     * Method to set and display the fun facts
     * @param factsActivated boolean, if the user has activated the option to true
     */
    public void showFunFact(boolean factsActivated) {

        FunFacts funFacts = new FunFacts();
        if (factsActivated) {
            imgFunFactTitle.setVisible(true);
            lstFunFacts.setItems(funFacts.getRandomFact());
        }
        else {
            imgFunFactTitle.setVisible(false);
            lstFunFacts.setItems(null);
        }
    }

    /**
     * Method to add a plant to the logged in users library. Asks the user if it wants to add a nickname to the plant and receives a string if the answer is yes
     * @param plantAdd the selected plant to add
     */
    @FXML
    public void addPlantToCurrentUserLibrary(Plant plantAdd) {
        String plantNickname = plantAdd.getCommonName();

        int answer = MessageBox.askYesNo(BoxTitle.Add, "Do you want to add a nickname for your plant?");
        if (answer == 1) {
            plantNickname = MessageBox.askForStringInput("Add a nickname", "Nickname:");
        }
        mainPaneController.getMyPlantsTabPaneController().addPlantToCurrentUserLibrary(plantAdd, plantNickname);
    }

    /**
     * Method to show the search result on the pane
     */
    private void showResultsOnPane() {
        ObservableList<SearchPlantPane> searchPlantPanes = FXCollections.observableArrayList();
        for (Plant plant : searchResults) {
            searchPlantPanes.add(new SearchPlantPane(this, ImageLibrary.getLoadingImageFile().toURI().toString(), plant));
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
                                spp.setDefaultImage(ImageLibrary.getDefaultPlantImage().toURI().toString());
                            }
                            else {
                                try {
                                    spp.updateImage();
                                }
                                catch (IllegalArgumentException e) {
                                    spp.setDefaultImage(ImageLibrary.getDefaultPlantImage().toURI().toString());
                                }
                            }
                            updateProgress(i++, searchPlantPanes.size());
                        }
                            Text text = (Text) progressIndicator.lookup(".percentage");
                            if(text.getText().equals("90%") || text.getText().equals("Done")){
                                text.setText("Done");
                                progressIndicator.setPrefWidth(text.getLayoutBounds().getWidth());
                            }
                        return true;
                    }
                };
        Thread imageThread = new Thread(getImagesTask);
        progressIndicator.progressProperty().bind(getImagesTask.progressProperty());
        imageThread.start();
    }

    /**
     * Method to sent a message to the server to get the results from the database. Displays a message to the user that more info is on its way
     */
    @FXML
    private void searchButtonPressed() {
        btnSearch.setDisable(true);
        txtFldSearchText.addToHistory();
        PopupBox.display(MessageText.holdOnGettingInfo.toString());
        Thread searchThread = new Thread(() -> {
            Message apiRequest = new Message(MessageType.search, txtFldSearchText.getText());
            ServerConnection connection = ServerConnection.getClientConnection();
            Message apiResponse = connection.makeRequest(apiRequest);

            if (apiResponse != null) {
                if (apiResponse.isSuccess()) {
                    searchResults = apiResponse.getPlantArray();
                    Platform.runLater(() -> txtNbrOfResults.setText(searchResults.size() + " results"));
                    if(searchResults.size() == 0) {
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.setProgress(100);
                        btnSearch.setDisable(false);
                        Platform.runLater(() -> listViewResult.getItems().clear());
                        return;
                    }
                    Platform.runLater(() -> showResultsOnPane());
                }
            }
            else {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Error, "The connection to the server has failed. Check your connection and try again."));
            }
            btnSearch.setDisable(false);
        });
        searchThread.start();
    }

    /**
     * Method to message the right controller-class that the log out-button has been pressed
     * @throws IOException
     */
    @FXML
    private void logoutButtonPressed() throws IOException {
        mainPaneController.logoutButtonPressed();
    }

    public PlantDetails getPlantDetails(Plant plant) {
        PopupBox.display(MessageText.holdOnGettingInfo.toString());
        PlantDetails plantDetails = null;
        Message getInfoSearchedPlant = new Message(MessageType.getMorePlantInfo, plant);
        ServerConnection connection = ServerConnection.getClientConnection();
        Message response = connection.makeRequest(getInfoSearchedPlant);
        if (response != null) {
            plantDetails = response.getPlantDetails();
        }
        return plantDetails;
    }

    /**
     * Method to rearranges the results based on selected sorting option
     */
    @FXML
    public void sortResults() {
        SortingOption selectedOption;
        selectedOption = cmbSortOption.getValue();
        listViewResult.setItems(ListSorter.sort(selectedOption, listViewResult.getItems()));
    }

    /**
     * Method to update the users avatar picture on the tab
     */
    public void updateAvatar() {
        imgUserAvatar.setFill(new ImagePattern(new Image(LoggedInUser.getInstance().getUser().getAvatarURL())));
    }
}
