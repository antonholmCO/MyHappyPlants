package se.myhappyplants.server;

import se.myhappyplants.server.controller.ServerController;
import se.myhappyplants.client.view.LightTextFormatter;
import se.myhappyplants.client.model.WaterCalculator;
import se.myhappyplants.server.services.PlantRepository;
import se.myhappyplants.server.services.ServerConnection;
import se.myhappyplants.server.services.UserPlantRepository;
import se.myhappyplants.server.services.UserRepository;

/**
 * Created by: Frida Jacobson, Eric Simonson, Anton Holm, Linn Borgström, Christopher O'Driscoll
 * Updated by: Linn Borgström, 2021-05-13
 */
public class StartServer {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        PlantRepository plantRepository = new PlantRepository(new LightTextFormatter(), new WaterCalculator());
        UserPlantRepository userPlantRepository = new UserPlantRepository(plantRepository);
        ServerController serverController = new ServerController(userRepository,userPlantRepository,plantRepository);
        new ServerConnection(2555,serverController);
    }
}
