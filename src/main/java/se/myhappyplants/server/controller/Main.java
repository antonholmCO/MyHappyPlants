package se.myhappyplants.server.controller;

import se.myhappyplants.server.model.repository.UserRepository;
import se.myhappyplants.server.model.service.PlantService;

/**
 * Created by: Frida Jacobson, Eric Simonson, Anton Holm, Linn Borgström, Christopher O'Driscoll
 */
public class Main {
    public static void main(String[] args) throws Exception {
       // UserRepository userRepository = new UserRepository();
       // Controller controller = new Controller((UserRepository) userRepository);
        //Testa logga in och spara användare på DB samt skapa en ny User
        //controller.createNewUser();
        //controller.logIn();

        //new Server(2555, new UserRepository(), new PlantService());
        new Server(2555);

//        för att testa API:et används koden nedan
//        PlantService plantService = new PlantService();
//        plantService.getResult();
    }
}
