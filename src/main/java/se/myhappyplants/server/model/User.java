package se.myhappyplants.server.model;

import java.io.Serializable;

/**
 * Version 1. Author: Frida Jacobsson.
 * Version 2. Author: Linn Borgström
 */
public class User implements Serializable {

    private int uniqueId;
    private String email;
    private String username;
    private String password;
    private boolean isNotificationsActivated = true;

    public User(String email, String firstName, String password, boolean isNotificationsActivated) {
        this.email = email;
        this.username = firstName;
        this.password = password;
        this.isNotificationsActivated = isNotificationsActivated;
    }

    public User(String email, String username, boolean isNotificationsActivated) {
        this.email = email;
        this.username = username;
        this.isNotificationsActivated = isNotificationsActivated;
    }

    public User(String username) {
        this.username = username;

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

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
