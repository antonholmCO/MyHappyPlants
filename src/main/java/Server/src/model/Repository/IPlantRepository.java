package Server.src.model.Repository;

import model.DatabasePlant;
import model.PlantLibrary;
import model.User;

import java.util.ArrayList;

public interface IPlantRepository {

  ArrayList<DatabasePlant> getAllPlants();
  DatabasePlant getPlant(String nickname);
  void savePlant(DatabasePlant plant);
}
