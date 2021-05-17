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
    private LightTextFormatter lightTextFormatter;
    private WaterCalculator waterCalculator;

    public PlantRepository(LightTextFormatter lightTextFormatter, WaterCalculator waterCalculator){
        this.waterCalculator = waterCalculator;
        this.lightTextFormatter = lightTextFormatter;
    }

    /**
     * Makes a new connection to the database
     * @throws SQLException
     * @throws UnknownHostException
     */
    private void makeConnection() throws SQLException, UnknownHostException {
        if (conn==null) {
            System.out.println("New connection to Species");
            conn = Driver.getConnection("Species");
        }
        else if (conn.isClosed()) {
            System.out.println("Species connection closed, making new connection");
            conn = Driver.getConnection("Species");
        }
        else {
            System.out.println("Species connection active, reusing");
        }
    }

    public ArrayList<Plant> getResult(String plantSearch){
        ArrayList<Plant> plantList = new ArrayList<>();
        String query = "SELECT id, common_name, scientific_name, family, image_url FROM species WHERE scientific_name LIKE ('%" + plantSearch + "%') OR common_name LIKE ('%" + plantSearch + "%');";
        try {
            makeConnection();
            ResultSet resultSet = conn.createStatement().executeQuery(query);
            while (resultSet.next()) {
                String plantId = resultSet.getString("id");
                String commonName = resultSet.getString("common_name");
                String scientificName = resultSet.getString("scientific_name");
                String familyName = resultSet.getString("family");
                String imageURL = resultSet.getString("image_url");
                //long waterFrequency = resultSet.getLong("water_frequency");
                plantList.add(new Plant(plantId, commonName, scientificName, familyName, imageURL));
            }
        } catch (SQLException | UnknownHostException sqlException) {
            System.out.println(sqlException.fillInStackTrace());
            plantList = null;
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return plantList;
    }

    public PlantDetails getPlantDetails(Plant plant) {
        PlantDetails plantDetails = null;
        String query = "SELECT genus, scientific_name, light, water_frequency, family FROM species WHERE id = '" + plant.getPlantId() + "';";
        try {
            makeConnection();
            ResultSet resultSet = conn.createStatement().executeQuery(query);
            while (resultSet.next()) {
                String genus = resultSet.getString("genus");
                String scientificName = resultSet.getString("scientific_name");
                String light = resultSet.getString("light");
                String waterFrequency = resultSet.getString("water_frequency");
                String family = resultSet.getString("family");
               plantDetails = new PlantDetails(genus, scientificName, light, waterFrequency, family);
            }
        } catch (SQLException | UnknownHostException sqlException) {
            System.out.println(sqlException.fillInStackTrace());
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return plantDetails;
    }

    public long getWaterFrequency(String plantId) throws IOException, InterruptedException {
        long waterFrequencyMilli = 0;
        String query = "SELECT water_frequency FROM species WHERE id = '" + plantId + "';";
        try {
            makeConnection();
            ResultSet resultSet = conn.createStatement().executeQuery(query);
            while (resultSet.next()) {
                String waterFrequency = resultSet.getString("water_frequency");
                waterFrequencyMilli = waterCalculator.calculateWaterFrequencyForWatering(waterFrequency);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return waterFrequencyMilli;
    }

//
//    public String[] getMoreInformationOnLibraryPlants(Plant plant) {
//        String[] allInfo = new String[6];
//        try {
//            String query = "select common_name,scientific_name,genus,family,light,water_frequency from species\n" +
//                    "where id = " + plant.getPlantId();
//            makeConnection();
//            ResultSet resultSet = conn.createStatement().executeQuery(query);
//
//            while (resultSet.next()) {
//                String commonName = resultSet.getString("common_name");
//                String scientificName = resultSet.getString("scientific_name");
//                String genus = resultSet.getString("genus");
//                String family = resultSet.getString("family");
//                String light = resultSet.getString("light");
//                String waterFrequency = resultSet.getString("water_frequency");
//                String lightText = lightTextFormatter.calculateLightLevelToString(light);
//                String waterText = WaterCalculator.calculateWaterLevelToString(waterFrequency);
//                allInfo[0] = "Common name:\t" + commonName + "\n";
//                allInfo[1] = "Scientific name:\t" + scientificName + "\n";
//                allInfo[2] = "Genus:\t" + genus + "\n";
//                allInfo[3] = "Family:\t" + family + "\n";
//                allInfo[4] = "Light:\t" + lightText + "\n";
//                allInfo[5] = "Water:\t" + waterText + "\n";
//            }
//        }
//        catch (SQLException | UnknownHostException sqlException) {
//            System.out.println(sqlException.fillInStackTrace());
//            return null;
//        }
//        finally {
//            try {
//                conn.close();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
//        return allInfo;
//    }
}
