package se.myhappyplants.server.model.repository;

import se.myhappyplants.shared.DBPlant;

import java.util.ArrayList;

/**
 * Interface for defining what a class calling the API for handling a plant has to contain.
 * Created by: Frida Jacobsson 2021-04-06
 * Updated by:
 */
public interface IPlantRepository {

  ArrayList<DBPlant> getAllPlants();

  DBPlant getPlant(String nickname);

  boolean savePlant(DBPlant plant);
}
