package se.myhappyplants.client.model;

import se.myhappyplants.server.model.DBPlant;
import se.myhappyplants.server.model.User;

/**
 * Class that could be used to communicate with server -> database
 * to update or fetch a users library
 * @author Christopher O'Driscoll
 */
public class LibraryRequest extends DBRequest {

    private User user;                      //The user that's logged in to the client application
    private LibraryRequestType requestType; //So that server knows what to do with request
    private DBPlant[] plants;         //Optional array of plants to be updated/added to DB

    public LibraryRequest(LibraryRequestType requestType) {
        this.user = LoggedInUser.getInstance().getUser();
        this.requestType = requestType;
    }

    public User getUser() {
        return user;
    }

    public LibraryRequestType getRequestType() {
        return requestType;
    }

    public DBPlant[] getPlants() {
        return plants;
    }

    public void setPlants(DBPlant[] plants) {
        this.plants = plants;
    }
}
