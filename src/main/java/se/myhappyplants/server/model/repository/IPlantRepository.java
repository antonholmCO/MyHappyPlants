package se.myhappyplants.server.model.repository;

import se.myhappyplants.shared.DBPlant;
import se.myhappyplants.shared.User;

import java.util.ArrayList;

/**
 * Interface for defining what a class calling the API for handling a plant has to contain.
 * Created by: Frida Jacobsson 2021-04-06
 * Updated by:
 */
public interface IPlantRepository {

  ArrayList<DBPlant> getUserLibrary(User user);

  DBPlant getPlant(User user, String nickname);

  boolean savePlant(User user, DBPlant plant);
}
