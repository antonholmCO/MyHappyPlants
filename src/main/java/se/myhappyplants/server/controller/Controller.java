package se.myhappyplants.server.controller;


import se.myhappyplants.shared.User;
import se.myhappyplants.server.model.repository.UserRepository;

import java.util.Scanner;
/*

 */

/**
 * Test-class for testing DB, logging in and create account
 * Created by: Linn Borgström
 * Updated by: Frida
 */

public class Controller {
    private UserRepository userRepository;

    public Controller(UserRepository userRepository) {
        this.userRepository=userRepository;
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
}
