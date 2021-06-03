package se.myhappyplants.client.view;

/**
 * Class to format the text about the light level on a plant
 * Created by: Frida Jacobsson, Eric Simonsson, Susanne Vikström, Linn Borgström
 * Updated by: Frida Jacobsson, 2021-05-12
 */
public class LightTextFormatter {

    public static String getLightTextString(int light) {
        String lightText;
        switch(light) {
            case 1:
            case 2:
                lightText = "Is happy in darkness";
                break;
            case 3:
            case 4:
                lightText = "Is happy to be in darker areas";
                break;
            case 5:
            case 6:
                lightText = "Is not happy to be placed in direct sunlight but still needs som light";
                break;
            case 7:
                lightText = "Is happy in full daylight without direct sunlight";
                break;
            case 8:
                lightText = "Is happy in full daylight with some direct sunlight";
                break;
            case 9:
                lightText = "Is happy in full daylight with a lot of direct sunlight";
                break;
            case 10:
                lightText = "Is happy in direct sunlight";
                break;
            default:
                lightText = "There's no information about the light level";
        }
        return lightText;
    }
}
