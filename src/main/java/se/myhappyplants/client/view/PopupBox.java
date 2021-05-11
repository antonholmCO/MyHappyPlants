package se.myhappyplants.client.view;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.util.Duration;
import se.myhappyplants.client.controller.StartClient;


public class PopupBox extends Popup {
    private Label label;

    public void display(String message) {

        label = new Label(message);
        label.setStyle("-fx-font-size: 14pt; -fx-text: textColor; -fx-border-color: black; -fx-font-family: 'Eras Medium ITC'; -fx-base: BackgroundGreen;");
        label.setLayoutX(750);
        label.setLayoutY(475);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setPrefSize(150, 100);
        getContent().add(label);
        //setAutoHide(true);
        showAndFade();
    }
    private void showAndFade() {
//        show(StartClient.getStage());
        FadeTransition ft = new FadeTransition(Duration.millis(4000), label);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();

    }
}
