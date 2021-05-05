package se.myhappyplants.server.model.repository;

import se.myhappyplants.server.controller.Controller;
import se.myhappyplants.shared.DBPlant;
import se.myhappyplants.shared.User;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class responsible for calling the database about plants.
 * Created by: Linn Borgström
 * Updated by: Frida Jacobsson
 */
public class UserPlantRepository {

    private Statement statement;
    private Controller controller;
    private DBPlantRepository dbPlantRepository;

    /**
     * Constructor that creates a connection to the database.
     *
     * @throws SQLException
     * @throws UnknownHostException
     */
    public UserPlantRepository(Controller controller) throws SQLException, UnknownHostException {
        this.controller = controller;
        dbPlantRepository = new DBPlantRepository(controller);
        Connection conn = Driver.getConnection("MyHappyPlants");
        statement = conn.createStatement();
    }

    /**
     * Method to save a new plant in database
     * Author: Frida Jacobsson
     * Updated Frida Jacobsson 2021-04-29
     *
     * @param plant an instance of a newly created plant by user
     * @return a boolean value, true if the plant was stored successfully
     */

    public boolean savePlant(User user, DBPlant plant) {
        boolean success = false;
        String sqlSafeNickname = plant.getNickname().replace("'", "''");
        String query = "INSERT INTO Plant (user_id, nickname, plant_id, last_watered) values (" + user.getUniqueId() + ", '" + sqlSafeNickname + "', '" + plant.getPlantId() + "', '" + plant.getLastWatered() + "')";
        try {
            CallableStatement callableStatement = Driver.getConnection("MyHappyPlants").prepareCall(query);
            callableStatement.execute();
            success = true;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Method that returns all the plants connected to the logged in user.
     * Author: Linn Borgström,
     * Updated by: Frida Jacobsson
     *
     * @return an arraylist if plants stored in the database
     */
    public ArrayList<DBPlant> getUserLibrary(User user) {
        ArrayList<DBPlant> plantList = new ArrayList<DBPlant>();
        try {
            String query = "SELECT nickname, plant_id, last_watered FROM [Plant] WHERE user_id =" + user.getUniqueId() + ";";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String nickname = resultSet.getString("nickname");
                String plantId = resultSet.getString("plant_id");
                Date lastWatered = resultSet.getDate("last_watered");
                long waterFrequency = dbPlantRepository.getWaterFrequency(plantId);
                plantList.add(new DBPlant(nickname, plantId, lastWatered, waterFrequency));
            }
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.fillInStackTrace());
            return null;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return plantList;
    }

    /**
     * Method that returns one specific plant based on nickname.
     *
     * @param nickname
     * @return an instance of a specific plant from the database, null if no plant with the specific nickname exists
     */
    public DBPlant getPlant(User user, String nickname) {
        try {
            String sqlSafeNickname = nickname.replace("'", "''");
            String query = "SELECT nickname, plant_id, last_watered FROM [Plant] WHERE user_id =" + user.getUniqueId() + "AND nickname = '" + sqlSafeNickname + "';";
            ResultSet resultSet = statement.executeQuery(query);
            String plantID = resultSet.getString("plant_id");
            Date lastWatered = resultSet.getDate("last_watered");
            return new DBPlant(nickname, plantID, lastWatered);
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.fillInStackTrace());
            return null;
        }
    }

    /**
     * Method that makes a query to delete a specific plant from table Plant
     *
     * @param user     the user that owns the plant
     * @param nickname nicknname of the plant
     * @return boolean result depending on the result, false if exception
     */
    public boolean deletePlant(User user, String nickname) {
        try {
            String sqlSafeNickname = nickname.replace("'", "''");
            String query = "DELETE FROM [plant] WHERE user_id =" + user.getUniqueId() + "AND nickname = '" + sqlSafeNickname + "';";
            statement.executeUpdate(query);
            return true;
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException);
            return false;
        }
    }

    /**
     * Method that makes a query to change the last watered date of a specific plant in table Plant
     *
     * @param user     the user that owns the plant
     * @param nickname nickname of the plant
     * @param date     new data to change to
     * @return boolean result depending on the result, false if exception
     */
    public boolean changeLastWatered(User user, String nickname, LocalDate date) {
        try {
            String sqlSafeNickname = nickname.replace("'", "''");
            String query = "UPDATE [Plant] SET last_watered = '" + date + "' WHERE user_id = " + user.getUniqueId() + " AND nickname = '" + sqlSafeNickname + "';";
            statement.executeUpdate(query);
            return true;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    public boolean changeNickname(User user, String nickname, String newNickname) {
        try {
            String sqlSafeNickname = nickname.replace("'", "''");
            String sqlSafeNewNickname = newNickname.replace("'", "''");
            String query = "UPDATE [Plant] SET nickname = '" + sqlSafeNewNickname + "' WHERE user_id = " + user.getUniqueId() + " AND nickname = '" + sqlSafeNickname + "';";
            statement.executeUpdate(query);
            return true;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }
}