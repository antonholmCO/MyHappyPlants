package se.myhappyplants.server.model.repository;


import se.myhappyplants.server.model.User;

public interface IUserRepository {

  boolean saveUser(User user);
  boolean checkLogin(String email, String password);
}
