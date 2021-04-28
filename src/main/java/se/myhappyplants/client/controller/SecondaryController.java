package se.myhappyplants.client.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.view.LibraryPlantPane;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.client.view.SearchPlantPane;
import se.myhappyplants.server.model.repository.PlantRepository;
import se.myhappyplants.shared.APIPlant;
import se.myhappyplants.shared.DBPlant;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controls the inputs from a 'logged in' user
 *
 * Created by: Christopher O'Driscoll, Eric Simonsson
 * Updated by: Linn Borgström, Eric Simonsson, Susanne Vikström, 2021-04-21
 */
public class SecondaryController {

  private ClientConnection connection;
  public ArrayList<DBPlant> currentUserLibrary;



  @FXML
  Label lblUsernameHome; //vi borde ändra namn
  @FXML
  Label lblUsernameSearch;
  @FXML
  Label lblUsernameSettings;
  @FXML
  TextField txtFldSearchText;
  @FXML
  ListView resultPane;
  @FXML
  ProgressIndicator progressIndicator;
  @FXML
  ImageView imageViewImageUrl;
  @FXML
  ListView userPlantLibrary;
  @FXML
  PasswordField deleteAccountPassField;

  /**
   * Constructor that has access to FXML variables
   */
  @FXML
  public void initialize() {

    LoggedInUser loggedInUser = LoggedInUser.getInstance();
    lblUsernameHome.setText(loggedInUser.getUser().getUsername());
    lblUsernameSearch.setText(loggedInUser.getUser().getUsername());
    lblUsernameSettings.setText(loggedInUser.getUser().getUsername());

    //Gets users plant library

    createCurrentUserLibraryFromDB();
    addCurrentUserLibraryToHomeScreen();

    //userAvatar.setImage(new Image(loggedInUser.getUser().getAvatarURL()));


    //populateListView(testPlantArray());
  }

  /**
   * Default constructor, probably unnecessary
   *
   * @param connection
   */
  public void SecondaryController(ClientConnection connection) {
    this.connection = connection;
  }

  /**
   * Switches to 'logged out' scene
   *
   * @throws IOException
   */
  @FXML
  private void switchToPrimary() throws IOException {
    StartClient.setRoot("primary");
  }

  /**
   * Logs out user, then switches scenes
   *
   * @throws IOException
   */
  @FXML
  private void logoutButtonPressed() throws IOException {

    //ToDo - Some code to handle what happens when user wants to log out
    String email = LoggedInUser.getInstance().getUser().getEmail();

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/lastLogin.txt"))) {
      bw.write(email);
      bw.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

    LoggedInUser.getInstance().setUser(null);

    switchToPrimary();
  }

  @FXML
  private void searchButtonPressed() {
    Message apiRequest = new Message("search", txtFldSearchText.getText());
    progressIndicator.setProgress(25);
    Message apiResponse = ClientConnection.getInstance().makeRequest(apiRequest);

    if (apiResponse != null) {
      if (apiResponse.isSuccess()) {
        progressIndicator.setProgress(50);
        showResultsOnPane(apiResponse);
      } else {

        MessageBox.display("No results", "No results on " + txtFldSearchText.getText() + ", sorry!");

      }
    } else {
      MessageBox.display("No response", "No response from the server");
    }
  }

  private void showResultsOnPane(Message apiResponse) {
    progressIndicator.setProgress(75);
    ArrayList<APIPlant> searchedPlant = apiResponse.getPlantList();

    ObservableList<SearchPlantPane> searchPlantPanes = FXCollections.observableArrayList();
    for(APIPlant plant: searchedPlant) {

        searchPlantPanes.add(new SearchPlantPane(this,new File("resources/images/img.png").toURI().toString()/*String.valueOf(plant.image_url)*/, plant));

    }
    resultPane.setItems(searchPlantPanes);
    progressIndicator.setProgress(100);
    Thread imageThread = new Thread() {
        @Override
        public void run() {
            for(SearchPlantPane spp: searchPlantPanes) {
                APIPlant apiPlant = spp.getApiPlant();
                if (apiPlant.image_url == null) {
                   spp.setDefaultImage(new File("resources/images/Grn_vxt.png").toURI().toString());
                } else {
                    spp.updateImage();
                }
            }
        }
    };
    imageThread.start();
      //resultPane.setItems(searchPlantPanes);
  }
  private void addCurrentUserLibraryToHomeScreen() {
    //Add a Pane for each plant

    //todo Adda varje planta i currentUserLibrary till hemskärmen på separata anchorpanes
    ObservableList<LibraryPlantPane> plantpane = FXCollections.observableArrayList();
    for (DBPlant plant: currentUserLibrary) {
      plantpane.add(new LibraryPlantPane(this, "resources/images/sapling_in_pot.png", 0.5, plant));
    }
    userPlantLibrary.setItems(plantpane);
  }


    private void createCurrentUserLibraryFromDB() {
        //TODO: Hämta plantor som tillhör currentuser från databasen och lägg dom i currentUserLibrary
        Message getLibrary = new Message("getLibrary", LoggedInUser.getInstance().getUser());
        Message response = ClientConnection.getInstance().makeRequest(getLibrary);

        if (response.isSuccess()) {
            currentUserLibrary = response.getPlantLibrary();
        } else {
            MessageBox.display("Fail", "Failed to add to database");
        }


    }


  private void updateDatabaseWithCurrentUserLibrary() {
    //TODO: Uppdatera databasen med senaste currentUserLibrary. Denna anropas när applikationen stängs ner

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

    public void removePlantFromDatabase(DBPlant plant) {
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
  public void addPlantToCurrentUserLibrary(APIPlant plantAdd) {
    //Add to GUI
    //APIPlant selectedPlant = (APIPlant) resultPane.getSelectionModel().getSelectedItem();
    String plantNickname = plantAdd.common_name;

    int answer = MessageBox.askYesNo("Want to add a nickname?", "Do you want to add a nickname for your plant?");
    if (answer == 1) {
      plantNickname = MessageBox.askForStringInput("Add a nickname", "What do you want to call your plant?");
    }

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


    //Add to library
//    DBPlant plantToAdd = new DBPlant(selectedPlant.common_name, selectedPlant.getLinks().getPlant(), null);
  }

  /**
   * author: Frida Jacobsson
   * @throws IOException
   */
  @FXML
  private void deleteAccountButtonPressed() throws IOException {
    int answer = MessageBox.askYesNo("Delete account", "Are you sure you want to delete your account? \n All your personal information will be deleted. \nA deleted account can't be restored. ");
    if (answer == 1) {
      Message deleteMessage = new Message("delete account", new User(LoggedInUser.getInstance().getUser().getEmail(), deleteAccountPassField.getText()));
      Message deleteResponse = ClientConnection.getInstance().makeRequest(deleteMessage);
      if (deleteResponse != null) {
        if (deleteResponse.isSuccess()) {
          MessageBox.display("Account deleted successfully", "Sorry to see you go");
          logoutButtonPressed();
        } else {
          MessageBox.display("Failed to delete account", "Invalid password");
        }
      } else {
        MessageBox.display("Failed to delete account", "No contact with server");
      }
    }
  }
}