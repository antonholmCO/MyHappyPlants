package se.myhappyplants.client.model;

/**
 * Created by: Frida Jacobsson, Eric Simonsson, Susanne Vikström, Linn Borgström
 * Updated by: Frida Jacobsson, 2021-05-12
 */
public class WaterCalculator {

    public static long calculateWaterFrequencyForWatering(int waterFrequency) {
        long waterFrequencyMilli = 0;

        long week = 604000000l;
        if (waterFrequency <= 200) {
            waterFrequencyMilli = week * 4;
        }
        else if (waterFrequency > 200 && waterFrequency <= 400) {
            waterFrequencyMilli = week * 3;
        }
        else if (waterFrequency > 400 && waterFrequency <= 600) {
            waterFrequencyMilli = week * 2;
        }
        else if (waterFrequency > 600 && waterFrequency <= 800) {
            waterFrequencyMilli = week * 1;
        }
        else if (waterFrequency > 800) {
            waterFrequencyMilli = week / 2;
        }
        return waterFrequencyMilli;
    }
}
