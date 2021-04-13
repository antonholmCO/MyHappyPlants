package se.myhappyplants.server.model.repository;


import se.myhappyplants.shared.User;

/**
 * Version 1. Author Frida Jacobsson 6/4
 */
public interface IUserRepository {

  boolean saveUser(User user);
  boolean checkLogin(String email, String password);
}
