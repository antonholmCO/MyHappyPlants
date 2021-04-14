package se.myhappyplants.client.controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.shared.APIPlant;
import se.myhappyplants.shared.Message;

/**
 * Controls the inputs from a 'logged in' user
 *
 * @author Christopher O'Driscoll
 * @author Eric Simonsson
 */
public class SecondaryController {

  private ClientConnection connection;


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

  /**
   * Constructor that has access to FXML variables
   */
  @FXML
  public void initialize() {

    LoggedInUser loggedInUser = LoggedInUser.getInstance();
    lblUsernameHome.setText(loggedInUser.getUser().getUsername());
    lblUsernameSearch.setText(loggedInUser.getUser().getUsername());
    lblUsernameSettings.setText(loggedInUser.getUser().getUsername());
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
        MessageBox.display("No results", "No results on "+txtFldSearchText +", sorry!");
      }
    } else {
      MessageBox.display("No response", "No response from the server");
    }
  }

  private void showResultsOnPane(Message apiResponse) {
    progressIndicator.setProgress(75);
    ArrayList<APIPlant> searchedPlant = apiResponse.getPlantList();
    ObservableList<String> items = FXCollections.observableArrayList ();
    for(APIPlant plant: searchedPlant) {
      items.add(plant.toString());
      //Image image = new Image(String.valueOf(plant.getImage_url()));
      //imageViewImageUrl.setImage(image);
    }
    resultPane.setItems(items);
    progressIndicator.setProgress(100);
  }
}