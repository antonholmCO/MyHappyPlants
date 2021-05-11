package se.myhappyplants.server.controller;


import se.myhappyplants.server.repository.UserRepository;

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

}
