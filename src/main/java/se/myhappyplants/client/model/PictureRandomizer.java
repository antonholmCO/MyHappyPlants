package se.myhappyplants.client.model;

import javafx.scene.image.Image;

import java.io.File;
import java.util.Random;

/**
 * Class that randomize the pictures
 */
public class PictureRandomizer {

    private static File fileImg1 = new File("resources/images/blomma2.jpg");
    private static Image flower1 = new Image(fileImg1.toURI().toString());

    private static File fileImg2 = new File("resources/images/blomma5.jpg");
    private static Image flower2 = new Image(fileImg2.toURI().toString());

    private static File fileImg3 = new File("resources/images/blomma6.jpg");
    private static Image flower3 = new Image(fileImg3.toURI().toString());

    private static File fileImg4 = new File("resources/images/blomma9.jpg");
    private static Image flower4 = new Image(fileImg4.toURI().toString());

    private static File fileImg5 = new File("resources/images/blomma10.jpg");
    private static Image flower5 = new Image(fileImg5.toURI().toString());

    private static File fileImg6 = new File("resources/images/blomma17.jpg");
    private static Image flower6 = new Image(fileImg6.toURI().toString());

    private static File fileImg7 = new File("resources/images/blomma18.jpg");
    private static Image flower7 = new Image(fileImg7.toURI().toString());

    private static File fileImg8 = new File("resources/images/blomma19.jpg");
    private static Image flower8 = new Image(fileImg8.toURI().toString());

    private static File fileImg9 = new File("resources/images/blomma21.jpg");
    private static Image flower9 = new Image(fileImg9.toURI().toString());

    /**
     * Method that generated a random path to a image of a flower
     *
     * @return String path to a picture
     */
    public static Image getRandomPicture() {
        Image image = new Image(getRandomPictureURL());
        return image;
    }

    /**
     * Method that generate and return the pictures URL
     * @return string URL
     */
    public static String getRandomPictureURL() {
        Random random = new Random();
        switch (1 + random.nextInt(8)) {
            case 1:
                return flower1.getUrl();
            case 2:
                return flower2.getUrl();
            case 3:
                return flower3.getUrl();
            case 4:
                return flower4.getUrl();
            case 5:
                return flower5.getUrl();
            case 6:
                return flower6.getUrl();
            case 7:
                return flower7.getUrl();
            case 8:
                return flower8.getUrl();
            default:
                return flower9.getUrl();
        }
    }
}
