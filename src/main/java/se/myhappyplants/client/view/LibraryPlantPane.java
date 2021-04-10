package se.myhappyplants.client.view;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Simple pane that displays a DBPlant's information
 * todo update to prettier pane
 * @author Christopher O'Driscoll
 */
public class LibraryPlantPane extends BorderPane {

    private ImageView image;
    private Label name;
    private ProgressBar progressBar;

    public LibraryPlantPane(ImageView image, Label name, double progress) {

        this.image = image;
        image.setFitHeight(100);
        image.setFitWidth(100);

        HBox hBox = new HBox(image);
        hBox.setMaxWidth(100);
        this.setLeft(hBox);

        this.name = name;
        Region region = new Region();
        region.setMinWidth(USE_COMPUTED_SIZE);
        this.progressBar = new ProgressBar(progress);
        progressBar.setMinWidth(400);

        VBox vbox = new VBox(name, region, progressBar);
        this.setRight(vbox);
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

}
