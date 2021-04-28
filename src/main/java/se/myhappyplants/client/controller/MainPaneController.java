package se.myhappyplants.client.controller;

import javafx.event.Event;
import javafx.event.WeakEventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import se.myhappyplants.client.model.LoggedInUser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Controls the inputs from a 'logged in' user
 *
 * Created by: Christopher O'Driscoll, Eric Simonsson
 * Updated by: Linn Borgström, Eric Simonsson, Susanne Vikström, 2021-04-21
 */
public class MainPaneController {

  @FXML private Tab homeTab;
  @FXML private Tab plantsTab;
  @FXML private Tab settingsTab;

  @FXML private HomeTabController homePaneController;
  @FXML private PlantsTabController plantsPaneController;
  @FXML private SettingsTabController settingsPaneController;

  /**
   * Constructor that has access to FXML variables
   */
  @FXML
  public void initialize() {
    homePaneController.setSecondaryController(this);
    plantsPaneController.setSecondaryController(this);
    settingsPaneController.setSecondaryController(this);
  }

  public HomeTabController getHomePaneController() {
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
    } catch (IOException e) {
      e.printStackTrace();
    }

    LoggedInUser.getInstance().setUser(null);
    StartClient.setRoot("loginPane");

  }


}