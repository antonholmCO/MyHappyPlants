package se.myhappyplants.shared;

import java.io.*;

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
    /*public User(String email, String username, boolean isNotificationsActivated) {
        this.email = email;
        this.username = username;
        this.isNotificationsActivated = isNotificationsActivated;
        SetAvatar.setAvatarOnLogin(email, avatarURL);
    }*/

    /**
     * Simple constructor for when login/registration requests fail
     *
     * @param username
     */
    /*public User(String username) {
        this.username = username;
    }*/

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

    //TODO: Fråga gruppen om hur de vill göra med logiken med denna.
    // Nu anropas den från SettingsTabController rad 42, MyPlantTabController rad 52, SearchTabController rad 53

    private void setAvatarOnLogin(String email) {
        try (BufferedReader br = new BufferedReader(new FileReader("resources/images/user_avatars/" + email + "_avatar.txt"))) {
            String readtxt = br.readLine();
            this.avatarURL = new File(readtxt).toURI().toString();
        }
        catch (IOException e) {
            this.avatarURL = new File("resources/images/user_default_img.png").toURI().toString();
        }
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

    public boolean areNotificationsActivated() {
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
