package se.myhappyplants.client.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import se.myhappyplants.client.model.BoxTitle;
import se.myhappyplants.client.service.ClientConnection;
import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.client.view.ButtonText;
import se.myhappyplants.client.view.MessageBox;
import se.myhappyplants.client.view.PopupBox;
import se.myhappyplants.shared.Message;
import se.myhappyplants.shared.MessageType;
import se.myhappyplants.client.model.SetAvatar;
import se.myhappyplants.shared.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

public class SettingsTabController {

    @FXML private ToggleButton tglBtnChangeNotification;
    @FXML private MainPaneController mainPaneController;
    @FXML private ImageView imgViewUserPicture;
    @FXML private Label lblUserName;
    @FXML private PasswordField passFldDeleteAccount;

    @FXML
    public void initialize() {
        User loggedInUser = LoggedInUser.getInstance().getUser();
        lblUserName.setText(loggedInUser.getUsername());
        //imgViewUserPicture.setImage(new Image(loggedInUser.getAvatarURL()));
        imgViewUserPicture.setImage(new Image(SetAvatar.setAvatarOnLogin(loggedInUser.getEmail())));
        tglBtnChangeNotification.setSelected(loggedInUser.areNotificationsActivated());
        ButtonText.setNotificationsButtonText(this);
        //setNotificationsButtonText();

    }
    public ToggleButton getTglBtnChangeNotification() {
        return tglBtnChangeNotification;
    }

    public void setMainController(MainPaneController mainPaneController) {
        this.mainPaneController = mainPaneController;
    }

    @FXML
    public void changeNotificationsSetting() {
        Thread changeNotificationsThread = new Thread(() -> {
            Message notificationRequest = new Message(MessageType.changeNotifications, tglBtnChangeNotification.isSelected(), LoggedInUser.getInstance().getUser());
            ClientConnection connection = new ClientConnection();
            Message notificationResponse = connection.makeRequest(notificationRequest);
            if(notificationResponse != null) {
                if(notificationResponse.isSuccess()) {
                    LoggedInUser.getInstance().getUser().setIsNotificationsActivated(tglBtnChangeNotification.isSelected());
                    PopupBox popupBox = new PopupBox();
                    Platform.runLater(() -> popupBox.display("Notification settings changed"));
                } else {
                    Platform.runLater(() -> MessageBox.display(BoxTitle.Failed, "Settings could not be changed"));
                }
            } else {
                Platform.runLater(() -> MessageBox.display(BoxTitle.Failed, "The connection to the server has failed. Check your connection and try again."));
            }
        });
        changeNotificationsThread.start();
        ButtonText.setNotificationsButtonText(this);
        //setNotificationsButtonText();
        mainPaneController.getHomePaneController().createCurrentUserLibraryFromDB();

    }

    //TODO: Kolla med gruppen om de vill att denna logik ska flyttas! Eftersom den är kopplad med FXML-filen till denna controller+variabler.
    //TODO: Om ja: fixa så det blir rätt anrop på rad ca. 44 & 75(samma metodnamn som nedan)
    /*private void setNotificationsButtonText() {
        if(tglBtnChangeNotification.isSelected()) {
            tglBtnChangeNotification.setText("On");
        }
        else {
            tglBtnChangeNotification.setText("Off");
        }
    }*/

    /**
     * Method that handles actions when a user clicks button to delete account.
     */
    @FXML
    private void deleteAccountButtonPressed() {
        int answer = MessageBox.askYesNo(BoxTitle.Delete, "Are you sure you want to delete your account? \n All your personal information will be deleted. \nA deleted account can't be restored. ");
        if (answer == 1) {
            Thread deleteAccountThread = new Thread(() -> {
                Message deleteMessage = new Message(MessageType.deleteAccount, new User(LoggedInUser.getInstance().getUser().getEmail(), passFldDeleteAccount.getText()));
                ClientConnection connection = new ClientConnection();
                Message deleteResponse = connection.makeRequest(deleteMessage);
                if (deleteResponse != null) {
                    if (deleteResponse.isSuccess()) {
                        Platform.runLater(() -> MessageBox.display(BoxTitle.Success, "We are sorry to see you go"));
                        try {
                            logoutButtonPressed();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Platform.runLater(() -> MessageBox.display(BoxTitle.Failed, "The passwords you entered do not match"));
                    }
                } else {
                    Platform.runLater(() -> MessageBox.display(BoxTitle.Failed, "The connection to the server has failed. Check your connection and try again."));
                }
            });
            deleteAccountThread.start();
        }
    }

    @FXML
    private void logoutButtonPressed() throws IOException {
        mainPaneController.logoutButtonPressed();
    }

    public void updateAvatar() {
        imgViewUserPicture.setImage(new Image(LoggedInUser.getInstance().getUser().getAvatarURL()));
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
        FileChooser fc = new FileChooser();

        FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png");
        fc.getExtensionFilters().add(fileExtensions);

        File selectedImage = fc.showOpenDialog(null);

        if (selectedImage != null) {
            String imagePath = selectedImage.toString();
            String imageExtension = imagePath.substring(imagePath.indexOf("."));
            try {
                try {
                    Files.copy(selectedImage.toPath(), new File("resources/images/user_avatars/" + user.getEmail() + "_avatar" + imageExtension).toPath());
                }
                catch (FileAlreadyExistsException e) {
                    Files.delete(new File("resources/images/user_avatars/" + user.getEmail() + "_avatar" + imageExtension).toPath());
                    Files.copy(selectedImage.toPath(), new File("resources/images/user_avatars/" + user.getEmail() + "_avatar" + imageExtension).toPath());
                }

                try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/images/user_avatars/" + user.getEmail() + "_avatar.txt"))) {
                    bw.write("resources/images/user_avatars/" + user.getEmail() + "_avatar" + imageExtension);
                    bw.flush();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                user.setAvatar("resources/images/user_avatars/" + user.getEmail() + "_avatar" + imageExtension);
                mainPaneController.updateAvatarOnAllTabs();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
