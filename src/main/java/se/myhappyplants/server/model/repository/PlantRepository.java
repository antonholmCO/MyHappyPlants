package se.myhappyplants.server.model.repository;

import se.myhappyplants.server.model.service.PlantService;
import se.myhappyplants.shared.DBPlant;
import se.myhappyplants.shared.User;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Class responsable for calling the database about plants.
 * Created by: Linn Borgström
 * Updated by: Frida Jacobsson
 */
public class PlantRepository implements IPlantRepository {

  private Statement statement;
  private User user;

  /**
   * Constructor that creates a connection to the database.
   *
   * @param user sets the logged in user
   * @throws SQLException
   * @throws UnknownHostException
   */
  public PlantRepository(User user) throws SQLException, UnknownHostException {
    Connection conn = Driver.getConnection();
    statement = conn.createStatement();
    this.user = user;
  }

  /**
   * Method to save a new plant in database
   * Author: Frida Jacobsson
   * Updated 2021-04-15 Christopher
   *
   * @param plant an instance of a newly created plant by user
   * @return a boolean value, true if the plant was stored successfully
   */
  @Override
  public boolean savePlant(DBPlant plant) {
    boolean success = false;
    String query = "INSERT INTO Plant (user_id, nickname, api_url, last_watered) values ('" + user.getUniqueId() + "', '" + plant.getNickname() + "', '" + plant.getURL() + "', '" + plant.getLastWatered() + "')";
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
  @Override
  public ArrayList<DBPlant> getAllPlants() {
    ArrayList<DBPlant> plantList = new ArrayList<DBPlant>();
    try {
      String query = "SELECT nickname, api_url, last_watered FROM [Plant] WHERE user_id =" + user.getUniqueId() + ";";
      ResultSet resultSet = statement.executeQuery(query);
      while (resultSet.next()) {
        String nickname = resultSet.getString("nickname");
        String APIUrl = resultSet.getString("api_url");
        Date lastWatered = resultSet.getDate("last_watered");
        long millis = lastWatered.getTime();
        PlantService plantService = new PlantService();
        long waterFrequency = plantService.getWaterFrequency(APIUrl);
        plantList.add(new DBPlant(nickname, APIUrl, lastWatered, waterFrequency));
      }
    } catch (SQLException sqlException) {
      System.out.println(sqlException.fillInStackTrace());
      return null;
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return plantList;
  }

  /**
   * Method that returns one specific plant based on nickname.
   * TODO Not sure if it will be needed but in case of! EDIT
   *
   * @param nickname
   * @return an instance of a specific plant from the database, null if no plant with the specific nickname exists
   */
  @Override
  public DBPlant getPlant(String nickname) {
    try {
      String query = "SELECT nickname, api_url, last_watered FROM [Plant] WHERE user_id =" + user.getUniqueId() + "AND nickname =" + nickname;
      ResultSet resultSet = statement.executeQuery(query);
      String APIUrl = resultSet.getString(4);
      Date lastWatered = resultSet.getDate(5);
      System.out.println("date format oflast watered = " + lastWatered);
      return new DBPlant(nickname, APIUrl, lastWatered);
    } catch (SQLException sqlException) {
      System.out.println(sqlException.fillInStackTrace());
      return null;
    }
  }
}