package se.myhappyplants.client.model;

/**
 * Class that could be used to communicate with server -> database
 * to register a new user
 * @author Christopher O'Driscoll
 */
public class RegisterRequest extends LoginRequest {

    private String username;

    public RegisterRequest(String email, String userName, String password) {
        super(email, password);
        this.username = userName;
    }

    public String getUsername() {
        return username;
    }
}
