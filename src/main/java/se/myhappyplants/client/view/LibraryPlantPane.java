package se.myhappyplants.client.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import se.myhappyplants.client.controller.HomeTabController;
import se.myhappyplants.shared.DBPlant;

import java.io.File;

/**
 * Simple pane that displays a DBPlant's information
 * todo update to prettier pane
 * Created by: Christopher O'Driscoll
 */
public class LibraryPlantPane extends Pane {
    private HomeTabController controller;

    //Always shown
    private ImageView image;
    private Label nickname;
    private ProgressBar progressBar;
    private Button editButton;
    private Button infoButton;
    private Button waterButton;

    //Shown when plant info pressed
    private Button changeNameButton;
    private Button changePictureButton;
    private Button deleteButton;


    private boolean extended;

    public LibraryPlantPane(HomeTabController controller, String imgPath, double progress, DBPlant plant) {
        this.controller = controller;
        File fileImg = new File(imgPath);
        Image img = new Image(fileImg.toURI().toString());

        this.image = new ImageView();
        image.setFitHeight(45.0);
        image.setFitWidth(45.0);
        image.setLayoutX(50.0);
        image.setLayoutY(14.0);
        image.setPickOnBounds(true);
        image.setPreserveRatio(true);
        image.setImage(img);

        this.nickname =  new Label(plant.getNickname());
        double nicknameWidth = this.nickname.getWidth();
        this.nickname.setLayoutX(0);
        this.nickname.setLayoutY(65);
        this.nickname.setPrefWidth(145);
        this.nickname.setAlignment(Pos.CENTER);
        //Region region = new Region();
        //region.setMinWidth(USE_COMPUTED_SIZE);


        this.progressBar = new ProgressBar(plant.getProgress());
        progressBar.setLayoutX(196.0);
        progressBar.setLayoutY(28.0);
        progressBar.setPrefHeight(18.0);
        progressBar.setPrefWidth(545.0);

        this.waterButton = new Button("Water");
        waterButton.setLayoutX(436.0);
        waterButton.setLayoutY(59.0);
        waterButton.setMnemonicParsing(false);

        this.editButton = new Button("Edit plant");
        editButton.setLayoutX(675.0);
        editButton.setLayoutY(59.0);
        editButton.setMnemonicParsing(false);

        this.infoButton = new Button("Show plant info");
        infoButton.setLayoutX(196.0);
        infoButton.setLayoutY(59.0);
        infoButton.setMnemonicParsing(false);
        infoButton.setOnAction(action -> {
            if(!extended) {
                extendPane();
            }
            else {
                retractPane();
            }
        });

        this.changeNameButton = new Button("Change nickname");
        changeNameButton.setLayoutX(370.0);
        changeNameButton.setLayoutY(250.0);
        changeNameButton.setMnemonicParsing(false);

        this.changePictureButton = new Button("Change plant picture");
        changePictureButton.setLayoutX(500);
        changePictureButton.setLayoutY(250.0);
        changePictureButton.setMnemonicParsing(false);

        this.deleteButton = new Button("Delete plant");
        deleteButton.setLayoutX(650.0);
        deleteButton.setLayoutY(250.0);
        deleteButton.setMnemonicParsing(false);
        deleteButton.setOnAction(onPress -> {
            controller.removePlantFromDatabase(plant);
        });


        this.setPrefHeight(92.0);
        this.setPrefWidth(761.0);
        this.getChildren().addAll(image, this.nickname, progressBar, waterButton, editButton, infoButton);

    }

    public void extendPane() {

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(100), event -> {
                            this.setPrefHeight(this.getHeight() + 50);
                    })
            );
            timeline.setCycleCount(4);
            timeline.play();
            timeline.setOnFinished(action -> {
                this.getChildren().addAll(changeNameButton, changePictureButton, deleteButton);
            });
            extended = true;

    }
    public void retractPane() {

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> {
                        this.setPrefHeight(this.getHeight() - 50);
                })
        );
        timeline.setCycleCount(4);
        timeline.play();
        this.getChildren().removeAll(changeNameButton, changePictureButton, deleteButton);
        extended = false;

    }

}
