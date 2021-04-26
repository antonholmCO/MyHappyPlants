package se.myhappyplants.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.view.LibraryPlantPane;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.shared.APIPlant;
import se.myhappyplants.shared.DBPlant;
import se.myhappyplants.shared.Message;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public class HomeTabController {

    private ArrayList<DBPlant> currentUserLibrary;

    @FXML private SecondaryController secondaryController = new SecondaryController();

    @FXML private Label lblUsernameHome;
    @FXML private ListView userPlantLibrary;

    @FXML public void initialize() {

        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        lblUsernameHome.setText(loggedInUser.getUser().getUsername());
        //userAvatar.setImage(new Image(loggedInUser.getUser().getAvatarURL()));

        createCurrentUserLibraryFromDB();
        addCurrentUserLibraryToHomeScreen();
    }

    void addCurrentUserLibraryToHomeScreen() {
        //Add a Pane for each plant

        //todo Adda varje planta i currentUserLibrary till hemskärmen på separata anchorpanes
        ObservableList<LibraryPlantPane> plantpane = FXCollections.observableArrayList();
        for (DBPlant plant: currentUserLibrary) {
            plantpane.add(new LibraryPlantPane(this, "resources/images/sapling_in_pot.png", plant));
        }
        userPlantLibrary.setItems(plantpane);
    }


    void createCurrentUserLibraryFromDB() {
        //TODO: Hämta plantor som tillhör currentuser från databasen och lägg dom i currentUserLibrary
        Message getLibrary = new Message("getLibrary", LoggedInUser.getInstance().getUser());
        Message response = ClientConnection.getInstance().makeRequest(getLibrary);

        if (response.isSuccess()) {
            currentUserLibrary = response.getPlantLibrary();
        } else {
            MessageBox.display("Fail", "Failed to add to database");
        }
    }
    public void removePlantFromDatabase(DBPlant plant) {
        System.out.println("hej");
        Message deletePlant = new Message("deletePlantFromLibrary", LoggedInUser.getInstance().getUser(), plant);
        Message response = ClientConnection.getInstance().makeRequest(deletePlant);

        if (response.isSuccess()) {
            createCurrentUserLibraryFromDB();
            addCurrentUserLibraryToHomeScreen();
        } else {
            MessageBox.display("Error", "Could not delete plant");
        }
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

    public void addPlantToDatabase(DBPlant plant) {

        Message savePlant = new Message("savePlant", LoggedInUser.getInstance().getUser(), plant);
        Message response = ClientConnection.getInstance().makeRequest(savePlant);
        if (response.isSuccess()) {
            createCurrentUserLibraryFromDB();
            addCurrentUserLibraryToHomeScreen();
        } else {
            MessageBox.display("Fail", "Failed to add to database");
        }

    }

    @FXML private void logoutButtonPressed() throws IOException {

        secondaryController.logoutButtonPressed();
    }
}
