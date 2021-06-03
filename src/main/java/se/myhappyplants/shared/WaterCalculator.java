package se.myhappyplants.shared;

/**
 * Class that calculated how much water a plant needs from the values from the database
 * Created by: Frida Jacobsson, Eric Simonsson, Susanne Vikström, Linn Borgström
 * Updated by: Frida Jacobsson, 2021-05-12
 */
public class WaterCalculator {

    /**
     * Calculates how often a plant needs water according
     * to minimum precipitation
     *
     * @param waterFrequency minimum mm of precipitation per year
     * @return time in milliseconds between each watering
     */
    public static long calculateWaterFrequencyForWatering(int waterFrequency) {
        long waterFrequencyMilli;
        long week = 604000000L;
        if (waterFrequency <= 200) {
            waterFrequencyMilli = week * 4;
        } else if (waterFrequency <= 400) {
            waterFrequencyMilli = week * 3;
        } else if (waterFrequency <= 600) {
            waterFrequencyMilli = week * 2;
        } else if (waterFrequency <= 800) {
            waterFrequencyMilli = week;
        } else {
            waterFrequencyMilli = week / 2;
        }
        return waterFrequencyMilli;
    }
}
