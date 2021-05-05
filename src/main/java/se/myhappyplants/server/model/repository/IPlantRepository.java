package se.myhappyplants.server.model.repository;

import se.myhappyplants.shared.UserPlant;
import se.myhappyplants.shared.User;

import java.util.ArrayList;

/**
 * Interface for defining a repository calling Trefle API about plants
 * Created by: Frida Jacobsson 2021-04-06
 * Updated by:
 */
public interface IPlantRepository {

  ArrayList<UserPlant> getUserLibrary(User user);

  UserPlant getPlant(User user, String nickname);

  boolean savePlant(User user, UserPlant plant);
}
