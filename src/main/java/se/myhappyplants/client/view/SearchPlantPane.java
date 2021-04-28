package se.myhappyplants.client.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import se.myhappyplants.client.controller.PlantsTabController;
import se.myhappyplants.shared.APIPlant;

/**
 *  * Created by: Linn Borgström, Eric Simonsson, Susanne Vikström, 2021-04-21
 *  * Updated by: Linn Borgström, Eric Simonsson, Susanne Vikström, 2021-04-21
 */
public class SearchPlantPane extends Pane {
    private ImageView image;
    private Label commonName;
    private Label scientificName;
    private Button infoButton;
    private Button addButton;

    private APIPlant apiPlant;
    private PlantsTabController plantsTabController;

    public SearchPlantPane(PlantsTabController plantsTabController, String imgPath, APIPlant apiPlant){
        this.plantsTabController = plantsTabController;

        this.apiPlant = apiPlant;

        Image img = new Image(imgPath);

        this.image = new ImageView();
        image.setFitHeight(50.0);
        image.setFitWidth(50.0);
        image.setLayoutY(3.0);
        image.setPickOnBounds(true);
        image.setPreserveRatio(true);
        image.setImage(img);

        this.commonName =  new Label("Common name: " + apiPlant.getCommon_name());
        commonName.setLayoutX(60.0);
        commonName.setLayoutY(20.0);
        commonName.prefHeight(17.0);
        commonName.prefWidth(264.0);

        this.scientificName =  new Label("Scientific name: " + apiPlant.getScientific_name());
        scientificName.setLayoutX(315.0);
        scientificName.setLayoutY(20.0);
        scientificName.prefHeight(17.0);
        scientificName.prefWidth(254.0);

        this.infoButton = new Button("Show more information");
        infoButton.setLayoutX(570.0);
        infoButton.setLayoutY(16.0);
        infoButton.setMnemonicParsing(false);

        this.addButton = new Button("+");
        addButton.setLayoutX(723.0);
        addButton.setLayoutY(16.0);
        addButton.setMnemonicParsing(false);
        addButton.setOnAction(action -> plantsTabController.addPlantToCurrentUserLibrary(apiPlant));


        this.prefHeight(56.0);
        this.prefWidth(761.0);
        this.getChildren().addAll(image, commonName, scientificName, infoButton, addButton);
    }

    public void updateImage() {
        Image img = new Image(String.valueOf(apiPlant.image_url));
        image.setImage(img);
    }

    public APIPlant getApiPlant() {
        return apiPlant;
    }

    public void setDefaultImage(String defaultImage) {
        Image img = new Image(defaultImage);
        image.setImage(img);
    }
}

