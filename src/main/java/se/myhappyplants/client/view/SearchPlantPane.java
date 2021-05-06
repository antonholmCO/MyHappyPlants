package se.myhappyplants.client.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import se.myhappyplants.client.controller.PlantsTabController;

import se.myhappyplants.shared.DBPlant;

import java.util.concurrent.atomic.AtomicReference;

/**
 * * Created by: Linn Borgström, Eric Simonsson, Susanne Vikström, 2021-04-21
 * * Updated by: Linn Borgström, 2021-04-30
 */
public class SearchPlantPane extends Pane {
    private ImageView image;
    private Label commonName;
    private Label scientificName;
    private Button infoButton;
    private Button addButton;

    private DBPlant dbPlant;
    private PlantsTabController plantsTabController;
    private ListView listView;
    private boolean gotInfoOnPlant;

    private ObservableList<String> getAllPlantInfo;

    private boolean extended;

    public SearchPlantPane(PlantsTabController plantsTabController, String imgPath, DBPlant dbPlant) {

        this.plantsTabController = plantsTabController;

        this.dbPlant = dbPlant;

        Image img = new Image(imgPath);

        this.image = new ImageView();
        image.setFitHeight(50.0);
        image.setFitWidth(50.0);
        image.setLayoutY(3.0);
        image.setPickOnBounds(true);
        image.setPreserveRatio(true);
        image.setImage(img);

        this.commonName = new Label(dbPlant.getCommonName());
        commonName.setLayoutX(60.0);
        commonName.setLayoutY(20.0);
        commonName.prefHeight(17.0);
        commonName.prefWidth(264.0);

        this.scientificName = new Label(dbPlant.getScientificName());
        scientificName.setLayoutX(280.0);
        scientificName.setLayoutY(20.0);
        scientificName.prefHeight(17.0);
        scientificName.prefWidth(254.0);

        this.infoButton = new Button("More info");
        infoButton.setLayoutX(595.0);
        infoButton.setLayoutY(16.0);
        infoButton.setMnemonicParsing(false);
        infoButton.setOnAction(onPress -> {
            if (!extended) {
                if(!gotInfoOnPlant) {
                    getAllPlantInfo = plantsTabController.getMorePlantInfo(dbPlant);
                    listView.setItems(getAllPlantInfo);

                }
                extendPaneMoreInfoPlant();
            } else {
                retractPane();
            }
        });

        this.addButton = new Button("+");
        addButton.setLayoutX(710.0);
        addButton.setLayoutY(16.0);
        addButton.setMnemonicParsing(false);
        addButton.setOnAction(action -> plantsTabController.addPlantToCurrentUserLibrary(dbPlant));

        listView = new ListView();
        listView.setLayoutX(this.getWidth());
        listView.setLayoutY(this.getHeight() + 56.0);
        listView.setPrefWidth(751.0);
        listView.setPrefHeight(150.0);


        this.prefHeight(56.0);
        //this.prefWidth(761.0);
        this.getChildren().addAll(image, commonName, scientificName, infoButton, addButton);
    }


    public void updateImage() {
        Image img = new Image(String.valueOf(dbPlant.getImageURL()));
        image.setImage(img);
    }

    public DBPlant getApiPlant() {
        return dbPlant;
    }

    public void setDefaultImage(String defaultImage) {
        Image img = new Image(defaultImage);
        image.setImage(img);
    }

    public void extendPaneMoreInfoPlant() {
        AtomicReference<Double> height = new AtomicReference<>(this.getHeight());
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(7.5), event -> this.setPrefHeight(height.updateAndGet(v -> (double) (v + 6.25))))
        );
        timeline.setCycleCount(32);
        timeline.play();
        timeline.setOnFinished(action -> this.getChildren().addAll(listView));
        extended = true;
        gotInfoOnPlant = true;

    }

    public void retractPane() {
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
        this.getChildren().removeAll(listView);
        extended = false;
        gotInfoOnPlant = false;
    }
}

