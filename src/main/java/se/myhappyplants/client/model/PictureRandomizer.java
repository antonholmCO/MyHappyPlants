package se.myhappyplants.client.model;

import java.util.Random;

public class PictureRandomizer {
    /**
     * Method that generated a random path to a image of a flower
     *
     * @return String path to a picture
     */
    public static String getRandomPicture() {
        Random random = new Random();
        switch (1 + random.nextInt(8)) {
            case 1:
                return "resources/images/blomma2.jpg";
            case 2:
                return "resources/images/blomma5.jpg";
            case 3:
                return "resources/images/blomma6.jpg";
            case 4:
                return "resources/images/blomma9.jpg";
            case 5:
                return "resources/images/blomma10.jpg";
            case 6:
                return "resources/images/blomma17.jpg";
            case 7:
                return "resources/images/blomma18.jpg";
            case 8:
                return "resources/images/blomma19.jpg";
            default:
                return "resources/images/blomma21.jpg";
        }
    }
}
