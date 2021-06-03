package se.myhappyplants.client.view;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import se.myhappyplants.client.model.BoxTitle;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Simple message box
 * Created by: Christopher O'Driscoll
 */
public class MessageBox {

    private static Stage window;
    private static VBox vBox;

    /**
     * Method to initialize and display the pop up message box
     * @param boxTitle the title of the box
     * @param message the message of the box
     */
    public static void display(BoxTitle boxTitle, String message) {
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(String.valueOf(boxTitle));
        window.setMinWidth(250);
        window.setMinHeight(100);

        Label label = new Label();
        label.setText(message);

        Button okButton = new Button("OK");
        okButton.setOnAction(action -> {
            window.close();
        });

        label.setStyle("-fx-font-size: 18px; -fx-padding: 20 20 20 20;");
        okButton.setStyle("-fx-font-size: 18px;");

        vBox = new VBox(10);
        vBox.getChildren().addAll(label, okButton);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        scene.getStylesheets().add("/se/myhappyplants/client/controller/Stylesheet.css");
        window.setScene(scene);
        window.showAndWait();
    }


    /**
     * Displays a yes/no input box
     * Created by: Anton Holm
     *
     * @param boxTitle    The title of the message box stage/window
     * @param question The question which the user should answer yes or no
     * @return 1 if yes, 0 if no and -1 if no answer chosen
     */
    public static int askYesNo(BoxTitle boxTitle, String question) {
        Stage window = new Stage();
        AtomicInteger answer = new AtomicInteger(-1);


        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(String.valueOf(boxTitle));
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

        //Styling
        label.setStyle("-fx-font-size: 18px; -fx-padding: 20 20 20 20;");
        yesButton.setStyle("-fx-font-size: 18px;");
        noButton.setStyle("-fx-font-size: 18px;");
        VBox.setMargin(noButton,new Insets(5,0,15,0));

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(label, yesButton, noButton);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        scene.getStylesheets().add("/se/myhappyplants/client/controller/Stylesheet.css");
        window.setScene(scene);
        window.showAndWait();

        return answer.get();
    }

    /**
     * Displays a input box with a text field
     * Created by: Anton Holm
     *
     * @param title    The title of the message box stage/window
     * @param question The question that the user should answer with their input
     * @return String entered by user
     */
    public static String askForStringInput(String title, String question) {
        Stage window = new Stage();
        AtomicReference<String> input = new AtomicReference<>("");


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

        //Styling
        label.setStyle("-fx-font-size: 18px; -fx-padding: 20 20 5 20;");
        textField.setStyle("-fx-font-size: 18px;");
        enterButton.setStyle("-fx-font-size: 16px;");
        VBox.setMargin(textField,new Insets(0,20,0,20));
        VBox.setMargin(enterButton,new Insets(5,0,20,0));

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(label, textField, enterButton);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        scene.getStylesheets().add("/se/myhappyplants/client/controller/Stylesheet.css");
        window.setScene(scene);
        window.showAndWait();

        return input.get();
    }
}
