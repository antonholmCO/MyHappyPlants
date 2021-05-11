package se.myhappyplants.server.repository;

import org.mindrot.jbcrypt.BCrypt;
import se.myhappyplants.shared.User;

import java.net.UnknownHostException;
import java.sql.*;

/**
 * Class responsible for calling the database about users.
 * Created by: Frida Jacobsson
 * Updated by:
 */
public class UserRepository {

    private Statement statement;
    private Connection conn;

    /**
     * Constructor that creates a connection to the database.
     *
     * @throws SQLException
     * @throws UnknownHostException
     */
    public UserRepository() throws SQLException, UnknownHostException {
        conn = Driver.getConnection("MyHappyPlants");
        statement = conn.createStatement();
    }

    /**
     * Method to save a new user using BCrypt.
     *
     * @param user An instance of a newly created User that should be stored in the database.
     * @return A boolean value, true if the user was stored successfully
     */
    public boolean saveUser(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        String sqlSafeUsername = user.getUsername().replace("'", "''");
        try {
            String query = "INSERT INTO [User] VALUES ('" + sqlSafeUsername + "', " + "'" + user.getEmail() + "', '" + hashedPassword + "'," + 1 + ");";
            statement.executeUpdate(query);
            return true;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    /**
     * Method to check if a user exists in database.
     * Purpose of method is to make it possible for user to log in
     *
     * @param email    typed email from client and the application
     * @param password typed password from client and the application
     * @return A boolean value, true if the user exist in database and the password is correct
     */
    public boolean checkLogin(String email, String password) {
        boolean isVerified = false;
        try {
            String query = "SELECT password FROM [User] WHERE email = '" + email + "';";

            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString(1);
                isVerified = BCrypt.checkpw(password, hashedPassword);
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return isVerified;
    }

    /**
     * Method to get information (id, username and notification status) about a specific user
     *
     * @param email ??
     * @return a new instance of USer
     */
    public User getUserDetails(String email) {
        User user = null;
        String username = null;
        boolean notificationActivated = false;
        int uniqueID = 0;
        try {
            String query = "SELECT id, username, notification_activated FROM [User] WHERE email = '" + email + "';";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                uniqueID = resultSet.getInt(1);
                username = resultSet.getString(2);
                notificationActivated = resultSet.getBoolean(3);
            }
            user = new User(uniqueID, email, username, notificationActivated);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return user;
    }

    /**
     * Method to delete a user and all plants in user library at once
     * author: Frida Jacobsson
     *
     * @param email
     * @param password
     * @return boolean value, false if transaction is rolled back
     * @throws SQLException
     */
    public boolean deleteAccount(String email, String password) {
        if (!checkLogin(email, password)) {
            return false;
        }
        try {
            conn.setAutoCommit(false);
            String querySelect = "SELECT [User].id from [User] WHERE [User].email = '" + email + "';";
            ResultSet resultSet = statement.executeQuery(querySelect);
            if (!resultSet.next()) {
                throw new SQLException();
            }
            int id = resultSet.getInt(1);
            String queryDeletePlants = "DELETE FROM [Plant] WHERE user_id = " + id + ";";
            statement.executeUpdate(queryDeletePlants);
            String queryDeleteUser = "DELETE FROM [User] WHERE id = " + id + ";";
            statement.executeUpdate(queryDeleteUser);
            conn.commit();
            conn.setAutoCommit(true);
        }
        catch (SQLException sqlException) {
            try {
                conn.rollback();
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return false;
        }
        return true;
    }

    public boolean changeNotifications(User user, boolean notifications) {
        int notificationsActivated;
        if(notifications) {
            notificationsActivated = 1;
        }
        else {
            notificationsActivated = 0;
        }
        try {
            String query = "UPDATE [User] SET notification_activated = " + notificationsActivated + " WHERE email = '"+ user.getEmail() + "';";
            statement.executeUpdate(query);
            return true;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }
}
