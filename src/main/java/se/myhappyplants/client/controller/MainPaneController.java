package se.myhappyplants.client.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.model.RootName;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Controls the inputs from a 'logged in' user and is the mainPane for the GUI
 *
 * Created by: Christopher O'Driscoll, Eric Simonsson
 * Updated by: Linn Borgström, Eric Simonsson, Susanne Vikström, 2021-04-21
 */
public class MainPaneController {

    @FXML
    private MyPlantsTabController homePaneController;
    @FXML
    private SearchTabController plantsPaneController;
    @FXML
    private SettingsTabController settingsPaneController;

    /**
     * Constructor that has access to FXML variables
     */
    @FXML
    public void initialize() {
        homePaneController.setMainController(this);
        plantsPaneController.setMainController(this);
        settingsPaneController.setMainController(this);
    }

    public MyPlantsTabController getHomePaneController() {
        return homePaneController;
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
        homePaneController.updateAvatar();
        plantsPaneController.updateAvatar();
        settingsPaneController.updateAvatar();
    }


}