package se.myhappyplants.server.services;

import org.mindrot.jbcrypt.BCrypt;
import se.myhappyplants.shared.User;

import java.net.UnknownHostException;
import java.sql.*;

/**
 * Class responsible for calling the database about users.
 * Created by: Frida Jacobsson 2021-03-30
 * Updated by: Frida Jacobsson 2021-05-21
 */
public class UserRepository {

    private IQueryExecutor database;

    public UserRepository(IQueryExecutor database){
       this.database = database;
    }

    /**
     * Method to save a new user using BCrypt.
     *
     * @param user An instance of a newly created User that should be stored in the database.
     * @return A boolean value, true if the user was stored successfully
     */
    public boolean saveUser(User user) {
        boolean success = false;
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        String sqlSafeUsername = user.getUsername().replace("'", "''");
        String query = "INSERT INTO [User] VALUES ('" + sqlSafeUsername + "', '" + user.getEmail() + "', '" + hashedPassword + "'," + 1 + "," + 1 + ");";
        try {
            database.executeUpdate(query);
            success = true;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return success;
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
        String query = "SELECT password FROM [User] WHERE email = '" + email + "';";
        try {
            ResultSet resultSet = database.executeQuery(query);
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
        int uniqueID = 0;
        String username = null;
        boolean notificationActivated = false;
        boolean funFactsActivated = false;
        String query = "SELECT id, username, notification_activated, fun_facts_activated FROM [User] WHERE email = '" + email + "';";
        try {
            ResultSet resultSet = database.executeQuery(query);
            while (resultSet.next()) {
                uniqueID = resultSet.getInt(1);
                username = resultSet.getString(2);
                notificationActivated = resultSet.getBoolean(3);
                funFactsActivated = resultSet.getBoolean(4);
            }
            user = new User(uniqueID, email, username, notificationActivated, funFactsActivated);
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
        boolean accountDeleted = false;
        if (checkLogin(email, password)) {
            String querySelect = "SELECT [User].id from [User] WHERE [User].email = '" + email + "';";
            try {
                Statement statement = database.beginTransaction();
                ResultSet resultSet = statement.executeQuery(querySelect);
                if (!resultSet.next()) {
                    throw new SQLException();
                }
                int id = resultSet.getInt(1);
                String queryDeletePlants = "DELETE FROM [Plant] WHERE user_id = " + id + ";";
                statement.executeUpdate(queryDeletePlants);
                String queryDeleteUser = "DELETE FROM [User] WHERE id = " + id + ";";
                statement.executeUpdate(queryDeleteUser);
                database.endTransaction();
                accountDeleted = true;
            }
            catch (SQLException sqlException) {
                try {
                   database.rollbackTransaction();
                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return accountDeleted;
    }

    public boolean changeNotifications(User user, boolean notifications) {
        boolean notificationsChanged = false;
        int notificationsActivated = 0;
        if (notifications) {
            notificationsActivated = 1;
        }
        String query = "UPDATE [User] SET notification_activated = " + notificationsActivated + " WHERE email = '" + user.getEmail() + "';";
        try {
            database.executeUpdate(query);
            notificationsChanged = true;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return notificationsChanged;
    }

    public boolean changeFunFacts(User user, Boolean funFactsActivated) {
        boolean funFactsChanged = false;
        int funFactsBitValue = 0;
        if (funFactsActivated) {
            funFactsBitValue = 1;
        }
        String query = "UPDATE [User] SET fun_facts_activated = " + funFactsBitValue + " WHERE email = '" + user.getEmail() + "';";
        try {
            database.executeUpdate(query);
            funFactsChanged = true;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return funFactsChanged;
    }
}

