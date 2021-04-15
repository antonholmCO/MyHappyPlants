package se.myhappyplants.server.model.repository;


import se.myhappyplants.client.model.LoggedInUser;
import se.myhappyplants.shared.DBPlant;
import se.myhappyplants.shared.User;

import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by: Frida Jacobsson, Linn Borgström
 * Updated by:
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
     * Updated 2021-04-15 Christopher
     * @param plant
     * @return
     */
    @Override
    public boolean savePlant(DBPlant plant) {
        boolean success = false;
        String query = "INSERT INTO Plant (user_id, nickname, api_url, last_watered) values ('" + user.getUniqueId() + "', '" + plant.getNickname() + "', '" + plant.getURL() +"', '" + plant.getLastWatered() +"')";
        try {
            CallableStatement callableStatement = Driver.getConnection().prepareCall(query);
            callableStatement.execute();
            success = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return success;
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
                String nickname = resultSet.getString("nickname");
                String APIUrl = resultSet.getString("api_url");
                Date lastWatered = resultSet.getDate("last_watered");
                System.out.println("date format of last watered = " + lastWatered);
                plantList.add(new DBPlant(nickname, APIUrl, lastWatered.toString()));
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
            System.out.println("date format oflast watered = " + lastWatered);
            return new DBPlant(nickname,APIUrl,lastWatered.toString());
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.fillInStackTrace());
            return null;
        }
    }
}