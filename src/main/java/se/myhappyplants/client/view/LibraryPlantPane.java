package se.myhappyplants.client.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import se.myhappyplants.client.controller.HomeTabController;
import se.myhappyplants.client.controller.PlantsTabController;
import se.myhappyplants.shared.DBPlant;

import java.io.File;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Simple pane that displays a DBPlant's information
 * Created by: Christopher O'Driscoll
 * Updated by: Frida Jacobsson
 */
public class LibraryPlantPane extends Pane {
    private HomeTabController homeTabController;

    private ImageView image;
    private Label nickname;
    private ProgressBar progressBar;
    private Button infoButton;
    private Button waterButton;

    private Button changeNicknameButton;
    private Button changePictureButton;
    private Button deleteButton;
    private DatePicker datePicker;
    private Button changeOKButton;
    private ListView listView;
    private ObservableList<String> getAllPlantInfo;

    private boolean extended;
    private boolean gotInfoOnPlant;

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
     * @param homeTabController HomeTabController which contains logic for elements to use
     * @param imgPath    location of user's avatar image
     * @param plant      plant object from user's library
     */
    public LibraryPlantPane(HomeTabController homeTabController, String imgPath, DBPlant plant) {
        this.homeTabController = homeTabController;
        this.setStyle("-fx-background-color: #FFFFFF;");
        File fileImg = new File(imgPath);
        Image img = new Image(fileImg.toURI().toString());

        this.image = new ImageView();
        image.setFitHeight(70.0);
        image.setFitWidth(70.0);
        image.setLayoutX(50.0);
        image.setLayoutY(5.0);
        image.setPickOnBounds(true);
        image.setPreserveRatio(true);
        image.setImage(img);

        nickname = new Label(plant.getNickname());
        nickname.setLayoutX(0);
        nickname.setLayoutY(70);
        nickname.setPrefWidth(145);
        nickname.setAlignment(Pos.CENTER);

        this.progressBar = new ProgressBar(plant.getProgress());
        setColorProgressBar(plant.getProgress());
        progressBar.setLayoutX(150.0);
        progressBar.setLayoutY(28.0);
        progressBar.setPrefHeight(18.0);
        progressBar.setPrefWidth(575.0);

        this.waterButton = new Button("Water");
        waterButton.setLayoutX(400.0);
        waterButton.setLayoutY(55.0);
        waterButton.setMnemonicParsing(false);
        waterButton.setOnAction(action -> {
            progressBar.setProgress(100);
            setColorProgressBar(100);
            homeTabController.changeLastWateredInDB(plant, java.time.LocalDate.now());
            setColorProgressBar(100);
        });

        this.infoButton = new Button("Show info");
        infoButton.setLayoutX(150.0);
        infoButton.setLayoutY(55.0);
        infoButton.setMnemonicParsing(false);
        infoButton.setOnAction(onPress -> {
            infoButton.setDisable(true);
            if (!extended) {
                if(!gotInfoOnPlant) {
                    getAllPlantInfo = homeTabController.getMorePlantInfoOnMyLibraryPlants(plant);
                    listView.setItems(getAllPlantInfo);
                }
                expand();
            } else {
                collapse();
            }
        });

        this.changeNicknameButton = new Button("Change nickname");
        changeNicknameButton.setLayoutX(333.0);
        changeNicknameButton.setLayoutY(250.0);
        changeNicknameButton.setMnemonicParsing(false);
        changeNicknameButton.setOnAction(onPress -> {
            changeNickname(plant);
        });

        this.changeOKButton = new Button("Change");
        changeOKButton.setLayoutX(215.0);
        changeOKButton.setLayoutY(250.0);
        changeOKButton.setMnemonicParsing(false);
        changeOKButton.setOnAction(onPress -> {
            changeDate(plant);
            datePicker.setPromptText("Change last watered");
        });

        this.changePictureButton = new Button("Change picture");
        changePictureButton.setLayoutX(488.0);
        changePictureButton.setLayoutY(250.0);
        changePictureButton.setMnemonicParsing(false);

        this.datePicker = new DatePicker();
        datePicker.setLayoutX(10.0);
        datePicker.setLayoutY(250.0);
        datePicker.setEditable(false);
        datePicker.setPrefWidth(200);
        datePicker.setPromptText("Change last watered");

        this.deleteButton = new Button("Delete plant");
        deleteButton.setLayoutX(625.0);
        deleteButton.setLayoutY(250.0);
        deleteButton.setMnemonicParsing(false);
        deleteButton.setOnAction(onPress -> {
            removePlant(plant);
        });
        listView = new ListView();
        listView.setLayoutX(this.getWidth() + 10.0);
        listView.setLayoutY(this.getHeight() + 100.0); //56.0
        listView.setPrefWidth(725.0);
        listView.setPrefHeight(140.0);

        this.setPrefHeight(92.0);
//        this.setPrefWidth(720.0);
        this.getChildren().addAll(image, nickname, progressBar, waterButton, infoButton);
        this.getChildren().addAll(listView,changeNicknameButton, changePictureButton, deleteButton, datePicker, changeOKButton);
    }


    /**
     * Method for expanding tab with "more information"-buttons.
     */
    public void expand() {
        AtomicReference<Double> height = new AtomicReference<>(this.getHeight());
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(8.5), event -> this.setPrefHeight(height.updateAndGet(v -> (double) (v + 6.25))))
        );
        timeline.setCycleCount(32);
        timeline.play();
        timeline.setOnFinished(action -> {
            infoButton.setDisable(false);
        });
        extended = true;
        gotInfoOnPlant = true;
    }

    /**
     * Method for hiding tab with "more information"-buttons.
     */
    public void collapse() {
        int size = listView.getItems().size();
        for (int i = 0; i < size; i++) {
            listView.getItems().remove(0);
        }
        AtomicReference<Double> height = new AtomicReference<>(this.getHeight());
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(7.5), event -> this.setPrefHeight(height.updateAndGet(v -> (double) (v - 6.25))))
        );
        timeline.setCycleCount(32);
        timeline.play();
        timeline.setOnFinished(action -> infoButton.setDisable(false));
        extended = false;
        gotInfoOnPlant = false;
    }

    /**
     * Changes the colour of the progress bar
     *
     * @param progress How full the progress bar is(0-1.0)
     */
    private void setColorProgressBar(double progress) {
        if (progress < 0.15) {
            progressBar.setStyle("-fx-accent: #0B466B");
        } else {
            progressBar.setStyle("-fx-accent: 2D88AA");
        }
    }

    /**
     * Shows a confirmation box when called,
     * to double check the user really
     * wants to remove the plant
     *
     * @param plant selected plant
     */
    private void removePlant(DBPlant plant) {
        int answer = MessageBox.askYesNo("Delete plant", "Are you sure? The deleted plant can't be restored");
        if (answer == 1) {
            homeTabController.removePlantFromDB(plant);
        }
    }

    /**
     * @param plant
     */
    private void changeNickname(DBPlant plant) {
        String newNickname = MessageBox.askForStringInput("Change nickname", "New nickname:");
        if (homeTabController.changeNicknameInDB(plant, newNickname)) {
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
        homeTabController.changeLastWateredInDB(plant, date);
    }
}
