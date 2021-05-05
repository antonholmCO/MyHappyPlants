package se.myhappyplants.server.controller;

import se.myhappyplants.server.model.repository.DBPlantRepository;
import se.myhappyplants.server.model.repository.UserPlantRepository;
import se.myhappyplants.server.model.repository.UserRepository;

/**
 * Created by: Frida Jacobson, Eric Simonson, Anton Holm, Linn Borgström, Christopher O'Driscoll
 */
public class Main {
    public static void main(String[] args) throws Exception {

        Controller controller = new Controller();
        new Server(2555, new UserRepository(), new UserPlantRepository(controller), new DBPlantRepository(controller), controller);

    }
}
