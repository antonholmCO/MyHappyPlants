package se.myhappyplants.server.controller;


import se.myhappyplants.server.model.service.PlantService;
import se.myhappyplants.shared.User;
import se.myhappyplants.server.model.repository.UserRepository;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Test-class for testing DB, logging in and create account
 * Created by: Linn Borgström
 * Updated by: Frida Jacobsson
 */

public class Controller {
    private UserRepository userRepository;
    private PlantService plantService;

    public Controller(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

    public Controller(PlantService plantService) {
        this.plantService=plantService;
    }

    public Controller() {

    }

    public void createNewUser() {
        Scanner scan = new Scanner(System.in);
        System.out.println( "Please enter email:" );
        String email = scan.nextLine();
        System.out.println( "Please enter your first name:" );
        String firstName = scan.nextLine();
        System.out.println( "Please enter password:" );
        String password = scan.nextLine();
        User user = new User(email, firstName, password, true);
        boolean ok = userRepository.saveUser(user);
        if (ok) {
            System.out.println("Yey! You have a new account at My Happy Plants.");
        }
        else {
            System.out.println("Something went wrong. Please call our support");
        }
    }

    public void logIn() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter email:");
        String email = scan.nextLine();
        System.out.println("Please enter password:");
        String password = scan.nextLine();

        boolean isVerifiedUser= userRepository.checkLogin(email,password);
        if(isVerifiedUser){
            System.out.println("Successfull login! Welcome to your plant library");
        }
        else {
            System.out.println("Oups! No account found or incorrect password. Try again our call our support");
        }
    }

    public void deleteAccount() throws SQLException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter email:");
        String email = scan.nextLine();
        System.out.println("Please enter password:");
        String password = scan.nextLine();

        boolean isDeleted = userRepository.deleteAccount(email,password);
        if(isDeleted) {
            System.out.println("succesfully deleted in text");
        }
        else {
            System.out.println("not successfully deleted in text");
        }
    }

    public String calculateLightLevel(int light) {
        String lightText = String.valueOf(light);
        if(lightText == null) {
            lightText = "There's no information about the light level";
        }
        //if(light > )
        return null;
    }

    public String calculateWater(String minWater) {
        String waterText = null;
        String parsedWaterFreq = minWater.substring(4, minWater.length()-3);
        //todo find better calculation for how often each plant needs watering
        //1 day = 86 000 000
        //min water = 200mm/year -> 4 weeks
        //min water = 1000mm/year -> 1 week
        int waterFrequencyInt = Integer.parseInt(parsedWaterFreq);
        if(waterFrequencyInt<=200) {
            waterText = "Needs water 4 times a week";
        }
        else if(waterFrequencyInt>200 && waterFrequencyInt<=400) {
            waterText = "Needs water 3 times a week";
        }
        else if(waterFrequencyInt>400 && waterFrequencyInt<=600) {
            waterText = "Needs water 2 times a week";
        }
        else if(waterFrequencyInt>600 && waterFrequencyInt<=800) {
            waterText = "Needs water 1 times a week";
        }
        else if(waterFrequencyInt>800) {
            waterText = "Needs water every other week";
        }
        return waterText;

    }
}
