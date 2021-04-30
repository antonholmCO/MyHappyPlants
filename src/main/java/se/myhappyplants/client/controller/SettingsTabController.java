package se.myhappyplants.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.ArrayList;

public class SettingsTabController {

    @FXML private BorderPane settingsPane;
    @FXML private MainPaneController mainPaneController;
    @FXML private TabPane secondaryPane;
    @FXML private ImageView imgUserPicture;
    @FXML private Label lblUsernameSettings;
    @FXML private PasswordField deleteAccountPassField;


    @FXML public void initialize() {

        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        lblUsernameSettings.setText(loggedInUser.getUser().getUsername());
        imgUserPicture.setImage(new Image(loggedInUser.getUser().getAvatarURL()));
    }

    public void setSecondaryController (MainPaneController mainPaneController) {
        this.mainPaneController = mainPaneController;
    }

    /**
     * author: Frida Jacobsson
     * @throws IOException
     */
    @FXML private void deleteAccountButtonPressed() throws IOException {
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

    @FXML
    private void logoutButtonPressed() throws IOException {

        mainPaneController.logoutButtonPressed();

    }

    public void updateAvatar() {
        imgUserPicture.setImage(new Image(LoggedInUser.getInstance().getUser().getAvatarURL()));
    }

    /**
     * User selects a picture from their computer. The application copies it into
     * the resources/images/user_avatars folder and renames it to "[users email]_avatar".
     * Then the application sets stored picture to profile picture
     *
     * @author Anton
     */
    @FXML
    private void selectPicture() {
        User user = LoggedInUser.getInstance().getUser();
        ArrayList<String> allowedExtensions = new ArrayList<>();
        FileChooser fc = new FileChooser();

        allowedExtensions.add(".jpg");
        allowedExtensions.add(".jpeg");
        allowedExtensions.add(".png");

        File selectedImage = fc.showOpenDialog(null);

        if (selectedImage != null) {
            String imagePath = selectedImage.toString();
            String imageExtension = imagePath.substring(imagePath.indexOf("."));

            if (allowedExtensions.contains(imageExtension)) {
                try {
                    // Kopierar in filen i foldern, om den kastar FileAlreadyExistsException, ta bort filen och kopiera igen
                    try {
                        Files.copy(selectedImage.toPath(), new File("resources/images/user_avatars/" + user.getEmail() + "_avatar" + imageExtension).toPath());
                    } catch (FileAlreadyExistsException e) {
                        Files.delete(new File("resources/images/user_avatars/" + user.getEmail() + "_avatar" + imageExtension).toPath());
                        Files.copy(selectedImage.toPath(), new File("resources/images/user_avatars/" + user.getEmail() + "_avatar" + imageExtension).toPath());
                    }

                    //Skriver full filepath till en txt i samma folder (Gör detta pga att eftersom vi har olika fileextensions kan vi inte hitta filen utan veta extension. Bestämmer vi att man endast
                    // tar tex jpg filer så fungerar det. Eller om vi kan komma på någon smartare lösning)

                    try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/images/user_avatars/" + user.getEmail() + "_avatar.txt"))) {
                        bw.write("resources/images/user_avatars/" + user.getEmail() + "_avatar" + imageExtension);
                        bw.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //Sätter avataren i current session
                    user.setAvatar("resources/images/user_avatars/" + user.getEmail() + "_avatar" + imageExtension);
                    mainPaneController.updateAvatarOnAllTabs();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                String wrongExtensionMessage = String.format("You can only choose files of the following format: %s, %s, %s\nTry again",
                                                            allowedExtensions.get(0),
                                                            allowedExtensions.get(1),
                                                            allowedExtensions.get(2));

                MessageBox.display("Wrong type of file", wrongExtensionMessage);
            }
        }
    }
}
