package se.myhappyplants.server.model.repository;

import org.mindrot.jbcrypt.BCrypt;
import se.myhappyplants.server.model.User;

import java.net.UnknownHostException;
import java.sql.*;

/**
 * Version 1. Frida Jacobsson april.
 * Class that call the database to store information about user.
 * Class that call the database to check if a user exists.
 * Uses BCrypt to create a safe password
 */

public class UserRepository implements IUserRepository {

  Statement statement;

  /**
   * Constructor that creates a connection to the database.
   * @throws SQLException
   */
  public UserRepository() throws SQLException, UnknownHostException {
    Connection conn = Driver.getConnection();
    statement = conn.createStatement();
  }

  /**
   * Method to save a new user using BCrypt.
   * @param user
   * @return
   */
  public boolean saveUser(User user) {
    String hashedPassword = BCrypt.hashpw(user.getPassword(),BCrypt.gensalt()); //hashing the password
    try {
      String query = "INSERT INTO [User] VALUES ('" + user.getUsername() + "', " + "'"+user.getEmail() +"', '"+hashedPassword + "',"+1+");";
      statement.executeUpdate(query);
      return true;
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
      return false;
    }
  }

  /**
   * Method to check if a user exists in database
   *  //1. hämta användare från databasen, om användare inte finns: returnera falskt
   *     //2. om användaren finns: hämta hämta det hashat lösenord från databasen
   *     //3. jämför hashat lösenord med inskrivet lösenord genom metoden checkpw
   * @return
   */
  @Override
  public boolean checkLogin(String email, String password) {
    boolean isVerified = false;
    try {
      String query = "SELECT password FROM [User] WHERE email = '" +email +"';";
      ResultSet resultSet = statement.executeQuery(query);
      if (resultSet.next()) {
        String hashedPassword = resultSet.getString(1);
        System.out.println(password);
        System.out.println(hashedPassword);
        isVerified = BCrypt.checkpw(password,hashedPassword); //här kollar vi om lösenordet som användaren skrivit in är samma som det som finns lagrat i databasen
      }
    }
    catch(SQLException sqlException) {
      sqlException.printStackTrace();
    }
    return isVerified;
  }
}
