package se.myhappyplants.client.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class ImageLibrary {

    private static final Image plusSign = new Image("Blommor/plusSign.png");
    private static final File fileImg = new File("resources/images/img.png");

    public static Image getPlusSign() {
        return plusSign;
    }

    public Image getDefaultImage() {
        Image img = new Image(fileImg.toURI().toString());
        return img;
    }
}
