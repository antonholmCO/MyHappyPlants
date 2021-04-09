package se.myhappyplants.client.model;

/**
 * Class that could be used to communicate with server -> database
 * to register a new user
 * @author Christopher O'Driscoll
 */
public class RegisterRequest extends LoginRequest {

    private String userName;

    public RegisterRequest(String email, String userName, String password) {
        super(email, password);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
