package se.myhappyplants.server.repository;

import se.myhappyplants.server.PasswordsAndKeys;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by: Frida Jacobsson 2021-04-02
 * Updated by: Anton, 2021-04-06
 */

public class Driver {
    public static Connection getConnection(String database) throws SQLException, UnknownHostException {
        String dbServerIp = PasswordsAndKeys.dbServerIp;
        String dbServerPort = PasswordsAndKeys.dbServerPort;
        String dbUser = PasswordsAndKeys.dbUsername;
        String dbPassword = PasswordsAndKeys.dbPassword;
        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());


        if (InetAddress.getLocalHost().getHostName().equals(PasswordsAndKeys.dbHostName)) {
            dbServerIp = "localhost";
        }
        String dbURL = String.format("jdbc:sqlserver://%s:%s;databaseName=" + database + ";user=%s;password=%s", dbServerIp, dbServerPort, dbUser, dbPassword);
        Connection conn = DriverManager.getConnection(dbURL);
        if (conn != null) {
            System.out.println("Connected to database " +database);
        }
        return conn;
    }
}

