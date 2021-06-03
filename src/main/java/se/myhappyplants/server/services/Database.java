package se.myhappyplants.server.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class that handles the connection to the database and executes a query and translation
 */
public class Database implements IDatabase {

    private IConnection connection;

    public Database(IConnection connection) {
        this.connection = connection;
    }

    @Override
    public void executeUpdate(String query) throws SQLException {
        boolean isSuccess = false;
        int retries = 0;
        do {
            try {
                this.connection.getConnection().createStatement().executeUpdate(query);
                isSuccess = true;
                return;
            }
            catch (SQLException sqlException) {
                connection.closeConnection();
                retries++;
            }
        } while (!isSuccess && retries < 3);
        throw new SQLException("No connection to database");
    }

    @Override
    public ResultSet executeQuery(String query) throws SQLException {
        int retries = 0;
        do {
            try {
                ResultSet resultSet = this.connection.getConnection().createStatement().executeQuery(query);
                return resultSet;
            }
            catch (SQLException sqlException) {
                connection.closeConnection();
                retries++;
            }
        } while (retries < 3);
        throw new SQLException("No connection to database");
    }

    @Override
    public Statement beginTransaction() throws SQLException {
        int retries = 0;
        do {
            try {
                connection.getConnection().setAutoCommit(false);
                return connection.getConnection().createStatement();
            }
            catch (SQLException sqlException) {
                connection.closeConnection();
                retries++;
            }
        }
        while (retries < 3);
        throw new SQLException("No connection to database");
    }

    @Override
    public void endTransaction() throws SQLException {
        connection.getConnection().commit();
        connection.getConnection().setAutoCommit(true);
    }

    @Override
    public void rollbackTransaction() throws SQLException {
        connection.getConnection().rollback();
    }
}
