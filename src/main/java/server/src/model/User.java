package server.src.model;
/**
 * Version 1. Author: Frida Jacobsson.
 */
public class User {

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

    public User(String userName) {

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

    public String getUserName() {
        return null;
    }

    public String getAvatarURL() {
        return null;
    }
}
