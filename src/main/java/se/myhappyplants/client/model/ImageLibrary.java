package se.myhappyplants.client.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageLibrary {

    private static final Image plusSign = new Image("Blommor/plusSign.png");

    public static Image getPlusSign() {
       return plusSign;
    }
}
