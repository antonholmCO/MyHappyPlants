package server.src.model.repository;

import server.src.model.User;

public interface IUserRepository {

  boolean saveUser(User user);
  boolean checkLogin(String email, String password);
}
