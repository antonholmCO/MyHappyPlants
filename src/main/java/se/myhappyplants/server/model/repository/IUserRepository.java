package se.myhappyplants.server.model.repository;

import se.myhappyplants.shared.User;

/**
 * Interface for defining a repository calling database about users
 * Created by: Frida Jacobsson 2021-04-06
 * Updated by:
 */
public interface IUserRepository {

  boolean saveUser(User user);

  boolean checkLogin(String email, String password);

  User getUserDetails(String email);
}
