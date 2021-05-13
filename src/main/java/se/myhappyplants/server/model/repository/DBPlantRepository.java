package se.myhappyplants.server.model.repository;

import se.myhappyplants.server.controller.Controller;
import se.myhappyplants.shared.DBPlant;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBPlantRepository {

    private Controller controller;
    private Statement statement;

    public DBPlantRepository(Controller controller) throws SQLException, UnknownHostException {
        this.controller = controller;
        Connection conn = Driver.getConnection("Species");
        statement = conn.createStatement();
    }

    public ArrayList<DBPlant> getResult(String plantSearch) throws IOException, InterruptedException {
        ArrayList<DBPlant> plantList = new ArrayList<>();
        try {
            String query = "SELECT id, common_name, scientific_name, family, image_url FROM species WHERE scientific_name LIKE ('%" + plantSearch + "%') OR common_name LIKE ('%" + plantSearch + "%');";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String plantId = resultSet.getString("id");
                String commonName = resultSet.getString("common_name");
                String scientificName = resultSet.getString("scientific_name");
                String familyName = resultSet.getString("family");
                String imageURL = resultSet.getString("image_url");
                //long waterFrequency = resultSet.getLong("water_frequency");
                plantList.add(new DBPlant(plantId, commonName, scientificName, familyName, imageURL));
            }
        }
        catch (SQLException sqlException) {
            return null;
        }
        return plantList;

    }

    public String[] getMoreInformation(DBPlant plant) {
        String[] allInfo = new String[4];
        try {
            String query = "SELECT genus, light, water_frequency, family FROM species WHERE id = '" + plant.getPlantId() + "';";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String genus = resultSet.getString("genus");
                String light = resultSet.getString("light");
                String family = resultSet.getString("family");
                String waterFrequency = resultSet.getString("water_frequency");
                String lightText = controller.calculateLightLevelToString(light);
                String waterText = controller.calculateWaterLevelToString(waterFrequency);
                allInfo[0] = "Family:\t" + family + "\n";
                allInfo[1] = "Genus:\t" + genus + "\n";
                allInfo[2] = "Light:\t" + lightText + "\n";
                allInfo[3] = "Water:\t" + waterText + "\n";

            }
        }
        catch (SQLException sqlException) {
            return null;
        }

        return allInfo;
    }

    public long getWaterFrequency(String plantId) throws IOException, InterruptedException {
        long waterFrequencyMilli = 0;
        try {
            String query = "SELECT water_frequency FROM species WHERE id = '" + plantId + "';";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String waterFrequency = resultSet.getString("water_frequency");
                waterFrequencyMilli = controller.calculateWaterFrequencyForWatering(waterFrequency);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return waterFrequencyMilli;
    }

}
