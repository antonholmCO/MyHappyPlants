package se.myhappyplants.client.controller;

import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        StartClient.setRoot("primary");
    }
}