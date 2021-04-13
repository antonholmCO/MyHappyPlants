package se.myhappyplants.client.model;

import se.myhappyplants.shared.User;

/**
 * Singleton class that keeps track of the current user
 * that is logged in to the application
 * Created by Christopher O'Driscoll
 * Updated 2021-04-13 by Christopher
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

