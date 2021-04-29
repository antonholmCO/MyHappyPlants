package se.myhappyplants.shared;

import javafx.scene.image.Image;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by: Linn Borgström
 * Updated by: Christopher, 2021-04-13
 * Updated by: Anton, 2021-04-29
 */
public class User implements Serializable {

  private int uniqueId;
  private String email;
  private String username;
  private String password;
  private String avatarURL = new File("resources/images/user_default_img.png").toURI().toString();
  private boolean isNotificationsActivated = true;

  /**
   * Constructor used when registering a new user account
   *
   * @param email
   * @param username
   * @param password
   * @param isNotificationsActivated
   */
  public User(String email, String username, String password, boolean isNotificationsActivated) {
    this.email = email;
    this.username = username;
    this.password = password;
    this.isNotificationsActivated = isNotificationsActivated;
  }

  /**
   * Constructor for login responses from database
   *
   * @param email
   * @param username
   * @param isNotificationsActivated
   */
  public User(String email, String username, boolean isNotificationsActivated) {
    this.email = email;
    this.username = username;
    this.isNotificationsActivated = isNotificationsActivated;
  }

  /**
   * Simple constructor for when login/registration requests fail
   *
   * @param username
   */
  public User(String username) {
    this.username = username;

  }

  /**
   * Simple constructor for login requests
   *
   * @param email
   * @param password
   */
  public User(String email, String password) {

    this.email = email;
    this.password = password;
  }

  public User(int uniqueID, String email, String username, boolean notificationsActivated) {

    this.uniqueId = uniqueID;
    this.email = email;
    this.username = username;
    this.isNotificationsActivated = notificationsActivated;
  }

  public int getUniqueId() {
    return uniqueId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getEmail() {
    return email;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean getIsNotificationsActivated() {
    return isNotificationsActivated;
  }

  public void setIsNotificationsActivated(boolean notificationsActivated) {
    this.isNotificationsActivated = notificationsActivated;
  }

  public String getAvatarURL() {
    return avatarURL;
  }

  public void setAvatar(String pathToImg) {
    this.avatarURL = new File(pathToImg).toURI().toString();
  }
}
