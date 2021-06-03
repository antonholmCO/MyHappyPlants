package se.myhappyplants.client.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Simple yes/no confirmation box
 * Created by: Christopher O'Driscoll
 * Updated by:
 */
public class ConfirmationBox {

    private static boolean answer;

    /**
     * Static method to display a pop up box
     * @param title the title of the box
     * @param message the message in the box
     * @return boolean if it's successful
     */
    public static boolean display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(150);

        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(action -> {
            answer = true;
            window.close();
        });

        Button noButton = new Button("No");
        noButton.setOnAction(action -> {
            answer = false;
            window.close();
        });

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(label, yesButton, noButton);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        scene.getStylesheets().add("/se/myhappyplants/client/controller/Stylesheet.css");
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}

