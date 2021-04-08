package se.myhappyplants.server.model.repository;


import se.myhappyplants.server.model.DatabasePlant;

import java.util.ArrayList;

public interface IPlantRepository {

  ArrayList<DatabasePlant> getAllPlants();
  DatabasePlant getPlant(String nickname);
  void savePlant(DatabasePlant plant);
}
