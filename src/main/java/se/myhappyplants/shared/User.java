package se.myhappyplants.shared;

import java.io.File;
import java.io.Serializable;

/**
 * Created by: Linn Borgström
 * Updated by: Anton, 2021-04-29
 */
public class User implements Serializable {

    private int uniqueId;
    private String email;
    private String username;
    private String password;
    private String avatarURL;
    private boolean notificationsActivated = true;
    private boolean funFactsActivated = true;

    /**
     * Simple constructor for login requests
     *
     * @param email    Email address
     * @param password Password
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor used when registering a new user account
     *
     * @param email    Email address
     * @param username Username
     * @param password Password
     */
    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /**
     * Constructor used to return a users details from the database
     *
     * @param uniqueId               A user's unique id in the database
     * @param email                  Email address
     * @param username               Username
     * @param notificationsActivated True if notifications wanted
     * @param funFactsActivated      True if fun facts wanted
     */
    public User(int uniqueId, String email, String username, boolean notificationsActivated, boolean funFactsActivated) {
        this.uniqueId = uniqueId;
        this.email = email;
        this.username = username;
        this.notificationsActivated = notificationsActivated;
        this.funFactsActivated = funFactsActivated;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatar(String pathToImg) {
        this.avatarURL = new File(pathToImg).toURI().toString();
    }

    public boolean areNotificationsActivated() {
        return notificationsActivated;
    }

    public void setNotificationsActivated(boolean notificationsActivated) {
        this.notificationsActivated = notificationsActivated;
    }

    public boolean areFunFactsActivated() {
        return funFactsActivated;
    }

    public void setFunFactsActivated(boolean funFactsActivated) {
        this.funFactsActivated = funFactsActivated;
    }
}
