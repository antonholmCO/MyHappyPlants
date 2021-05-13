package se.myhappyplants.server.controller;

/**
 * Created by: Linn Borgström
 * Updated by: Linn Borgström, Eric Simonson, Susanne Vikström
 */

public class Controller {

    public String calculateLightLevelToString(String light) {
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

    public String calculateWaterLevelToString(String minWater) {
        String waterText;

        int waterFrequencyInt = Integer.parseInt(minWater);
        if (waterFrequencyInt <= 200) {
            waterText = "Needs water 4 times a week";
        }
        else if (waterFrequencyInt > 200 && waterFrequencyInt <= 400) {
            waterText = "Needs water 3 times a week";
        }
        else if (waterFrequencyInt > 400 && waterFrequencyInt <= 600) {
            waterText = "Needs water 2 times a week";
        }
        else if (waterFrequencyInt > 600 && waterFrequencyInt <= 800) {
            waterText = "Needs water 1 times a week";
        }
        else if (waterFrequencyInt > 800) {
            waterText = "Needs water every other week";
        }
        else {
            waterText = "There's no information about the water level";
        }

        return waterText;

    }

    public long calculateWaterFrequencyForWatering(String waterFrequency){
        long waterFrequencyMilli = 0;

        long week = 604000000l;
        int waterFrequencyInt = Integer.parseInt(waterFrequency);
        if (waterFrequencyInt <= 200) {
            waterFrequencyMilli = week * 4;
        }
        else if (waterFrequencyInt > 200 && waterFrequencyInt <= 400) {
            waterFrequencyMilli = week * 3;
        }
        else if (waterFrequencyInt > 400 && waterFrequencyInt <= 600) {
            waterFrequencyMilli = week * 2;
        }
        else if (waterFrequencyInt > 600 && waterFrequencyInt <= 800) {
            waterFrequencyMilli = week * 1;
        }
        else if (waterFrequencyInt > 800) {
            waterFrequencyMilli = week / 2;
        }

        return waterFrequencyMilli;

    }
}
