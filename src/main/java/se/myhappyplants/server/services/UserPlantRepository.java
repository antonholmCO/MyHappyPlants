package se.myhappyplants.server.services;

import se.myhappyplants.shared.Plant;
import se.myhappyplants.shared.User;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class responsible for calling the database about a users library.
 * Created by: Linn Borgström
 * Updated by: Christopher O'Driscoll
 */
public class UserPlantRepository {

    private Connection conn;
    private PlantRepository plantRepository;

    /**
     * Constructor that creates a connection to the database.
     *
     * @throws SQLException
     * @throws UnknownHostException
     */
    public UserPlantRepository(PlantRepository plantRepository){
        this.plantRepository = plantRepository;
    }

    /**
     * makes a new connection to the database
     * @throws SQLException
     * @throws UnknownHostException
     */
    private void makeConnection() throws SQLException, UnknownHostException {
        if (conn==null) {
            System.out.println("New connection to User Library");
            conn = Driver.getConnection("MyHappyPlants");

        }
        else if (conn.isClosed()) {
            System.out.println("User Library connection closed, making new connection");
            conn = Driver.getConnection("MyHappyPlants");
        }
        else {
            System.out.println("User Library connection active, reusing");
        }
    }
    /**
     * Method to save a new plant in database
     * Author: Frida Jacobsson
     * Updated Frida Jacobsson 2021-04-29
     *
     * @param plant an instance of a newly created plant by user
     * @return a boolean value, true if the plant was stored successfully
     */

    public boolean savePlant(User user, Plant plant) {
        boolean success = false;
        String sqlSafeNickname = plant.getNickname().replace("'", "''");
        String query = "INSERT INTO Plant (user_id, nickname, plant_id, last_watered) values (" + user.getUniqueId() + ", '" + sqlSafeNickname + "', '" + plant.getPlantId() + "', '" + plant.getLastWatered() + "')";
        try {
            makeConnection();
            conn.prepareCall(query).execute();
            success = true;
        } catch (SQLException | UnknownHostException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
    public ArrayList<Plant> getUserLibrary(User user) {
        ArrayList<Plant> plantList = new ArrayList<Plant>();
        String query = "SELECT nickname, plant_id, last_watered FROM [Plant] WHERE user_id =" + user.getUniqueId() + ";";
        try {
            makeConnection();
            ResultSet resultSet = conn.createStatement().executeQuery(query);
            while (resultSet.next()) {
                String nickname = resultSet.getString("nickname");
                String plantId = resultSet.getString("plant_id");
                Date lastWatered = resultSet.getDate("last_watered");
                long waterFrequency = plantRepository.getWaterFrequency(plantId);
                plantList.add(new Plant(nickname, plantId, lastWatered, waterFrequency));
            }
        } catch (SQLException | IOException | InterruptedException exception) {
            System.out.println(exception.fillInStackTrace());
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return plantList;
    }

    /**
     * Method that returns one specific plant based on nickname.
     *
     * @param nickname
     * @return an instance of a specific plant from the database, null if no plant with the specific nickname exists
     */
    public Plant getPlant(User user, String nickname) {
        Plant plant = null;
        String sqlSafeNickname = nickname.replace("'", "''");
        String query = "SELECT nickname, plant_id, last_watered FROM [Plant] WHERE user_id =" + user.getUniqueId() + "AND nickname = '" + sqlSafeNickname + "';";
        try {
            makeConnection();
            ResultSet resultSet = conn.createStatement().executeQuery(query);
            String plantID = resultSet.getString("plant_id");
            Date lastWatered = resultSet.getDate("last_watered");
            plant = new Plant(nickname, plantID, lastWatered);
        } catch (SQLException | UnknownHostException sqlException) {
            System.out.println(sqlException.fillInStackTrace());
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return plant;
    }

    /**
     * Method that makes a query to delete a specific plant from table Plant
     *
     * @param user     the user that owns the plant
     * @param nickname nickname of the plant
     * @return boolean result depending on the result, false if exception
     */
    public boolean deletePlant(User user, String nickname) {
        boolean plantDeleted = false;
        String sqlSafeNickname = nickname.replace("'", "''");
        String query = "DELETE FROM [plant] WHERE user_id =" + user.getUniqueId() + "AND nickname = '" + sqlSafeNickname + "';";
        try {
            makeConnection();
            conn.createStatement().executeUpdate(query);
            plantDeleted = true;
        } catch (SQLException | UnknownHostException sqlException) {
            System.out.println(sqlException);
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return plantDeleted;
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
        boolean dateChanged = false;
        String sqlSafeNickname = nickname.replace("'", "''");
        String query = "UPDATE [Plant] SET last_watered = '" + date + "' WHERE user_id = " + user.getUniqueId() + " AND nickname = '" + sqlSafeNickname + "';";
        try {
            makeConnection();
            conn.createStatement().executeUpdate(query);
            dateChanged = true;
        } catch (SQLException | UnknownHostException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return dateChanged;
    }

    public boolean changeNickname(User user, String nickname, String newNickname) {
        boolean nicknameChanged = false;
        String sqlSafeNickname = nickname.replace("'", "''");
        String sqlSafeNewNickname = newNickname.replace("'", "''");
        String query = "UPDATE [Plant] SET nickname = '" + sqlSafeNewNickname + "' WHERE user_id = " + user.getUniqueId() + " AND nickname = '" + sqlSafeNickname + "';";
        try {
            makeConnection();
            conn.createStatement().executeUpdate(query);
            nicknameChanged = true;
        } catch (SQLException | UnknownHostException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return nicknameChanged;
    }
}