package se.myhappyplants.server.model.repository;


import se.myhappyplants.server.model.DBPlant;

import java.util.ArrayList;

/**
 * Version 1. Author Frida Jacobsson 6/4
 */
public interface IPlantRepository {

  ArrayList<DBPlant> getAllPlants();
  DBPlant getPlant(String nickname);
  void savePlant(DBPlant plant);
}
