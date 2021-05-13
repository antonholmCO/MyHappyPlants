package se.myhappyplants.server.controller;

import se.myhappyplants.server.model.LightCalculator;
import se.myhappyplants.server.model.WaterCalculator;
import se.myhappyplants.server.services.PlantRepository;
import se.myhappyplants.server.services.UserPlantRepository;
import se.myhappyplants.server.services.UserRepository;

/**
 * Created by: Frida Jacobson, Eric Simonson, Anton Holm, Linn Borgström, Christopher O'Driscoll
 */
public class StartServer {
    public static void main(String[] args) throws Exception {
        Controller controller = new Controller();
        PlantRepository plantRepository = new PlantRepository(new LightCalculator(), new WaterCalculator());
        new Server(2555, new UserRepository(), plantRepository, new UserPlantRepository(plantRepository));
    }
}
