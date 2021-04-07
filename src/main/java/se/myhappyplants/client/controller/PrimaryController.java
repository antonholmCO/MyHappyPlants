package se.myhappyplants.client.controller;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        StartClient.setRoot("secondary");
    }
}
