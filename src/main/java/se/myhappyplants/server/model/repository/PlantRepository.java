package se.myhappyplants.server.model.repository;


import se.myhappyplants.server.model.DBPlant;
import se.myhappyplants.server.model.User;

import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
/**
 * Class for calling DB to get saved plants or save new plants.
 * Version 1. Author: Frida Jacobsson.
 * Version 2. Author: Linn Borgström.
 * Version 3. Author Frida Jacobsson
 */
public class PlantRepository implements IPlantRepository {

    private Statement statement;
    private User user;

    public PlantRepository(User user) throws SQLException, UnknownHostException {
        Connection conn = Driver.getConnection();
        statement = conn.createStatement();
        this.user = user;
    }

    /**
     * Method that saves a new plant
     * Author: Frida Jacobsson
     * @param plant
     */
    @Override
    public void savePlant(DBPlant plant) {
        //INSERT INTO Plant (plant.getUsername, plant.getNickname, plant.getURL, plant.getLastWatered);
    }

    /**
     * Method that return an arraylist of all the plants connected to the logged in user.
     * Author: Version 1.0 Linn Borgström, version 2.0 Frida Jacobsson
     * @return
     */
    @Override
    public ArrayList<DBPlant> getAllPlants() {
        ArrayList<DBPlant> plantList = new ArrayList<DBPlant>();
        try {
            String query = "SELECT nickname, api_url, last_watered FROM [Plant] WHERE user_id =" +user.getUniqueId() +";";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                String nickname = resultSet.getString(3);
                String APIUrl = resultSet.getString(4);
                Date lastWatered = resultSet.getDate(5);
                plantList.add(new DBPlant(nickname, APIUrl, lastWatered));
            }
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.fillInStackTrace());
            return null;
        }
        return plantList;
    }

    /**
     * Method that returns one specific plant based on nickname.
     * Not sure if it will be needed but in case of!
     * @param nickname
     * @return
     */
    @Override
    public DBPlant getPlant(String nickname) {
        try {
            String query = "SELECT nickname, api_url, last_watered FROM [Plant] WHERE user_id =" + user.getUniqueId() +"AND nickname =" +nickname;
            ResultSet resultSet = statement.executeQuery(query);
            String APIUrl = resultSet.getString(4);
            Date lastWatered = resultSet.getDate(5);
            return new DBPlant(nickname,APIUrl,lastWatered);
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.fillInStackTrace());
            return null;
        }
    }
}