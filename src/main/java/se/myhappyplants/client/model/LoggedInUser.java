package se.myhappyplants.client.model;

import se.myhappyplants.server.model.User;

/**
 * Singleton class that keeps track of the current user
 * that is logged in to the application
 * @author Christopher O'Driscoll
 */
public final class LoggedInUser {

    private User user;
    private final static LoggedInUser INSTANCE = new LoggedInUser();

    private LoggedInUser() {}

    public static LoggedInUser getInstance() {
        return INSTANCE;
    }

    public void setUser(User u) {
        this.user = u;
    }

    public User getUser() {
        return this.user;
    }
}

