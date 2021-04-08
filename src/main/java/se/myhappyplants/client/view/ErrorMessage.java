package se.myhappyplants.client.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorMessage {
    public static void display(String title, String message) {
        Stage window = new Stage();

        //block interaction with other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(100);

        Label label = new Label();
        label.setText(message);

        Button okButton = new Button("OK");
        okButton.setOnAction(action -> {
            window.close();
        });


        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(label, okButton);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();

    }

}
