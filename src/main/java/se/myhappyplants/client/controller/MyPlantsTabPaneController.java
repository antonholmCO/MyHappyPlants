package se.myhappyplants.client.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import se.myhappyplants.client.model.*;
import se.myhappyplants.client.service.ClientConnection;
import se.myhappyplants.client.view.LibraryPlantPane;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.client.view.PopupBox;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.MessageType;
import se.myhappyplants.shared.Plant;
import se.myhappyplants.client.model.SetAvatar;
import se.myhappyplants.shared.PlantDetails;


import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Controller with logic used by the "My Plants" tab
 * Created by: Christopher O'Driscoll
 * Updated by: Christopher O'Driscoll, 2021-05-14
 */
public class MyPlantsTabPaneController {

    @FXML
    public ImageView imgNotifications;

    private ArrayList<Plant> currentUserLibrary;

    @FXML
    private MainPaneController mainPaneController;

    @FXML
    private Label lblUsernameMyPlants;

    @FXML
    private Circle imgUserPicture;

    @FXML
    private ComboBox<SortingOption> cmbSortOption;

    @FXML
    private ListView lstViewUserPlantLibrary;

    @FXML
    private ListView<String> lstViewNotifications;

    @FXML
    private Button btnWaterAll;

    @FXML
    private Button btnExpandAll;

    @FXML
    public Button btnCollapseAll;

    /**
     * Method to initilize the variables
     */

    @FXML
    public void initialize() {
        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        lblUsernameMyPlants.setText(loggedInUser.getUser().getUsername());
        imgUserPicture.setFill(new ImagePattern(new Image(SetAvatar.setAvatarOnLogin(loggedInUser.getUser().getEmail()))));
        //MainPaneController.makeAvatarRound(imgUserPicture);
        cmbSortOption.setItems(ListSorter.sortOptionsLibrary());
        createCurrentUserLibraryFromDB();
        addCurrentUserLibraryToHomeScreen();
    }


    /**
     * Method to set the mainPaneController
     * @param mainPaneController to set
     */
    public void setMainController(MainPaneController mainPaneController) {
        this.mainPaneController = mainPaneController;
    }
    public MainPaneController getMainPaneController() {
        return mainPaneController;
    }

    /**
     * Method to add a users plants to myPlantsTab
     */
    @FXML
    public void addCurrentUserLibraryToHomeScreen() {
        ObservableList<LibraryPlantPane> obsListLibraryPlantPane = FXCollections.observableArrayList();
        if (currentUserLibrary == null) {
            disableButtons();
            obsListLibraryPlantPane.add(new LibraryPlantPane());
        } else {
            if (currentUserLibrary.size()<1) {
                disableButtons();
                obsListLibraryPlantPane.add(new LibraryPlantPane(this));
            } else {
                enableButtons();
                for (Plant plant : currentUserLibrary) {
                    obsListLibraryPlantPane.add(new LibraryPlantPane(this, plant));
                }
            }
        }
        Platform.runLater(() -> {
            lstViewUserPlantLibrary.setItems(obsListLibraryPlantPane);
            sortLibrary();
        });
    }

    private void disableButtons () {
        btnWaterAll.setDisable(true);
        btnExpandAll.setDisable(true);
        btnCollapseAll.setDisable(true);
    }
    private void enableButtons () {
        btnWaterAll.setDisable(false);
        btnExpandAll.setDisable(false);
        btnCollapseAll.setDisable(false);
    }

    public void showNotifications() {
        ObservableList<String> notificationStrings = NotificationsCreator.getNotificationsStrings(currentUserLibrary, imgNotifications);
        Platform.runLater(() -> lstViewNotifications.setItems(notificationStrings));
    }

    @FXML
    public void createCurrentUserLibraryFromDB() {
        Thread getLibraryThread = new Thread(() -> {
            Message getLibrary = new Message(MessageType.getLibrary, LoggedInUser.getInstance().getUser());
            ClientConnection connection = ClientConnection.getClientConnection();
            Message response = connection.makeRequest(getLibrary);

            if (response.isSuccess()) {
                currentUserLibrary = response.getPlantArray();
                addCurrentUserLibraryToHomeScreen();
                showNotifications();
            } else {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Failed, "The connection to the server has failed. Check your connection and try again."));
            }
        });
        getLibraryThread.start();
    }

    @FXML
    public void removePlantFromDB(Plant plant) {
        Platform.runLater(() ->PopupBox.display(MessageText.removePlant.toString()));
        Thread removePlantThread = new Thread(() -> {
            currentUserLibrary.remove(plant);
            addCurrentUserLibraryToHomeScreen();
            Message deletePlant = new Message(MessageType.deletePlant, LoggedInUser.getInstance().getUser(), plant);
            ClientConnection connection = ClientConnection.getClientConnection();
            Message response = connection.makeRequest(deletePlant);

            if (!response.isSuccess()) {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Failed, "The connection to the server has failed. Check your connection and try again."));
                createCurrentUserLibraryFromDB();
            }
        });
        removePlantThread.start();
    }

    @FXML
    public void addPlantToCurrentUserLibrary(Plant selectedPlant, String plantNickname) {
        int plantsWithThisNickname = 1;
        String uniqueNickName = plantNickname;
        for (Plant plant : currentUserLibrary) {
            if (plant.getNickname().equals(uniqueNickName)) {
                plantsWithThisNickname++;
                uniqueNickName = plantNickname + plantsWithThisNickname;
            }
        }
        long currentDateMilli = System.currentTimeMillis();
        Date date = new Date(currentDateMilli);
        String imageURL = PictureRandomizer.getRandomPictureURL();
        Plant plantToAdd = new Plant(uniqueNickName, selectedPlant.getPlantId(), date, imageURL);
        PopupBox.display(MessageText.sucessfullyAddPlant.toString());
        addPlantToDB(plantToAdd);
    }

    @FXML
    public void addPlantToDB(Plant plant) {
        Thread addPlantThread = new Thread(() -> {
            currentUserLibrary.add(plant);
            addCurrentUserLibraryToHomeScreen();
            Message savePlant = new Message(MessageType.savePlant, LoggedInUser.getInstance().getUser(), plant);
            ClientConnection connection = ClientConnection.getClientConnection();
            Message response = connection.makeRequest(savePlant);
            if (!response.isSuccess()) {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Failed, "The connection to the server has failed. Check your connection and try again."));
                createCurrentUserLibraryFromDB();
            }
        });
        addPlantThread.start();
    }

    @FXML
    private void logoutButtonPressed() throws IOException {
        mainPaneController.logoutButtonPressed();
    }

    /**
     * Method to change last watered date in database, send a request to server and get a boolean respons depending on the result
     *
     * @param plant instance of the plant which to change last watered date
     * @param date  new date to change to
     */
    public void changeLastWateredInDB(Plant plant, LocalDate date) {
        Message changeLastWatered = new Message(MessageType.changeLastWatered, LoggedInUser.getInstance().getUser(), plant, date);
        ClientConnection connection = ClientConnection.getClientConnection();
        Message response = connection.makeRequest(changeLastWatered);
        PopupBox.display(MessageText.sucessfullyChangedDate.toString());
        if (!response.isSuccess()) {
            MessageBox.display(BoxTitle.Failed, "The connection to the server has failed. Check your connection and try again.");
        }
        createCurrentUserLibraryFromDB();
        showNotifications();
    }

    /**
     * @param plant
     * @param newNickname
     * @return
     */
    public boolean changeNicknameInDB(Plant plant, String newNickname) {
        Message changeNicknameInDB = new Message(MessageType.changeNickname, LoggedInUser.getInstance().getUser(), plant, newNickname);
        ClientConnection connection = ClientConnection.getClientConnection();
        Message response = connection.makeRequest(changeNicknameInDB);
        PopupBox.display(MessageText.sucessfullyChangedPlant.toString());
        if (!response.isSuccess()) {
            MessageBox.display(BoxTitle.Failed, "It was not possible to change nickname for you plant. Try again.");
            return false;
        } else {
            plant.setNickname(newNickname);
            sortLibrary();
            return true;
        }
    }

    /**
     * rearranges the library based on selected sorting option
     */
    public void sortLibrary() {
        SortingOption selectedOption;
        selectedOption = cmbSortOption.getValue();
        if (selectedOption == null)
            selectedOption = SortingOption.nickname;
        lstViewUserPlantLibrary.setItems(ListSorter.sort(selectedOption, lstViewUserPlantLibrary.getItems()));
    }

    public void updateAvatar() {
        imgUserPicture.setFill(new ImagePattern(new Image(LoggedInUser.getInstance().getUser().getAvatarURL())));
    }

    public PlantDetails getPlantDetails(Plant plant) {
        PlantDetails plantDetails = null;
        Message getInfoSearchedPlant = new Message(MessageType.getMorePlantInfo, plant);
        ClientConnection connection = ClientConnection.getClientConnection();
        Message response = connection.makeRequest(getInfoSearchedPlant);
        if (response != null) {
            plantDetails = response.getPlantDetails();
        }
        return plantDetails;
    }

    @FXML
    public void waterAll() {
        btnWaterAll.setDisable(true);
        ObservableList<LibraryPlantPane> libraryPlantPanes = lstViewUserPlantLibrary.getItems();
        changeAllToWateredInDB();
        for (LibraryPlantPane lpp : libraryPlantPanes) {
            lpp.getProgressBar().setProgress(100);
            lpp.setColorProgressBar(100);
        }
    }
    @FXML
    public void expandAll() {
        btnExpandAll.setDisable(true);
        ObservableList<LibraryPlantPane> libraryPlantPanes = lstViewUserPlantLibrary.getItems();
        for (LibraryPlantPane lpp : libraryPlantPanes) {
            if (!lpp.extended)
                lpp.pressInfoButton();
        }
        btnExpandAll.setDisable(false);
    }

    @FXML
    public void collapseAll() {
        btnCollapseAll.setDisable(true);
        ObservableList<LibraryPlantPane> libraryPlantPanes = lstViewUserPlantLibrary.getItems();
        for (LibraryPlantPane lpp : libraryPlantPanes) {
            if (lpp.extended)
                lpp.pressInfoButton();
        }
        btnCollapseAll.setDisable(false);
    }

    private void changeAllToWateredInDB() {
        Thread waterAllThread = new Thread(() -> {
            Message changeAllToWatered = new Message(MessageType.changeAllToWatered, LoggedInUser.getInstance().getUser());
            ClientConnection connection = ClientConnection.getClientConnection();
            Message response = connection.makeRequest(changeAllToWatered);
            if (!response.isSuccess()) {
                MessageBox.display(BoxTitle.Failed, "The connection to the server has failed. Check your connection and try again.");
            }
            btnWaterAll.setDisable(false);
            createCurrentUserLibraryFromDB();
            showNotifications();
        });
        waterAllThread.start();
    }

}
