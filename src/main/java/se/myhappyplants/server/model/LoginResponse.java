package se.myhappyplants.server.model;

public class LoginResponse extends LibraryResponse {

    private User user;

    public LoginResponse(boolean success, User user) {
        super(success);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
