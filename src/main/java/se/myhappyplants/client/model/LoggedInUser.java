package se.myhappyplants.client.model;

import se.myhappyplants.server.model.User;

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

