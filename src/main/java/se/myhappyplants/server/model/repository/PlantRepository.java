package se.myhappyplants.server.model.repository;

import se.myhappyplants.server.model.service.PlantService;
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
public class PlantRepository implements IPlantRepository {

    private Statement statement;

    /**
     * Constructor that creates a connection to the database.
     *
     * @throws SQLException
     * @throws UnknownHostException
     */
    public PlantRepository() throws SQLException, UnknownHostException {
        Connection conn = Driver.getConnection();
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
    @Override
    public boolean savePlant(User user, DBPlant plant) {
        boolean success = false;
        String sqlSafeNickname = plant.getNickname().replace("'", "''");
        String query = "INSERT INTO Plant (user_id, nickname, api_url, last_watered) values (" + user.getUniqueId() + ", '" + sqlSafeNickname + "', '" + plant.getURL() + "', '" + plant.getLastWatered() + "')";
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
     * Method that returns all the plants connected to the logged in user.
     * Author: Linn Borgström,
     * Updated by: Frida Jacobsson
     *
     * @return an arraylist if plants stored in the database
     */
    public ArrayList<DBPlant> getUserLibrary(User user) {
        ArrayList<DBPlant> plantList = new ArrayList<DBPlant>();
        try {
            String query = "SELECT nickname, api_url, last_watered FROM [Plant] WHERE user_id =" + user.getUniqueId() + ";";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String nickname = resultSet.getString("nickname");
                String APIUrl = resultSet.getString("api_url");
                Date lastWatered = resultSet.getDate("last_watered");
                PlantService plantService = new PlantService();
                long waterFrequency = plantService.getWaterFrequency(APIUrl);
                plantList.add(new DBPlant(nickname, APIUrl, lastWatered, waterFrequency));
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
            String query = "SELECT nickname, api_url, last_watered FROM [Plant] WHERE user_id =" + user.getUniqueId() + "AND nickname = '" + sqlSafeNickname + "';";
            ResultSet resultSet = statement.executeQuery(query);
            String APIUrl = resultSet.getString(4);
            Date lastWatered = resultSet.getDate(5);
            return new DBPlant(nickname, APIUrl, lastWatered);
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