package Server.src.model;

import java.util.ArrayList;

public class PlantLibrary {
    private ArrayList<DatabasePlant> userPlantLibrary;

    public void addPlantsToLibrary(DatabasePlant plant){
        userPlantLibrary.add(plant);
    }

    public DatabasePlant getPlantFromLibrary(int index) {
       return userPlantLibrary.get(index);
    }
}
