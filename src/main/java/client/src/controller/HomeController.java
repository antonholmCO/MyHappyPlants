import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class HomeController {

    @FXML
    public ListView<LibraryPlantPane> listView;
    public ImageView userAvatar;
    public Label lblUserName;

    /**
     * Constructor with access to FXML attributes
     */
    @FXML
    public void initialize() {

        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        lblUserName.setText(loggedInUser.getUser().getUserName());
        userAvatar.setImage(new Image(loggedInUser.getUser().getAvatarURL()));

        populateListView(testPlantArray());
    }

    private ArrayList<UserPlant> testPlantArray() {
        ArrayList<UserPlant> userPlants = new ArrayList<>();
        for(int i = 1; i<=10; i++) {
            userPlants.add(new UserPlant("Plant"+(i), "/blommaPNG/blomma"+(i)+".png", 86400000l*(i+1), new Date(System.currentTimeMillis())));
        }
        return userPlants;
    }

    /**
     * Switches back to login window
     * @param actionEvent
     * @throws IOException
     */
    public void changeToLogInScene(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent logInParent = FXMLLoader.load(getClass().getResource("LogInParent.fxml"));
        Scene logInScene = new Scene(logInParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(logInScene);
        window.show();
    }


    public void populateListView(ArrayList<UserPlant> userPlants) {

        ObservableList<LibraryPlantPane> libraryPlantPanes = FXCollections.observableArrayList();
        for (UserPlant p: userPlants) {
            libraryPlantPanes.add(new LibraryPlantPane(
                    new ImageView(p.getImageURL()),
                    new Label(p.getName()),
                    p.getProgress()));
        }
        listView.setItems(libraryPlantPanes);
        for (LibraryPlantPane lpp: libraryPlantPanes) {
            startProgressBar(lpp.getProgressBar(), new Random().nextInt(10)+1);
        }
    }
    public void startProgressBar(ProgressBar pb, int speed) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(10*speed), (event -> {
                    updateProgress(pb);
                })
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateProgress(ProgressBar pb) {

        if(pb.getProgress() <= 0.5) {
            pb.setStyle("-fx-accent: orange;");
        }
        if(pb.getProgress() <= 0.2) {
            pb.setStyle("-fx-accent: red;");
        }
        if(pb.getProgress() <= 0.01) {
            pb.setProgress(1);
            pb.setStyle(null);
        }
        pb.setProgress(pb.getProgress() - 0.001);
    }

}