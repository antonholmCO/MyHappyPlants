package se.myhappyplants.server.services;

import se.myhappyplants.server.PasswordsAndKeys;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for handling connection with a specific database
 * Created by: Frida Jacobsson 2021-05-21
 */
public class DatabaseConnection implements IDatabaseConnection {

    private java.sql.Connection conn;
    private String databaseName;

    public DatabaseConnection(String databaseName) {
        this.databaseName = databaseName;
    }

    private java.sql.Connection createConnection() throws SQLException, UnknownHostException {
        String dbServerIp = PasswordsAndKeys.dbServerIp;
        String dbServerPort = PasswordsAndKeys.dbServerPort;
        String dbUser = PasswordsAndKeys.dbUsername;
        String dbPassword = PasswordsAndKeys.dbPassword;
        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());

        if (InetAddress.getLocalHost().getHostName().equals(PasswordsAndKeys.dbHostName)) {
            dbServerIp = "localhost";
        }
        String dbURL = String.format("jdbc:sqlserver://%s:%s;databaseName=" + databaseName + ";user=%s;password=%s", dbServerIp, dbServerPort, dbUser, dbPassword);
        this.conn = DriverManager.getConnection(dbURL);
        return conn;
    }

    @Override
    public java.sql.Connection getConnection() {
        if(conn==null) {
            try {
                conn = createConnection();
            }
            catch (UnknownHostException e) {
                e.printStackTrace();
            }
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return conn;
    }

    @Override
    public void closeConnection() {
        try {
            conn.close();
        }
        catch (SQLException sqlException) {
           //do nothing when this occurs, we don't care about this exception
        }
        conn = null;
    }
}
