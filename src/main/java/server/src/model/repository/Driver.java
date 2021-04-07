package server.src.model.repository;
import server.PasswordsAndKeys;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Version 2 - 6/4 - Anton
 */

public class Driver {
  public static Connection getConnection() throws SQLException, UnknownHostException {
    String dbServerIp = PasswordsAndKeys.dbServerIp;
    String dbServerPort = PasswordsAndKeys.dbServerPort;
    String dbUser = PasswordsAndKeys.dbUsername;
    String dbPassword = PasswordsAndKeys.dbPassword;

    //If the DB host connects from their own IP then change connection to localhost
    if (InetAddress.getLocalHost().getHostName().equals(PasswordsAndKeys.dbHostName)) {
      dbServerIp = "localhost";
    }
    String dbURL = String.format("jdbc:sqlserver://%s:%s;databaseName=MyHappyPlants;user=%s;password=%s", dbServerIp, dbServerPort, dbUser, dbPassword);

    Connection conn = DriverManager.getConnection(dbURL);

    if (conn != null) {
      System.out.println("Connected");
    }
    return conn;
  }
}