package se.myhappyplants.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.model.RootName;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Controls the inputs from a 'logged in' user and is the mainPane for the GUI
 * Created by: Christopher O'Driscoll, Eric Simonsson
 * Updated by: Linn Borgström, Eric Simonsson, Susanne Vikström, 2021-04-21
 */
public class MainPaneController {

    @FXML public TabPane mainPane;

    @FXML private MyPlantsTabPaneController myPlantsTabPaneController;
    @FXML private SearchTabPaneController searchTabPaneController;
    @FXML private SettingsTabPaneController settingsTabPaneController;

    /**
     * Constructor that has access to FXML variables
     */
    @FXML
    public void initialize() {
        myPlantsTabPaneController.setMainController(this);
        searchTabPaneController.setMainController(this);
        settingsTabPaneController.setMainController(this);
    }

    /**
     * Getter-method to get the myPlantsTabPaneController
     * @return MyPlantsTabPaneController
     */

    public MyPlantsTabPaneController getMyPlantsTabPaneController() {
        return myPlantsTabPaneController;
    }

    /**
     * Getter-method to get the searchTabPaneController
     * @return searchTabPaneController
     */
    public SearchTabPaneController getSearchTabPaneController() {
        return searchTabPaneController;
    }

    /**
     * Method to logs out the user and then switches scenes to the loginPane
     *
     * @throws IOException
     */
    @FXML
    public void logoutButtonPressed() throws IOException {
        String email = LoggedInUser.getInstance().getUser().getEmail();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/lastLogin.txt"))) {
            bw.write(email);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoggedInUser.getInstance().setUser(null);
        StartClient.setRoot(String.valueOf(RootName.loginPane));
    }

    /**
     * Method to update so the user picture is the same on all the tabs
     */
    public void updateAvatarOnAllTabs() {
        myPlantsTabPaneController.updateAvatar();
        searchTabPaneController.updateAvatar();
        settingsTabPaneController.updateAvatar();
    }

    /**
     * Method to switch to the tab the user selects
     */
    public void changeToSearchTab() {
        mainPane.getSelectionModel().select(1);
    }
}