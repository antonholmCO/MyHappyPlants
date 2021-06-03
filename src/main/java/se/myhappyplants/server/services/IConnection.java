package se.myhappyplants.server.services;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface to the connection classes
 */
public interface IConnection {

    Connection getConnection();

    void closeConnection();
}
