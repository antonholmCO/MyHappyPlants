package se.myhappyplants.server;

import se.myhappyplants.server.controller.ServerController;
import se.myhappyplants.client.model.WaterCalculator;
import se.myhappyplants.server.services.*;

import java.net.UnknownHostException;
import java.sql.SQLException;

/**
 * Created by: Frida Jacobson, Eric Simonson, Anton Holm, Linn Borgström, Christopher O'Driscoll
 * Updated by: Linn Borgström, 2021-05-13
 */
public class StartServer {
    public static void main(String[] args) throws UnknownHostException, SQLException {
        IConnection connectionMyHappyPlants = new DatabaseConnection("MyHappyPlants");
        IDatabase database = new Database(connectionMyHappyPlants);
        UserRepository userRepository = new UserRepository(database);
        PlantRepository plantRepository = new PlantRepository(new WaterCalculator());
        UserPlantRepository userPlantRepository = new UserPlantRepository(plantRepository, database);
        ServerController serverController = new ServerController(userRepository,userPlantRepository,plantRepository);
        new ServerConnection(2555,serverController);
    }
}
