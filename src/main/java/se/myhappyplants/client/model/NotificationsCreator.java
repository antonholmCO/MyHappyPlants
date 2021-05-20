package se.myhappyplants.client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import se.myhappyplants.shared.Plant;

import java.util.ArrayList;

public class NotificationsCreator {

    public static ObservableList<String> getNotificationsStrings (ArrayList<Plant> currentUserLibrary, ImageView imgNotifications) {

        ObservableList<String> notificationStrings = FXCollections.observableArrayList();
        if (LoggedInUser.getInstance().getUser().areNotificationsActivated()) {
            imgNotifications.setVisible(true);
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
            imgNotifications.setVisible(false);
            notificationStrings = null;
        }
        return notificationStrings;
    }
}
