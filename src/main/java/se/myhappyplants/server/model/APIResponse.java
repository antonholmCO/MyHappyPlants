package se.myhappyplants.server.model;

import java.util.ArrayList;

/**
 * ToDo
 * Could contain things like an array list of Plants, text, image locations etc
 * @author Christopher O'Driscoll
 */
public class APIResponse extends Response {
    private ArrayList<APIPlant> plantList;

    public APIResponse(boolean success,ArrayList<APIPlant> plantList) {
        super(success);
        this.plantList=plantList;
    }

    public ArrayList<APIPlant> getPlantList() {
        return plantList;
    }

    @Override
    public String toString() {
        return "APIResponse{" +
                "plantList=" + plantList +
                '}';
    }
}
