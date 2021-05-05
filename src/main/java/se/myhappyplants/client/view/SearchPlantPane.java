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
    private Label lblFamilyName;
    private Label lblLightText;
    private Label lblWaterText;
    private Label lblGenusText;
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

        this.commonName = new Label("Common name: " + dbPlant.getCommonName());
        commonName.setLayoutX(60.0);
        commonName.setLayoutY(20.0);
        commonName.prefHeight(17.0);
        commonName.prefWidth(264.0);

        this.scientificName = new Label("Scientific name: " + dbPlant.getScientificName());
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
                if(!gotInfoOnPlant) {
                    getAllPlantInfo = plantsTabController.getMorePlantInfo(dbPlant);
                    for (int i = 0; i < getAllPlantInfo.size(); i++) {
                        listView.getItems().add(getAllPlantInfo.get(i));
                    }

                }
                extendPaneMoreInfoPlant();
                System.out.println(" From searchPane " + plantsTabController.getMorePlantInfo(dbPlant));
            } else {
                retractPane();
            }
        });

        this.addButton = new Button("+");
        addButton.setLayoutX(723.0);
        addButton.setLayoutY(16.0);
        addButton.setMnemonicParsing(false);
        addButton.setOnAction(action -> plantsTabController.addPlantToCurrentUserLibrary(dbPlant));

        listView = new ListView();
        listView.setLayoutX(110.0); //this.getWidth()
        listView.setLayoutY(this.getHeight() + 56.0);
        listView.setPrefWidth(651.0); //751.0
        listView.setPrefHeight(150.0);

        lblFamilyName = new Label();
        lblFamilyName.setText("Family name: ");
        lblFamilyName.setLayoutX(5.0);
        lblFamilyName.setLayoutY(67.0);
        lblFamilyName.setPrefHeight(15.0);
        lblFamilyName.setPrefWidth(100.0);

        lblLightText = new Label();
        lblLightText.setText("Light: ");
        lblLightText.setLayoutX(5.0);
        lblLightText.setLayoutY(95.0);
        lblLightText.setPrefHeight(15.0);
        lblLightText.setPrefWidth(100.0);

        lblWaterText = new Label();
        lblWaterText.setText("Water: ");
        lblWaterText.setLayoutX(5.0);
        lblWaterText.setLayoutY(119.0);
        lblWaterText.setPrefHeight(15.0);
        lblWaterText.setPrefWidth(100.0);

        lblGenusText = new Label();
        lblGenusText.setText("Genus: ");
        lblGenusText.setLayoutX(5.0);
        lblGenusText.setLayoutY(144.0);
        lblGenusText.setPrefHeight(15.0);
        lblGenusText.setPrefWidth(100.0);


        this.prefHeight(56.0);
        this.prefWidth(761.0);
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

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> this.setPrefHeight(this.getHeight() + 50))
        );
        timeline.setCycleCount(4);
        timeline.play();
        timeline.setOnFinished(action -> this.getChildren().addAll(listView,lblFamilyName,lblLightText,lblWaterText,lblGenusText));
        extended = true;
        gotInfoOnPlant = true;

    }

    public void retractPane() {
        int size = listView.getItems().size();
        for (int i = 0; i < size; i++) {
            listView.getItems().remove(0);
        }

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> this.setPrefHeight(this.getHeight() - 50))
        );
        timeline.setCycleCount(4);
        timeline.play();
        this.getChildren().removeAll();
        extended = false;
        gotInfoOnPlant = false;
    }
}
