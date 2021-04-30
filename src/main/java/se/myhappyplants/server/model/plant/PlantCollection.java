package se.myhappyplants.server.model.plant;
/**
 * Created by: Frida Jacobsson
 * Updated by:
 */


import se.myhappyplants.shared.APIPlant;

import java.io.Serializable;
import java.util.ArrayList;

public class PlantCollection implements Serializable {

    public ArrayList<APIPlant> data;

    public ArrayList<APIPlant> getData() {
        return data;
    }
}
