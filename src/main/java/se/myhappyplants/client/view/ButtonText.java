package se.myhappyplants.client.view;

import javafx.scene.control.ToggleButton;
import se.myhappyplants.client.controller.SettingsTabPaneController;

/**
 * Created by: Linn Borgström, 2021-04-13
 * Updated by: Linn Borgström, 2021-04-13
 */
public class ButtonText {

    public static void setButtonText(ToggleButton tglButton) {
        if(tglButton.isSelected()) {
            tglButton.setText("On");
        }
        else {
            tglButton.setText("Off");
        }
    }
}
