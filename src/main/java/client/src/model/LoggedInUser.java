//DENNA KLASS SKA LIGGA B{DE I SERVER OCH CLIENT SENARE
package client.src.model;

import server.src.model.User;

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

