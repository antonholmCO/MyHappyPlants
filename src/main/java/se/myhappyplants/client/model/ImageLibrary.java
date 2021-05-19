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

    public static File getDefaultImage() {
        return fileImg;
    }
}
