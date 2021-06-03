package se.myhappyplants.client.view;

/**
 * Class to format the text of the water infor from the database
 * Created by: Frida Jacobsson
 * Updated by: Frida Jacobsson
 */
public class WaterTextFormatter {

    public static String getWaterString(long waterInMilli) {
        String waterText;

        if (waterInMilli <= 200) {
            waterText = "Needs water 4 times a week";
        }
        else if (waterInMilli > 200 && waterInMilli <= 400) {
            waterText = "Needs water 3 times a week";
        }
        else if (waterInMilli > 400 && waterInMilli <= 600) {
            waterText = "Needs water 2 times a week";
        }
        else if (waterInMilli > 600 && waterInMilli <= 800) {
            waterText = "Needs water 1 times a week";
        }
        else if (waterInMilli > 800) {
            waterText = "Needs water every other week";
        }
        else {
            waterText = "There's no information about the water level";
        }
        return waterText;
    }
}
