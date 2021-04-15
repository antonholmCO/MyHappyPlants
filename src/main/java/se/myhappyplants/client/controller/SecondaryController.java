package se.myhappyplants.client.controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.view.LibraryPlantPane;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.shared.APIPlant;
import se.myhappyplants.shared.DBPlant;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.PlantLibrary;

/**
 * Controls the inputs from a 'logged in' user
 *
 * @author Christopher O'Driscoll
 * @author Eric Simonsson
 */
public class SecondaryController {

  private ClientConnection connection;
  private PlantLibrary currentUserLibrary;


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
    currentUserLibrary = LoggedInUser.getInstance().getUser().getPlantLibrary();
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

    try(BufferedWriter bw = new BufferedWriter(new FileWriter("resources/lastLogin.txt"))){
      bw.write(email);
      bw.flush();
    }
    catch (IOException e){
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

        MessageBox.display("No results", "No results on "+txtFldSearchText.getText() +", sorry!");

      }
    } else {
      MessageBox.display("No response", "No response from the server");
    }
  }

  private void showResultsOnPane(Message apiResponse) {
    progressIndicator.setProgress(75);
    ArrayList<APIPlant> searchedPlant = apiResponse.getPlantList();
    ObservableList<APIPlant> items = FXCollections.observableArrayList ();
    for(APIPlant plant: searchedPlant) {
//      arrayItem.add(plant);
      items.add(plant);
      //Image image = new Image(String.valueOf(plant.getImage_url()));
      //imageViewImageUrl.setImage(image);
    }
    resultPane.setItems(items);
    progressIndicator.setProgress(100);
  }


  private void createCurrentUserLibraryFromDB() {
    //TODO: Hämta plantor som tillhör currentuser från databasen och lägg dom i currentUserLibrary
  }

  private void addCurrentUserLibraryToHomeScreen() {
    //TODO: Adda varje planta i currentUserLibrary till hemskärmen på separata anchorpanes
  }

  private void updateDatabaseWithCurrentUserLibrary() {
    //TODO: Uppdatera databasen med senaste currentUserLibrary. Denna anropas när applikationen stängs ner
  }

  @FXML
  private void addPlantToCurrentUserLibrary() {
    //Add to GUI
    APIPlant selectedPlant = (APIPlant) resultPane.getSelectionModel().getSelectedItem();
    ObservableList<APIPlant> plants = FXCollections.observableArrayList();
    plants.add(selectedPlant);
    userPlantLibrary.setItems(plants);

    //Add a Pane for the plant
    ObservableList<LibraryPlantPane> plantpane = FXCollections.observableArrayList();
    plantpane.add(new LibraryPlantPane(null, "Erics blomma", 0.5, new DBPlant("Erics blomma", null, null)));
    userPlantLibrary.setItems(plantpane);

    //Add to library
//    DBPlant plantToAdd = new DBPlant(selectedPlant.common_name, selectedPlant.getLinks().getPlant(), null);
//    currentUserLibrary.addPlantToLibrary(plantToAdd);

  }
}