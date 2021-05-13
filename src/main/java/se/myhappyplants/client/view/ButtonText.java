package se.myhappyplants.client.view;

import javafx.scene.control.ToggleButton;

public class ButtonText {

    public static void setNotificationsButtonText() {
        ToggleButton tglBtnChangeNotification = new ToggleButton();
        if(tglBtnChangeNotification.isSelected()) {
            tglBtnChangeNotification.setText("On");
        }
        else {
            tglBtnChangeNotification.setText("Off");
        }
    }
}
