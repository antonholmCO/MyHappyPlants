package se.myhappyplants.client.model;

/**
 * Created by: Frida Jacobsson, Eric Simonsson, Susanne Vikström, Linn Borgström
 * Updated by: Frida Jacobsson, 2021-05-12
 */
public class LightCalculator {

    public String calculateLightLevel(String light) {
        String lightText = light;
        if (light.equals("10")) {
            lightText = "Is happy in direct sunlight";
        }
        else if (light.equals("9")) {
            lightText = "Is happy in full daylight with a lot of direct sunlight";
        }
        else if (light.equals("8")) {
            lightText = "Is happy in full daylight with some direct sunlight";
        }
        else if (light.equals("7")) {
            lightText = "Is happy in full daylight without direct sunlight";
        }
        else if (light.equals("6") || light.equals("5")) {
            lightText = "Is not happy to be placed in direct sunlight but still needs som light";
        }
        else if (light.equals("4") || light.equals("3")) {
            lightText = "Is happy to be in darker areas";
        }
        else if (light.equals("1") || light.equals("2")) {
            lightText = "Is happy in darkness";
        }
        else {
            lightText = "There's no information about the light level";
        }

        return lightText;
    }
}
