package se.myhappyplants.client.view;

import javafx.scene.control.ToggleButton;
import se.myhappyplants.client.controller.SettingsTabPaneController;

/**
 * Class to set the text on the button
 * Created by: Linn Borgström, 2021-04-13
 * Updated by: Linn Borgström, 2021-04-13
 */
public class ButtonText {
    /**
     * Static method to set the text on the toggleButton
     * @param tglButton the button to change
     */
    public static void setButtonText(ToggleButton tglButton) {
        if(tglButton.isSelected()) {
            tglButton.setText("On");
        }
        else {
            tglButton.setText("Off");
        }
    }
}
