package se.myhappyplants.server.services;

import se.myhappyplants.client.view.LightTextFormatter;
import se.myhappyplants.client.model.WaterCalculator;
import se.myhappyplants.shared.Plant;
import se.myhappyplants.shared.PlantDetails;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class responsible for calling the database about plants.
 * Created by: Frida Jacobsson
 * Updated by: Christopher O'Driscoll
 */
public class PlantRepository {

    private Connection conn;
    private WaterCalculator waterCalculator;

    public PlantRepository(WaterCalculator waterCalculator) throws UnknownHostException, SQLException {
        this.waterCalculator = waterCalculator;
        this.conn = Driver.getConnection("Species");
    }

    public ArrayList<Plant> getResult(String plantSearch) {
        ArrayList<Plant> plantList = new ArrayList<>();
        String query = "SELECT id, common_name, scientific_name, family, image_url FROM species WHERE scientific_name LIKE ('%" + plantSearch + "%') OR common_name LIKE ('%" + plantSearch + "%');";
        try {
            ResultSet resultSet = conn.createStatement().executeQuery(query);
            while (resultSet.next()) {
                String plantId = resultSet.getString("id");
                String commonName = resultSet.getString("common_name");
                String scientificName = resultSet.getString("scientific_name");
                String familyName = resultSet.getString("family");
                String imageURL = resultSet.getString("image_url");
                plantList.add(new Plant(plantId, commonName, scientificName, familyName, imageURL));
            }
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.fillInStackTrace());
            plantList = null;
        }
        return plantList;
    }

    public PlantDetails getPlantDetails(Plant plant) {
        PlantDetails plantDetails = null;
        String query = "SELECT genus, scientific_name, light, water_frequency, family FROM species WHERE id = '" + plant.getPlantId() + "';";
        try {
            ResultSet resultSet = conn.createStatement().executeQuery(query);
            while (resultSet.next()) {
                String genus = resultSet.getString("genus");
                String scientificName = resultSet.getString("scientific_name");
                String lightText = resultSet.getString("light");
                String waterText = resultSet.getString("water_frequency");
                String family = resultSet.getString("family");

                int light = (isNumeric(lightText)) ? Integer.parseInt(lightText) : -1;
                int water = (isNumeric(waterText)) ? Integer.parseInt(waterText) : -1;

                plantDetails = new PlantDetails(genus, scientificName, light, water, family);
            }
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.fillInStackTrace());
        }
        return plantDetails;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public long getWaterFrequency(String plantId) throws IOException, InterruptedException {
        long waterFrequencyMilli = 0;
        String query = "SELECT water_frequency FROM species WHERE id = '" + plantId + "';";
        try {
            ResultSet resultSet = conn.createStatement().executeQuery(query);
            while (resultSet.next()) {
                int waterFrequency = Integer.parseInt(resultSet.getString("water_frequency"));
                waterFrequencyMilli = waterCalculator.calculateWaterFrequencyForWatering(waterFrequency);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return waterFrequencyMilli;
    }
}
