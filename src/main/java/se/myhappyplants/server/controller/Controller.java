package se.myhappyplants.server.controller;


import se.myhappyplants.shared.User;
import se.myhappyplants.server.model.repository.UserRepository;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by: Linn Borgström
 * Updated by: Linn Borgström, Eric Simonson, Susanne Vikström
 */

public class Controller {
    private UserRepository userRepository;

    public Controller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Controller() {

    }

    public String calculateLightLevel(String light) {
        String lightText = light;
        if (light.equals("10")) {
            lightText = "Is happy in direct sunlight";
        } else if (light.equals("9")) {
            lightText = "Is happy in full daylight with a lot of direct sunlight";
        } else if (light.equals("8")) {
            lightText = "Is happy in full daylight with some direct sunlight";
        } else if (light.equals("7")) {
            lightText = "Is happy in full daylight without direct sunlight";
        } else if (light.equals("6") || light.equals("5")) {
            lightText = "Is not happy to be placed in direct sunlight but still needs som light";
        } else if (light.equals("4") || light.equals("3")) {
            lightText = "Is happy to be in darker areas";
        } else if (light.equals("1") || light.equals("2")) {
            lightText = "Is happy in darkness";
        } else {
            lightText = "There's no information about the light level";
        }

        return lightText;
    }

    public String calculateWater(String minWater) {
        String waterText;

        int waterFrequencyInt = Integer.parseInt(minWater);
        if (waterFrequencyInt <= 200) {
            waterText = "Needs water 4 times a week";
        } else if (waterFrequencyInt > 200 && waterFrequencyInt <= 400) {
            waterText = "Needs water 3 times a week";
        } else if (waterFrequencyInt > 400 && waterFrequencyInt <= 600) {
            waterText = "Needs water 2 times a week";
        } else if (waterFrequencyInt > 600 && waterFrequencyInt <= 800) {
            waterText = "Needs water 1 times a week";
        } else if (waterFrequencyInt > 800) {
            waterText = "Needs water every other week";
        } else {
            waterText = "There's no information about the water level";
        }

        return waterText;

    }
}
