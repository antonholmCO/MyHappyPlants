package se.myhappyplants.client.model;

import se.myhappyplants.shared.DBPlant;

import java.util.ArrayList;

public class UserLibrary {

    private ArrayList<DBPlant> currentUserLibrary;
    private final static UserLibrary INSTANCE = new UserLibrary();

    private UserLibrary() {}

    public static UserLibrary getInstance() {
        return INSTANCE;
    }

    public void setCurrentUserLibrary(ArrayList<DBPlant> currentUserLibrary) {
        this.currentUserLibrary = currentUserLibrary;
    }

    public ArrayList<DBPlant> getCurrentUserLibrary() {
        return this.currentUserLibrary;
    }
}
