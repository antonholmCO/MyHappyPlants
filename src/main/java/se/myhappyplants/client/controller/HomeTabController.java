package se.myhappyplants.client.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.view.LibraryPlantPane;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.shared.APIPlant;
import se.myhappyplants.shared.DBPlant;
import se.myhappyplants.shared.Message;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

/**
 * Controller with logic used by the "Home" tab
 */
public class HomeTabController {

    private ArrayList<DBPlant> currentUserLibrary;

    @FXML
    private MainPaneController mainPaneController;

    @FXML
    private Label lblUsernameHome;

    @FXML
    private ImageView imgUserPicture;

    @FXML
    private ListView userPlantLibrary;

    @FXML
    public void initialize() {
        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        lblUsernameHome.setText(loggedInUser.getUser().getUsername());
        imgUserPicture.setImage(new Image(loggedInUser.getUser().getAvatarURL()));

        createCurrentUserLibraryFromDB();
        addCurrentUserLibraryToHomeScreen();

    }

    public void setMainController(MainPaneController mainPaneController) {
        this.mainPaneController = mainPaneController;
    }

    @FXML
    public void addCurrentUserLibraryToHomeScreen() {
        ObservableList<LibraryPlantPane> plantPane = FXCollections.observableArrayList();
        if (currentUserLibrary == null) {
            plantPane.add(new LibraryPlantPane());
        } else {
            for (DBPlant plant : currentUserLibrary) {
                plantPane.add(new LibraryPlantPane(this, getRandomImagePath(), plant));
            }
        }
        Platform.runLater(() -> userPlantLibrary.setItems(plantPane));
    }

    /**
     * Method that generated a random path to a image of a flower
     *
     * @return
     */
    private String getRandomImagePath() {
        Random random = new Random();
        switch (1 + random.nextInt(8)) {
            case 1:
                return "resources/images/blomma2.jpg";
            case 2:
                return "resources/images/blomma5.jpg";
            case 3:
                return "resources/images/blomma6.jpg";
            case 4:
                return "resources/images/blomma9.jpg";
            case 5:
                return "resources/images/blomma10.jpg";
            case 6:
                return "resources/images/blomma17.jpg";
            case 7:
                return "resources/images/blomma18.jpg";
            case 8:
                return "resources/images/blomma19.jpg";
            default:
                return "resources/images/blomma21.jpg";
        }
    }

    @FXML
    public void createCurrentUserLibraryFromDB() {
        Thread getLibraryThread = new Thread(() -> {
            Message getLibrary = new Message("getLibrary", LoggedInUser.getInstance().getUser());
            ClientConnection connection = new ClientConnection();
            Message response = connection.makeRequest(getLibrary);

            if (response.isSuccess()) {
                currentUserLibrary = response.getPlantLibrary();
                addCurrentUserLibraryToHomeScreen();
            } else {
                Platform.runLater(() -> MessageBox.display("Fail", "Failed to get library from database"));
            }
        });
        getLibraryThread.start();
    }

    @FXML
    public void removePlantFromDatabase(DBPlant plant) {
        Thread removePlantThread = new Thread(() -> {
            currentUserLibrary.remove(plant);
            addCurrentUserLibraryToHomeScreen();
            Message deletePlant = new Message("deletePlantFromLibrary", LoggedInUser.getInstance().getUser(), plant);
            ClientConnection connection = new ClientConnection();
            Message response = connection.makeRequest(deletePlant);
            if (!response.isSuccess()) {
                Platform.runLater(() -> MessageBox.display("Error", "Could not delete plant"));
                createCurrentUserLibraryFromDB();
            }
        });
        removePlantThread.start();
    }

    @FXML
    public void addPlantToCurrentUserLibrary(APIPlant plantAdd, String plantNickname) {
        int plantsWithThisNickname = 1;
        String nonDuplicatePlantNickname = plantNickname;
        for (DBPlant plant : currentUserLibrary) {
            if (plant.getNickname().equals(nonDuplicatePlantNickname)) {
                plantsWithThisNickname++;
                nonDuplicatePlantNickname = plantNickname + plantsWithThisNickname;
            }
        }
        long currentDateMilli = System.currentTimeMillis();
        Date date = new Date(currentDateMilli);
        DBPlant plantToAdd = new DBPlant(nonDuplicatePlantNickname, plantAdd.getLinks().getPlant(), date);
        addPlantToDatabase(plantToAdd);
    }

    @FXML
    public void addPlantToDatabase(DBPlant plant) {
        Thread addPlantThread = new Thread(() -> {
            currentUserLibrary.add(plant);
            addCurrentUserLibraryToHomeScreen();
            Message savePlant = new Message("savePlant", LoggedInUser.getInstance().getUser(), plant);
            ClientConnection connection = new ClientConnection();
            Message response = connection.makeRequest(savePlant);
            if (!response.isSuccess()) {
                Platform.runLater(() -> MessageBox.display("Fail", "Failed to add to database"));
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
    public void changeLastWateredInDB(DBPlant plant, LocalDate date) {
        Message changeLastWatered = new Message("changeLastWatered", LoggedInUser.getInstance().getUser(), plant, date);
        Message response = new ClientConnection().makeRequest(changeLastWatered);
        if (!response.isSuccess()) {
            MessageBox.display("Fail", "Something went wrong trying to change date");
        }
    }

    /**
     * @param plant
     * @param newNickname
     * @return
     */
    public boolean changeNicknameInDB(DBPlant plant, String newNickname) {
        Message changeNicknameInDB = new Message("changeNickname", LoggedInUser.getInstance().getUser(), plant, newNickname);
        Message response = new ClientConnection().makeRequest(changeNicknameInDB);
        if (!response.isSuccess()) {
            MessageBox.display("Fail", "Something went wrong trying to change nickname");
            return false;
        } else {
            plant.setNickname(newNickname);
            return true;
        }
    }

    public void updateAvatar() {
        imgUserPicture.setImage(new Image(LoggedInUser.getInstance().getUser().getAvatarURL()));
    }
}
