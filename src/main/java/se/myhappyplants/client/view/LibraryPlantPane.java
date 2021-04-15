package se.myhappyplants.client.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import se.myhappyplants.shared.DBPlant;

/**
 * Simple pane that displays a DBPlant's information
 * todo update to prettier pane
 * Created by: Christopher O'Driscoll
 */
public class LibraryPlantPane extends Pane {

    private ImageView image;
    private Label nickname;
    private ProgressBar progressBar;
    private Button editButton;
    private Button infoButton;
    private Button waterButton;

    public LibraryPlantPane(ImageView image, double progress, DBPlant plant) {

        //this.image = image;
        //image.setFitHeight(45.0);
        //image.setFitWidth(45.0);
        //image.setLayoutX(50.0);
        //image.setLayoutY(14.0);
        //image.setPickOnBounds(true);
        //image.setPreserveRatio(true);


        this.nickname =  new Label(plant.getNickname());
        this.nickname.setLayoutX(117.0);
        this.nickname.setLayoutY(28.0);
        //Region region = new Region();
        //region.setMinWidth(USE_COMPUTED_SIZE);



        this.progressBar = new ProgressBar(0.5);
        progressBar.setLayoutX(196.0);
        progressBar.setLayoutY(27.0);
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


        this.setPrefHeight(92.0);
        this.setPrefWidth(800.0);
        this.getChildren().addAll(this.nickname, progressBar, waterButton, editButton, infoButton);

//        Timeline timeline = new Timeline(
//                new KeyFrame(Duration.millis(1000), event -> {
//                    this.setHeight(200);
//                })
//        );
//        timeline.setCycleCount(1);
//        timeline.play();
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

}
