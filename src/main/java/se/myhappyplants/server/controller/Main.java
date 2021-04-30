package se.myhappyplants.server.controller;

import se.myhappyplants.server.model.repository.PlantRepository;
import se.myhappyplants.server.model.repository.UserRepository;
import se.myhappyplants.server.model.service.PlantService;

/**
 * Created by: Frida Jacobson, Eric Simonson, Anton Holm, Linn Borgström, Christopher O'Driscoll
 */
public class Main {
    public static void main(String[] args) throws Exception {

        Controller controller = new Controller();
        new Server(2555, new UserRepository(), new PlantRepository(), new PlantService(controller), controller);

//        för att testa API:et används koden nedan
//        PlantService plantService = new PlantService();
//        plantService.getResult();
    }
}
