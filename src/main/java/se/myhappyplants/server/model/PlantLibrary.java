package se.myhappyplants.server.model;

import java.util.ArrayList;

/**
 * version 1 Linn
 * version 2 Frida 7/4
 */

public class PlantLibrary {
    private ArrayList<DBPlant> userPlantLibrary;

    public void addPlantToLibrary(DBPlant plant){
        userPlantLibrary.add(plant);
    }

    public DBPlant getPlantFromLibrary(int index) {
       return userPlantLibrary.get(index);
    }
}
