package se.myhappyplants.server.controller;

import se.myhappyplants.server.model.LightCalculator;
import se.myhappyplants.server.model.WaterCalculator;
import se.myhappyplants.server.repository.PlantRepository;
import se.myhappyplants.server.repository.UserPlantRepository;
import se.myhappyplants.server.repository.UserRepository;

/**
 * Created by: Frida Jacobson, Eric Simonson, Anton Holm, Linn Borgström, Christopher O'Driscoll
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Controller controller = new Controller();
        LightCalculator lightCalculator = new LightCalculator();
        WaterCalculator waterCalculator = new WaterCalculator();
        new Server(2555, new UserRepository(), new UserPlantRepository(lightCalculator, waterCalculator), new PlantRepository(lightCalculator, waterCalculator), controller);

    }
}
