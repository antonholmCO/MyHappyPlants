package se.myhappyplants.server.services;

import java.sql.Connection;

/**
 * Interface for all classes handling Connection
 * Created by: Frida Jacobsson 2021-05-21
 */
public interface IDatabaseConnection {

    Connection getConnection();

    void closeConnection();
}
