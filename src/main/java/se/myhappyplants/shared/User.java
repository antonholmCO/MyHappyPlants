package se.myhappyplants.shared;

import java.io.Serializable;

/**
 * Created by: Author: Frida Jacobsson Linn Borgström
 * Updated by: Christopher, 2021-04-13
 */
public class User implements Serializable {

    private int uniqueId;
    private String email;
    private String username;
    private String password;
    private PlantLibrary plantLibrary;
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
     * @param email
     * @param password
     */
    public User(String email, String password) {

        this.email = email;
        this.password = password;
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

    public PlantLibrary getPlantLibrary() {
        return plantLibrary;
    }

    public void setPlantLibrary(PlantLibrary plantLibrary) {
        this.plantLibrary = plantLibrary;
    }
}
