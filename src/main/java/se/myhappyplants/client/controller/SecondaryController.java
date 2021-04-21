package se.myhappyplants.client.controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

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
import se.myhappyplants.server.model.repository.UserRepository;
import se.myhappyplants.shared.APIPlant;
import se.myhappyplants.shared.DBPlant;
import se.myhappyplants.shared.Message;

/**
 * Controls the inputs from a 'logged in' user
 *
 * @author Christopher O'Driscoll
 * @author Eric Simonsson
 */
public class SecondaryController {

  private ClientConnection connection;
  private ArrayList<DBPlant> currentUserLibrary;


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
    ObservableList<SearchPlantPane> searchPlantPanes = FXCollections.observableArrayList ();
    for(APIPlant plant: searchedPlant) {
//      arrayItem.add(plant);
      //items.add(plant);
      searchPlantPanes.add(new SearchPlantPane(this,plant));
      //Image image = new Image(String.valueOf(plant.getImage_url()));
      //imageViewImageUrl.setImage(image);
    }
    resultPane.setItems(searchPlantPanes);
    progressIndicator.setProgress(100);
  }
  private void addCurrentUserLibraryToHomeScreen() {
    //Add a Pane for each plant

    //todo Adda varje planta i currentUserLibrary till hemskärmen på separata anchorpanes
    ObservableList<LibraryPlantPane> plantpane = FXCollections.observableArrayList();
    for (DBPlant plant: currentUserLibrary) {
      plantpane.add(new LibraryPlantPane("resources/images/sapling_in_pot.png", 0.5, plant));
    }
    userPlantLibrary.setItems(plantpane);
  }

  private void createCurrentUserLibraryFromDB() {
    //TODO: Hämta plantor som tillhör currentuser från databasen och lägg dom i currentUserLibrary
    try {
      PlantRepository plantRepository = new PlantRepository(LoggedInUser.getInstance().getUser());
      currentUserLibrary = plantRepository.getAllPlants();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }


  }



  private void updateDatabaseWithCurrentUserLibrary() {
    //TODO: Uppdatera databasen med senaste currentUserLibrary. Denna anropas när applikationen stängs ner

  }

  private void addPlantToDatabase(DBPlant plant) {

    try {
      PlantRepository plantRepository= new PlantRepository(LoggedInUser.getInstance().getUser());
      if(plantRepository.savePlant(plant)) {
        createCurrentUserLibraryFromDB();
        addCurrentUserLibraryToHomeScreen();
      }
    else {
      MessageBox.display("Fail", "Failed to add to database");
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }
  private void removePlantFromDatabase(DBPlant plant) {

  }

  @FXML
  public void addPlantToCurrentUserLibrary() {
    //Add to GUI
    APIPlant selectedPlant = (APIPlant) resultPane.getSelectionModel().getSelectedItem();
    String plantNickname = selectedPlant.common_name;

    int answer = MessageBox.askYesNo("Want to add a nickname?", "Do you want to add a nickname for your plant?");
    if (answer == 1) {
      plantNickname = MessageBox.askForStringInput("Add a nickname", "What do you want to call your plant?");
    }

    int plantsWithThisNickname = 1;
    for (DBPlant plant: currentUserLibrary) {
      if (plant.getNickname().equals(plantNickname)) {
        plantsWithThisNickname++;
      }
    }
    if (plantsWithThisNickname>1) {
      plantNickname = plantNickname + plantsWithThisNickname;
    }

    DBPlant plantToAdd = new DBPlant(plantNickname, selectedPlant.getLinks().getPlant(), "2021-04-15");
    addPlantToDatabase(plantToAdd);

    //Add to library
//    DBPlant plantToAdd = new DBPlant(selectedPlant.common_name, selectedPlant.getLinks().getPlant(), null);


  }
}