package se.myhappyplants.client.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import se.myhappyplants.client.controller.PlantsTabController;
import se.myhappyplants.shared.DBPlant;

/**
 * * Created by: Linn Borgström, Eric Simonsson, Susanne Vikström, 2021-04-21
 * * Updated by: Linn Borgström, Eric Simonsson, Susanne Vikström 2021-04-28
 */
public class SearchPlantPane extends Pane {
    private ImageView image;
    private Label commonName;
    private Label scientificName;
    private Button infoButton;
    private Button addButton;

    private DBPlant DBPlant;
    private PlantsTabController plantsTabController;
    private ListView listView;

    private ObservableList<String> getAllPlantInfo;

    private boolean extended;

    public SearchPlantPane(PlantsTabController plantsTabController, String imgPath, DBPlant DBPlant) {

        this.plantsTabController = plantsTabController;

        this.DBPlant = DBPlant;

        Image img = new Image(imgPath);

        this.image = new ImageView();
        image.setFitHeight(50.0);
        image.setFitWidth(50.0);
        image.setLayoutY(3.0);
        image.setPickOnBounds(true);
        image.setPreserveRatio(true);
        image.setImage(img);

        this.commonName = new Label("Common name: " + DBPlant.getCommonName());
        commonName.setLayoutX(60.0);
        commonName.setLayoutY(20.0);
        commonName.prefHeight(17.0);
        commonName.prefWidth(264.0);

        this.scientificName = new Label("Scientific name: " + DBPlant.getScientificName());
        scientificName.setLayoutX(315.0);
        scientificName.setLayoutY(20.0);
        scientificName.prefHeight(17.0);
        scientificName.prefWidth(254.0);

        this.infoButton = new Button("Show more information");
        infoButton.setLayoutX(570.0);
        infoButton.setLayoutY(16.0);
        infoButton.setMnemonicParsing(false);
        infoButton.setOnAction(onPress -> {
            if (!extended) {
                getAllPlantInfo = plantsTabController.getMorePlantInfo(DBPlant);
                for (int i = 0; i < getAllPlantInfo.size(); i++) {
                    listView.getItems().add(getAllPlantInfo.get(i).toString());
                }

                extendPaneMoreInfoPlant();
            } else {
                retractPane();
            }
        });

        this.addButton = new Button("+");
        addButton.setLayoutX(723.0);
        addButton.setLayoutY(16.0);
        addButton.setMnemonicParsing(false);
        addButton.setOnAction(action -> plantsTabController.addPlantToCurrentUserLibrary(DBPlant));

        listView = new ListView();
        listView.setLayoutX(this.getWidth());
        listView.setLayoutY(this.getHeight() + 56.0);
        listView.setPrefWidth(751.0);
        listView.setPrefHeight(150.0);


        this.prefHeight(56.0);
        this.prefWidth(761.0);
        this.getChildren().addAll(image, commonName, scientificName, infoButton, addButton);
    }


    public void updateImage() {
        Image img = new Image(DBPlant.getImageURL());
        System.out.println(DBPlant.getImageURL());
        image.setImage(img);
    }

    public DBPlant getApiPlant() {
        return DBPlant;
    }

    public void setDefaultImage(String defaultImage) {
        Image img = new Image(defaultImage);
        image.setImage(img);
    }

    public void extendPaneMoreInfoPlant() {

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> this.setPrefHeight(this.getHeight() + 50))
        );
        timeline.setCycleCount(4);
        timeline.play();
        timeline.setOnFinished(action -> this.getChildren().addAll(listView));
        extended = true;

    }

    public void retractPane() {

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> this.setPrefHeight(this.getHeight() - 50))
        );
        timeline.setCycleCount(4);
        timeline.play();
        this.getChildren().removeAll();
        extended = false;
    }
}

