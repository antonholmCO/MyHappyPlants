package se.myhappyplants.client.model;

import javafx.scene.image.Image;

import java.io.File;

public class ImageLibrary {

    private static final Image plusSign = new Image("Blommor/plusSign.png");
    private static final File loadingImageFile = new File("resources/images/img.png");
    private static final File defaultPlantImageFile = new File("resources/images/Grn_vxt.png");

    public static Image getPlusSign() {
        return plusSign;
    }

    public static File getLoadingImageFile() {
        return loadingImageFile;
    }

    public static File getDefaultPlantImage() {
        return defaultPlantImageFile;
    }
}
