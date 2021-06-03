package se.myhappyplants.server.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Interface to the database class
 */
public interface IDatabase {

    void executeUpdate(String query) throws SQLException;

    ResultSet executeQuery(String query) throws SQLException;

    Statement beginTransaction() throws SQLException;

    void endTransaction() throws SQLException;

    void rollbackTransaction() throws SQLException;
}
