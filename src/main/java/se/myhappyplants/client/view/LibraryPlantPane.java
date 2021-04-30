package se.myhappyplants.client.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
    private Button changeNicknameButton;
    private Button changePictureButton;
    private Button deleteButton;
    private DatePicker datePicker;
    private Button changeOKButton;

    private boolean extended;

    /**
     * Creates a simple pane with loading image
     * while waiting for response from database
     */
    public LibraryPlantPane() {
        File fileImg = new File("resources/images/img.png");
        Image img = new Image(fileImg.toURI().toString());
        image = new ImageView(img);
        image.setFitHeight(45.0);
        image.setFitWidth(45.0);
        image.setLayoutX(50.0);
        image.setLayoutY(14.0);

        nickname = new Label("Your plants are being loaded from the database..");
        nickname.setLayoutX(100);
        nickname.setLayoutY(25);
        nickname.setPrefWidth(300);
        nickname.setAlignment(Pos.CENTER);

        this.getChildren().addAll(image, nickname);
    }

    /**
     * Creates a pane using information from a user's
     * plant library
     *
     * @param controller HomeTabController which contains logic for elements to use
     * @param imgPath    location of user's avatar image
     * @param plant      plant object from user's library
     */
    public LibraryPlantPane(HomeTabController controller, String imgPath, DBPlant plant) {
        this.controller = controller;
        this.setStyle("-fx-background-color: #FFFFFF;");
        File fileImg = new File(imgPath);
        Image img = new Image(fileImg.toURI().toString());

        this.image = new ImageView();
        image.setFitHeight(70.0);
        image.setFitWidth(70.0);
        image.setLayoutX(50.0);
        image.setLayoutY(10.0);
        image.setPickOnBounds(true);
        image.setPreserveRatio(true);
        image.setImage(img);

        nickname = new Label(plant.getNickname());
        nickname.setLayoutX(0);
        nickname.setLayoutY(75);
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
            setColorProgressBar(100);
            controller.changeLastWateredInDB(plant, java.time.LocalDate.now());
            setColorProgressBar(100);
        });

        this.infoButton = new Button("Show plant info");
        infoButton.setLayoutX(150.0);
        infoButton.setLayoutY(59.0);
        infoButton.setMnemonicParsing(false);
        infoButton.setOnAction(onPress -> {
            infoButton.setDisable(true);
            if (!extended) {
                expand();
            } else {
                collapse();
            }
        });

        this.changeNicknameButton = new Button("Change nickname");
        changeNicknameButton.setLayoutX(350.0);
        changeNicknameButton.setLayoutY(250.0);
        changeNicknameButton.setMnemonicParsing(false);
        changeNicknameButton.setOnAction(onPress -> {
            changeNickname(plant);
        });

        this.changeOKButton = new Button("Change");
        changeOKButton.setLayoutX(210.0);
        changeOKButton.setLayoutY(250.0);
        changeOKButton.setMnemonicParsing(false);
        changeOKButton.setOnAction(onPress -> {
            changeDate(plant);
        });

        this.changePictureButton = new Button("Change plant picture");
        changePictureButton.setLayoutX(480.0);
        changePictureButton.setLayoutY(250.0);
        changePictureButton.setMnemonicParsing(false);

        this.datePicker = new DatePicker();
        datePicker.setLayoutX(10.0);
        datePicker.setLayoutY(250.0);
        datePicker.setEditable(false);
        datePicker.setPromptText("Change last watered");

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
        this.getChildren().addAll(changeNicknameButton, changePictureButton, deleteButton, datePicker, changeOKButton);
    }

    /**
     * Method for expanding tab with "more information"-buttons.
     */
    public void expand() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> this.setPrefHeight(this.getHeight() + 50))
        );
        timeline.setCycleCount(4);
        timeline.play();
        timeline.setOnFinished(action -> infoButton.setDisable(false));
        extended = true;
    }

    /**
     * Method for hiding tab with "more information"-buttons.
     */
    public void collapse() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> this.setPrefHeight(this.getHeight() - 50))
        );
        timeline.setCycleCount(4);
        timeline.play();
        timeline.setOnFinished(action -> infoButton.setDisable(false));
        extended = false;
    }

    /**
     * Changes the colour of the progress bar
     *
     * @param progress How full the progress bar is(0-1.0)
     */
    //TODO: decide how we want colors in progressbar
    private void setColorProgressBar(double progress) {
        if (progress < 0.15) {
            progressBar.setStyle("-fx-accent: red");
        } else {
            progressBar.setStyle("-fx-accent: 2D88AA");
        }
    }

    /**
     * Shows a confirmation box when called,
     * to double check the user really
     * wants to remove the plant
     * todo: should this method be in controller instead?
     *
     * @param plant selected plant
     */
    private void removePlant(DBPlant plant) {
        int answer = MessageBox.askYesNo("Delete plant", "Are you sure?");
        if (answer == 1) {
            controller.removePlantFromDatabase(plant);
        }
    }

    /**
     * @param plant
     */
    private void changeNickname(DBPlant plant) {
        String newNickname = MessageBox.askForStringInput("Change nickname", "Type new nickname:");
        if (controller.changeNicknameInDB(plant, newNickname)) {
            nickname.setText(newNickname);
        }
    }

    /**
     * @param plant
     */
    private void changeDate(DBPlant plant) {
        LocalDate date = datePicker.getValue();
        plant.setLastWatered(date);
        progressBar.setProgress(plant.getProgress());
        setColorProgressBar(plant.getProgress());
        controller.changeLastWateredInDB(plant, date);
    }
}
