package se.myhappyplants.client.controller;

import javafx.fxml.FXML;
import se.myhappyplants.client.model.LoggedInUser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Controls the inputs from a 'logged in' user
 * <p>
 * Created by: Christopher O'Driscoll, Eric Simonsson
 * Updated by: Linn Borgström, Eric Simonsson, Susanne Vikström, 2021-04-21
 */
public class MainPaneController {

    @FXML
    private MyPlantsTabController homePaneController;
    @FXML
    private PlantsTabController plantsPaneController;
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
     * Logs out user, then switches scenes
     *
     * @throws IOException
     */
    @FXML
    public void logoutButtonPressed() throws IOException {
        String email = LoggedInUser.getInstance().getUser().getEmail();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/lastLogin.txt"))) {
            bw.write(email);
            bw.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        LoggedInUser.getInstance().setUser(null);
        StartClient.setRoot("loginPane");
    }

    public void updateAvatarOnAllTabs() {
        homePaneController.updateAvatar();
        plantsPaneController.updateAvatar();
        settingsPaneController.updateAvatar();
    }

    public PlantsTabController getPlantsTabController() {
        return plantsPaneController;
    }
}