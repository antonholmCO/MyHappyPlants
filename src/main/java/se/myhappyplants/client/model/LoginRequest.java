package se.myhappyplants.client.model;

/**
 * Class that could be used to communicate with server -> database
 * to verify login details
 * @author Christopher O'Driscoll
 */
public class LoginRequest extends DBRequest {

    private String email;
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
