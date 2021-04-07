package Server.src.model.Repository;

import model.User;

public interface IUserRepository {

  boolean saveUser(User user);
  boolean checkLogin(String email, String password);
}
