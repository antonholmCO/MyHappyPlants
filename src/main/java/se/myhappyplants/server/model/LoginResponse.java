package se.myhappyplants.server.model;

/**
 * A class that can be sent via tcp with a user
 * object when a successful login/register request is made
 * @author Christopher O'Driscoll
 */
public class LoginResponse extends LibraryResponse {

    private User user;

    public LoginResponse (boolean success) {
        super(success);

    }
    public LoginResponse(boolean success, User user) {
        this(success);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
