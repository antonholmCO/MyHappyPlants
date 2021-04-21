package se.myhappyplants.server.model.repository;

import org.mindrot.jbcrypt.BCrypt;
import se.myhappyplants.shared.User;

import java.net.UnknownHostException;
import java.sql.*;

/**
 * Class responsable for calling the database about users.
 * Created by: Frida Jacobsson
 * Updated by:
 */
public class UserRepository implements IUserRepository {

  Statement statement;

  /**
   * Constructor that creates a connection to the database.
   *
   * @throws SQLException
   * @throws UnknownHostException
   */
  public UserRepository() throws SQLException, UnknownHostException {
    Connection conn = Driver.getConnection();
    statement = conn.createStatement();
  }

  /**
   * Method to save a new user using BCrypt.
   *
   * @param user An instance of a newly created User that should be stored in the database.
   * @return A boolean value, true if the user was stored successfully
   */
  public boolean saveUser(User user) {
    String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()); //hashing the password
    try {
      String query = "INSERT INTO [User] VALUES ('" + user.getUsername() + "', " + "'" + user.getEmail() + "', '" + hashedPassword + "'," + 1 + ");";
      System.out.println("yas du savade en user");
      statement.executeUpdate(query);
      return true;
    } catch (SQLException sqlException) {
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
  @Override
  public boolean checkLogin(String email, String password) {
    boolean isVerified = false;
    try {
      String query = "SELECT password FROM [User] WHERE email = '" + email + "';";
      System.out.println("yas du logga in");
      ResultSet resultSet = statement.executeQuery(query);
      if (resultSet.next()) {
        String hashedPassword = resultSet.getString(1);
        isVerified = BCrypt.checkpw(password, hashedPassword); //här kollar vi om lösenordet som användaren skrivit in är samma som det som finns lagrat i databasen
      }
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
      System.out.println("nej du! du logga ej in!");
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
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }
    return user;
  }
}
