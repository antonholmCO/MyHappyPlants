package se.myhappyplants.client.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import se.myhappyplants.client.controller.StartClient;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Creates a message box that disappears by itself
 * created by: Christopher O'Driscoll
 * updated by:
 */
public class PopupBox extends Popup {

    private static Stage window;
    private static VBox vBox;
    private static ToggleButton tglBtnChangeNotification;

    public static void display(String message) {

        window = new Stage();
        window.initModality(Modality.NONE);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setMinWidth(150);
        window.setMinHeight(30);

        Stage mainStage = StartClient.getStage();
        double x = mainStage.getX() + mainStage.getWidth() - 200;
        double y = mainStage.getY() + mainStage.getHeight() - 70;
        window.setX(x);
        window.setY(y);

        Label label = new Label();
        label.setText(message);
        label.setTextAlignment(TextAlignment.CENTER);

        vBox = new VBox(10);
        vBox.getChildren().add(label);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, Color.TRANSPARENT);
        scene.getStylesheets().add("/se/myhappyplants/client/controller/Stylesheet.css");
        window.setScene(scene);

        showAndFade();
    }

    private static void showAndFade() {
        window.show();
        AtomicReference<Double> opacity = new AtomicReference<>(1.0);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(40), event -> {
                    window.getScene().getRoot().opacityProperty().set(opacity.updateAndGet(v -> (double) (v - 0.01)));
                })
        );
        timeline.setCycleCount(100);
        timeline.setOnFinished(action -> {
            if(tglBtnChangeNotification!=null){
                tglBtnChangeNotification.setDisable(false);
            }
            window.close();
        });
        timeline.play();

    }

    public static void display(String message, ToggleButton tglBtnChangeNotification) {
        PopupBox.tglBtnChangeNotification = tglBtnChangeNotification;
        display(message);
    }
}
