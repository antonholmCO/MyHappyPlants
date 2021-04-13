package se.myhappyplants.server.model.plant;


import se.myhappyplants.server.model.APIPlant;

import java.io.Serializable;
import java.util.ArrayList;

public class PlantCollection implements Serializable {

  public ArrayList<APIPlant> data;

  public ArrayList<APIPlant> getData() {
    return data;
  }
}
