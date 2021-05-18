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
            waterText = "Needs water once a month";
        }
        else if (waterFrequencyInt <= 400) {
            waterText = "Needs water every fortnight";
        }
        else if (waterFrequencyInt <= 600) {
            waterText = "Needs water once a week";
        }
        else if (waterFrequencyInt <= 800) {
            waterText = "Needs water once every 5 days";
        }
        else {
            waterText = "There's no information about the water level";
        }
        return waterText;
    }

    public long calculateWaterFrequencyForWatering(String waterFrequency){
        long waterFrequencyMilli;

        long week = 604000000L;
        int waterFrequencyInt = Integer.parseInt(waterFrequency);
        if (waterFrequencyInt <= 200) {
            waterFrequencyMilli = week * 4;
        }
        else if (waterFrequencyInt <= 400) {
            waterFrequencyMilli = week * 3;
        }
        else if (waterFrequencyInt <= 600) {
            waterFrequencyMilli = week * 2;
        }
        else if (waterFrequencyInt <= 800) {
            waterFrequencyMilli = week;
        }
        else {
            waterFrequencyMilli = week / 2;
        }
        return waterFrequencyMilli;
    }
}
