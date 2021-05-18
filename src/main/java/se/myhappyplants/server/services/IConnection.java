package se.myhappyplants.server.services;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnection {

    Connection getConnection();

    void closeConnection();
}
