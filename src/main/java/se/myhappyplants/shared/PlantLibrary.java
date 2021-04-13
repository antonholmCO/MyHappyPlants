package se.myhappyplants.shared;

import java.util.ArrayList;

/**
 * Created by: Linn Borgström
 * Updated by: Frida Jacobsson 2021-04-07
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
