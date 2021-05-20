package se.myhappyplants.server.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Interface for defining query executor methods
 * Created by: Frida Jacobsson 2021-05-19
 */
public interface IQueryExecutor {

    void executeUpdate(String query) throws SQLException;

    ResultSet executeQuery(String query) throws SQLException;

    Statement beginTransaction() throws SQLException;

    void endTransaction() throws SQLException;

    void rollbackTransaction() throws SQLException;
}
