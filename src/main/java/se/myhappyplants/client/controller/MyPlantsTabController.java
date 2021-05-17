package se.myhappyplants.client.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se.myhappyplants.client.model.BoxTitle;
import se.myhappyplants.client.service.ClientConnection;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.model.PictureRandomizer;
import se.myhappyplants.client.view.LibraryPlantPane;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.shared.MessageType;
import se.myhappyplants.shared.Plant;
import se.myhappyplants.shared.Message;
import se.myhappyplants.client.model.SetAvatar;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

/**
 * Controller with logic used by the "Home" tab
 * Created by:
 * Updated by: Linn Borgström, 2021-05-13
 */
public class MyPlantsTabController {

    private ArrayList<Plant> currentUserLibrary;

    @FXML private MainPaneController mainPaneController;

    @FXML private Label lblUsernameMyPlants;

    @FXML private ImageView imgUserPicture;

    @FXML private ListView lstViewUserPlantLibrary;

    @FXML private ListView lstViewNotifications;


    @FXML
    public void initialize() {
        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        lblUsernameMyPlants.setText(loggedInUser.getUser().getUsername());
        //imgUserPicture.setImage(new Image(loggedInUser.getUser().getAvatarURL()));
        imgUserPicture.setImage(new Image(SetAvatar.setAvatarOnLogin(loggedInUser.getUser().getEmail())));
        createCurrentUserLibraryFromDB();
        addCurrentUserLibraryToHomeScreen();

    }

    public void setMainController(MainPaneController mainPaneController) {
        this.mainPaneController = mainPaneController;
    }

    @FXML
    public void addCurrentUserLibraryToHomeScreen() {
        ObservableList<LibraryPlantPane> obsListLibraryPlantPane = FXCollections.observableArrayList();
        if (currentUserLibrary == null) {
            obsListLibraryPlantPane.add(new LibraryPlantPane());
        } else {
            for (Plant plant : currentUserLibrary) {
                obsListLibraryPlantPane.add(new LibraryPlantPane(this, PictureRandomizer.getRandomPicture(), plant));
            }
        }
        Platform.runLater(() -> lstViewUserPlantLibrary.setItems(obsListLibraryPlantPane));
    }



    public void showNotifications () {
        ObservableList<String> notificationStrings = FXCollections.observableArrayList();
        if (LoggedInUser.getInstance().getUser().areNotificationsActivated()) {
            int plantsThatNeedWater = 0;
            for (Plant plant : currentUserLibrary) {
                if (plant.getProgress() < 0.25) {
                    plantsThatNeedWater++;
                    notificationStrings.add(plant.getNickname() + " needs water");
                }
            }
            if (plantsThatNeedWater == 0) {
                notificationStrings.add("All your plants are watered");
            }
        }
        else {
            notificationStrings.add("");
        }
        Platform.runLater(() -> lstViewNotifications.setItems(notificationStrings));
    }
    @FXML
    public void createCurrentUserLibraryFromDB() {
        Thread getLibraryThread = new Thread(() -> {
            Message getLibrary = new Message(MessageType.getLibrary, LoggedInUser.getInstance().getUser());
            ClientConnection connection = new ClientConnection();
            Message response = connection.makeRequest(getLibrary);

            if (response.isSuccess()) {
                currentUserLibrary = response.getPlantLibrary();
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
        Thread removePlantThread = new Thread(() -> {
            currentUserLibrary.remove(plant);
            addCurrentUserLibraryToHomeScreen();
            Message deletePlant = new Message(MessageType.deletePlantFromLibrary, LoggedInUser.getInstance().getUser(), plant);
            ClientConnection connection = new ClientConnection();
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
        Plant plantToAdd = new Plant(uniqueNickName, selectedPlant.getPlantId(), date);
        addPlantToDB(plantToAdd);
    }

    @FXML
    public void addPlantToDB(Plant plant) {
        Thread addPlantThread = new Thread(() -> {
            currentUserLibrary.add(plant);
            addCurrentUserLibraryToHomeScreen();
            Message savePlant = new Message(MessageType.savePlant, LoggedInUser.getInstance().getUser(), plant);
            ClientConnection connection = new ClientConnection();
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
        Message response = new ClientConnection().makeRequest(changeLastWatered);
        if (!response.isSuccess()) {
            MessageBox.display(BoxTitle.Failed, "The connection to the server has failed. Check your connection and try again.");
        }
        createCurrentUserLibraryFromDB();
    }

    /**
     * @param plant
     * @param newNickname
     * @return
     */
    public boolean changeNicknameInDB(Plant plant, String newNickname) {
        Message changeNicknameInDB = new Message(MessageType.changeNickname, LoggedInUser.getInstance().getUser(), plant, newNickname);
        Message response = new ClientConnection().makeRequest(changeNicknameInDB);
        if (!response.isSuccess()) {
            MessageBox.display(BoxTitle.Failed, "The connection to the server has failed. Check your connection and try again.");
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
