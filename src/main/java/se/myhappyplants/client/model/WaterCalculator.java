package se.myhappyplants.client.model;

/**
 * Created by: Frida Jacobsson, Eric Simonsson, Susanne Vikström, Linn Borgström
 * Updated by: Frida Jacobsson, 2021-05-12
 */
public class WaterCalculator {

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
