package server.src.model.repository;


import server.src.model.DatabasePlant;

import java.util.ArrayList;

public interface IPlantRepository {

  ArrayList<DatabasePlant> getAllPlants();
  DatabasePlant getPlant(String nickname);
  void savePlant(DatabasePlant plant);
}
