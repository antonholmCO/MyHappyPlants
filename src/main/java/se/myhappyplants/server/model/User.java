package se.myhappyplants.server.model;

import java.io.Serializable;

/**
 * Version 1. Author: Frida Jacobsson.
 * Version 2. Author: Linn Borgström
 */
public class User implements Serializable {

    private int uniqueId;
    private String email;
    private String firstName;
    private String password;
    private boolean isNotificationsActivated = true;

    public User(String email, String firstName, String password, boolean isNotificationsActivated) {
        this.email = email;
        this.firstName = firstName;
        this.password = password;
        this.isNotificationsActivated = isNotificationsActivated;
    }

    /**
     * temporary constructor to test client-server communication
     * todo remove when redundant
     * @param firstName
     */
    public User(String email, String firstName, boolean isNotificationsActivated) {
        this.email = email;
        this.firstName = firstName;
        this.isNotificationsActivated = isNotificationsActivated;
    }

    public User(String firstName) {
        this.firstName = firstName;

    }

    public int getUniqueId() {
        return uniqueId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
