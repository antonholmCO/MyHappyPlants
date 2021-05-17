package se.myhappyplants.client.view;

import javafx.scene.control.ToggleButton;
import se.myhappyplants.client.controller.SettingsTabController;

/**
 * Created by: Linn Borgström, 2021-04-13
 * Updated by: Linn Borgström, 2021-04-13
 */
public class ButtonText {

    public static void setNotificationsButtonText(SettingsTabController settingsTabController) {
        ToggleButton tglBtnChangeNotification = settingsTabController.getTglBtnChangeNotification();
        if(tglBtnChangeNotification.isSelected()) {
            tglBtnChangeNotification.setText("On");
        }
        else {
            tglBtnChangeNotification.setText("Off");
        }
    }
}
