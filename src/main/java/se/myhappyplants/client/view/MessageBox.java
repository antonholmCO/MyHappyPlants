package se.myhappyplants.client.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Simple message box
 * Created by: Christopher O'Driscoll
 */
public class MessageBox {
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
    /**
     * Displays a yes/no input box
     * Created by: Anton Holm
     *
     * @return 1 if yes, 0 if no and -1 if no answer chosen
     */
    public static int askYesNo(String title, String question) {
        Stage window = new Stage();
        AtomicInteger answer = new AtomicInteger(-1);

        //block interaction with other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);
        window.setMinHeight(150);

        Label label = new Label();
        label.setText(question);

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(action -> {
            answer.set(1);
            window.close();
        });

        Button noButton = new Button("No");
        noButton.setOnAction(action -> {
            answer.set(0);
            window.close();
        });


        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(label, yesButton, noButton);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();

        return answer.get();
    }

    public static String askForStringInput(String title, String question) {
        Stage window = new Stage();
        AtomicReference<String> input = new AtomicReference<>("");

        //block interaction with other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);
        window.setMinHeight(150);

        Label label = new Label();
        label.setText(question);

        TextField textField = new TextField();
        textField.setMinWidth(100);
        textField.setMinHeight(10);

        Button enterButton = new Button("Enter");
        enterButton.setOnAction(action -> {
            input.set(textField.getText());
            window.close();
        });


        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(label, textField, enterButton);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();

        return input.get();
    }

}
