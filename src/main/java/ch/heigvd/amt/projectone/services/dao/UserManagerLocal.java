package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.User;

import javax.ejb.Local;

@Local
public interface UserManagerLocal {

    // CRUD
    public void createUser(String username, String password);
    public User findUserByName(String username);
    public User getUser(int userId);
    public void updateUser(int userId, String username, String password);
    public void deleteUser(int userId);
    public boolean checkUser(String username, String password);
}
