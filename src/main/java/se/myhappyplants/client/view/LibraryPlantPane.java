package se.myhappyplants.client.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import se.myhappyplants.client.controller.HomeTabController;
import se.myhappyplants.shared.DBPlant;
import java.io.File;
import java.time.LocalDate;

/**
 * Simple pane that displays a DBPlant's information
 * todo update to prettier pane
 * Created by: Christopher O'Driscoll
 * Updated by: Frida Jacobsson
 */
public class LibraryPlantPane extends Pane {
    private HomeTabController controller;

    //Always shown
    private ImageView image;
    private Label nickname;
    private ProgressBar progressBar;
    private Button infoButton;
    private Button waterButton;

    //Shown when plant info pressed
    private Button changeNameButton;
    private Button changePictureButton;
    private Button deleteButton;
    private Label changeLastWaterLbl;
    private DatePicker datePicker;
    private Button changeOKButton;


    private boolean extended;

    public LibraryPlantPane(HomeTabController controller, String imgPath, DBPlant plant) {
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

        nickname =  new Label(plant.getNickname());
        nickname.setLayoutX(0);
        nickname.setLayoutY(65);
        nickname.setPrefWidth(145);
        nickname.setAlignment(Pos.CENTER);

        this.progressBar = new ProgressBar(plant.getProgress());

        setColorProgressBar(plant.getProgress());
        progressBar.setLayoutX(196.0);

        progressBar.setLayoutY(28.0);
        progressBar.setPrefHeight(18.0);
        progressBar.setPrefWidth(575.0);

        this.waterButton = new Button("Water");
        waterButton.setLayoutX(400.0);
        waterButton.setLayoutY(59.0);
        waterButton.setMnemonicParsing(false);
        waterButton.setOnAction(action -> {
            progressBar.setProgress(100);
            progressBar.setStyle("-fx-accent: 2D88AA");
        });

        this.infoButton = new Button("Show plant info");
        infoButton.setLayoutX(150.0);
        infoButton.setLayoutY(59.0);
        infoButton.setMnemonicParsing(false);
        infoButton.setOnAction(onPress -> {
            if(!extended) {
                extendPaneEditPlant();
            }
            else {
                retractPane();
            }
        });

        this.changeNameButton = new Button("Change nickname");
        changeNameButton.setLayoutX(350.0);
        changeNameButton.setLayoutY(250.0);
        changeNameButton.setMnemonicParsing(false);

        this.changeOKButton = new Button("Submit");
        changeOKButton.setLayoutX(210.0);
        changeOKButton.setLayoutY(250.0);
        changeOKButton.setMnemonicParsing(false);
        changeOKButton.setOnAction(onPress -> {
            LocalDate date = datePicker.getValue();
            plant.setLastWatered(date);
            System.out.println(plant.getLastWatered());
        });

        this.changePictureButton = new Button("Change plant picture");
        changePictureButton.setLayoutX(480.0);
        changePictureButton.setLayoutY(250.0);
        changePictureButton.setMnemonicParsing(false);

        this.changeLastWaterLbl = new Label("Change last watered");
        changeLastWaterLbl.setLayoutX(10.0);
        changeLastWaterLbl.setLayoutY(220);
        changeLastWaterLbl.setMnemonicParsing(false);

        this.datePicker = new DatePicker();
        datePicker.setLayoutX(10.0);
        datePicker.setLayoutY(250.0);

        this.deleteButton = new Button("Delete plant");
        deleteButton.setLayoutX(625.0);
        deleteButton.setLayoutY(250.0);
        deleteButton.setMnemonicParsing(false);

        deleteButton.setOnAction(onPress -> {
            removePlant(plant);
        });


        this.setPrefHeight(92.0);
        this.setPrefWidth(750.0);
        this.getChildren().addAll(image, nickname, progressBar, waterButton, infoButton);

    }

    public void extendPaneEditPlant() {

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(100), event -> this.setPrefHeight(this.getHeight() + 50))
            );
            timeline.setCycleCount(4);
            timeline.play();
            timeline.setOnFinished(action -> this.getChildren().addAll(changeNameButton, changePictureButton, deleteButton, changeLastWaterLbl, datePicker, changeOKButton));
            extended = true;

    }

    public void retractPane() {

        Timeline timeline = new Timeline(

                new KeyFrame(Duration.millis(100), event -> this.setPrefHeight(this.getHeight() - 50))


        );
        timeline.setCycleCount(4);
        timeline.play();
        this.getChildren().removeAll(changeNameButton, changePictureButton, deleteButton);
        extended = false;
    }


        //TODO: decide how we want colors in progressbar
        private void setColorProgressBar(double progress){
            if (progress < 0.15) {
                progressBar.setStyle("-fx-accent: red");
            }
        }

        private void removePlant(DBPlant plant) {
            int answer = MessageBox.askYesNo("Delete plant", "Are you sure?");
            if (answer == 1) {
                controller.removePlantFromDatabase(plant);
                controller.removePlantFromDatabase(plant);
            }
        }

}
