package se.myhappyplants.server.repository;

import se.myhappyplants.server.model.LightCalculator;
import se.myhappyplants.server.model.WaterCalculator;
import se.myhappyplants.shared.Plant;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PlantRepository {

    private Connection conn;
    private LightCalculator lightCalculator;
    private WaterCalculator waterCalculator;

    public PlantRepository(LightCalculator lightCalculator, WaterCalculator waterCalculator){
        this.waterCalculator = waterCalculator;
        this.lightCalculator = lightCalculator;
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
        } /*finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }*/
        return plantList;
    }

    public String[] getMoreInformation(Plant plant) {
        String[] allInfo = new String[4];
        String query = "SELECT genus, light, water_frequency, family FROM species WHERE id = '" + plant.getPlantId() + "';";
        try {
            makeConnection();
            ResultSet resultSet = conn.createStatement().executeQuery(query);
            while (resultSet.next()) {
                String genus = resultSet.getString("genus");
                String light = resultSet.getString("light");
                String family = resultSet.getString("family");
                String waterFrequency = resultSet.getString("water_frequency");
                String lightText = lightCalculator.calculateLightLevel(light);
                String waterText = waterCalculator.calculateWater(waterFrequency);
                allInfo[0] = "Family:\t" + family + "\n";
                allInfo[1] = "Genus:\t" + genus + "\n";
                allInfo[2] = "Light:\t" + lightText + "\n";
                allInfo[3] = "Water:\t" + waterText + "\n";
            }
        } catch (SQLException | UnknownHostException sqlException) {
            System.out.println(sqlException.fillInStackTrace());
            allInfo = null;
        } /*finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }*/
        return allInfo;
    }

    public long getWaterFrequency(String plantId) throws IOException, InterruptedException {
        long waterFrequencyMilli = 0;
        String query = "SELECT water_frequency FROM species WHERE id = '" + plantId + "';";
        try {
            makeConnection();
            ResultSet resultSet = conn.createStatement().executeQuery(query);
            while (resultSet.next()) {
                String waterFrequency = resultSet.getString("water_frequency");
                long week = 604000000l;
                int waterFrequencyInt = Integer.parseInt(waterFrequency);
                if (waterFrequencyInt <= 200) {
                    waterFrequencyMilli = week * 4;
                }
                else if (waterFrequencyInt > 200 && waterFrequencyInt <= 400) {
                    waterFrequencyMilli = week * 3;
                }
                else if (waterFrequencyInt > 400 && waterFrequencyInt <= 600) {
                    waterFrequencyMilli = week * 2;
                }
                else if (waterFrequencyInt > 600 && waterFrequencyInt <= 800) {
                    waterFrequencyMilli = week * 1;
                }
                else if (waterFrequencyInt > 800) {
                    waterFrequencyMilli = week / 2;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } /*finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }*/
        return waterFrequencyMilli;
    }
}
