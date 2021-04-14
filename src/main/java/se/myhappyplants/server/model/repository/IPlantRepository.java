package se.myhappyplants.server.model.repository;


import se.myhappyplants.shared.DBPlant;

import java.util.ArrayList;

/**
 * Created by: Frida Jacobsson 2021-04-06
 * Updated by:
 */
public interface IPlantRepository {

  ArrayList<DBPlant> getAllPlants();
  DBPlant getPlant(String nickname);
  void savePlant(DBPlant plant);
}
