package se.myhappyplants.client.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.client.view.SearchPlantPane;
import se.myhappyplants.shared.APIPlant;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.User;

/**
 * Controls the inputs from a 'logged in' user
 *
 * Created by: Christopher O'Driscoll, Eric Simonsson
 * Updated by: Linn Borgström, Eric Simonsson, Susanne Vikström, 2021-04-21
 */
public class SecondaryController {

  @FXML private HomeTabController homeTabController;
  @FXML private PlantsTabController plantsTabController;
  @FXML private SettingsTabController settingsTabController;

  /**
   * Constructor that has access to FXML variables
   */
  @FXML
  public void initialize() {

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
    StartClient.setRoot("primary");
  }


}