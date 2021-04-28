package se.myhappyplants.client.controller;

import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.view.LibraryPlantPane;
import se.myhappyplants.client.view.MessageBox;

import se.myhappyplants.shared.APIPlant;
import se.myhappyplants.shared.DBPlant;
import se.myhappyplants.shared.Message;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public class HomeTabController {

    private ArrayList<DBPlant> currentUserLibrary;

    @FXML private MainPaneController mainPaneController;

    @FXML private Label lblUsernameHome;
    @FXML private ListView userPlantLibrary;

    @FXML public void initialize() {

        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        lblUsernameHome.setText(loggedInUser.getUser().getUsername());
        //userAvatar.setImage(new Image(loggedInUser.getUser().getAvatarURL()));
        createCurrentUserLibraryFromDB();
        addCurrentUserLibraryToHomeScreen();
    }

    public void setMainController(MainPaneController mainPaneController) {
        this.mainPaneController = mainPaneController;
    }

    @FXML
    void addCurrentUserLibraryToHomeScreen() {

        ObservableList<LibraryPlantPane> plantpane = FXCollections.observableArrayList();
        if(currentUserLibrary==null) {
            plantpane.add(new LibraryPlantPane());
        }
        else {
            for (DBPlant plant : currentUserLibrary) {
                plantpane.add(new LibraryPlantPane(this, "resources/images/sapling_in_pot.png", plant));
            }
        }
        Platform.runLater(() ->userPlantLibrary.setItems(plantpane));
    }

    @FXML
    void createCurrentUserLibraryFromDB() {

        Thread getLibraryThread = new Thread(() -> {
        Message getLibrary = new Message("getLibrary", LoggedInUser.getInstance().getUser());
        ClientConnection connection = new ClientConnection();
        Message response = connection.makeRequest(getLibrary);

        if (response.isSuccess()) {
            currentUserLibrary = response.getPlantLibrary();
            addCurrentUserLibraryToHomeScreen();
        } else {
            Platform.runLater(() ->MessageBox.display("Fail", "Failed to get library from database"));
        }
        });
        getLibraryThread.start();
    }
    @FXML
    public void removePlantFromDatabase(DBPlant plant) {

        Thread removePlantThread = new Thread(() -> {
        Message deletePlant = new Message("deletePlantFromLibrary", LoggedInUser.getInstance().getUser(), plant);
        ClientConnection connection = new ClientConnection();
        Message response = connection.makeRequest(deletePlant);

        if (response.isSuccess()) {
            createCurrentUserLibraryFromDB();
        } else {
            Platform.runLater(() ->MessageBox.display("Error", "Could not delete plant"));
        }
        });
        removePlantThread.start();
    }
    @FXML
    public void addPlantToCurrentUserLibrary(APIPlant plantAdd, String plantNickname) {

        int plantsWithThisNickname = 1;
        for (DBPlant plant : currentUserLibrary) {
            if (plant.getNickname().equals(plantNickname)) {
                plantsWithThisNickname++;
            }
        }
        if (plantsWithThisNickname > 1) {
            plantNickname = plantNickname + plantsWithThisNickname;
        }

        long currentDateMilli = System.currentTimeMillis();
        Date date = new Date(currentDateMilli);
        DBPlant plantToAdd = new DBPlant(plantNickname, plantAdd.getLinks().getPlant(), date);
        addPlantToDatabase(plantToAdd);
    }
    @FXML
    public void addPlantToDatabase(DBPlant plant) {

        Thread addPlantThread = new Thread(() -> {
        Message savePlant = new Message("savePlant", LoggedInUser.getInstance().getUser(), plant);
        ClientConnection connection = new ClientConnection();
        Message response = connection.makeRequest(savePlant);
        if (response.isSuccess()) {
            createCurrentUserLibraryFromDB();
        } else {
            Platform.runLater(() ->MessageBox.display("Fail", "Failed to add to database"));
        }
        });
        addPlantThread.start();
    }
    @FXML private void logoutButtonPressed() throws IOException {

        mainPaneController.logoutButtonPressed();
    }
}
