package se.myhappyplants.client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import se.myhappyplants.shared.Plant;

import java.util.ArrayList;

public class NotificationsCreator {

    public static ObservableList<String> getNotificationsStrings (ArrayList<Plant> currentUserLibrary) {

        ObservableList<String> notificationStrings = FXCollections.observableArrayList();
        if (LoggedInUser.getInstance().getUser().areNotificationsActivated()) {
            int plantsThatNeedWater = 0;
            for (Plant plant : currentUserLibrary) {
                if (plant.getProgress() < 0.25) {
                    plantsThatNeedWater++;
                    notificationStrings.add(plant.getNickname() + " needs water");
                }
            }
            if (plantsThatNeedWater == 0) {
                notificationStrings.add("All your plants are watered");
            }
        } else {
            notificationStrings.add("");
        }
        return notificationStrings;
    }
}
