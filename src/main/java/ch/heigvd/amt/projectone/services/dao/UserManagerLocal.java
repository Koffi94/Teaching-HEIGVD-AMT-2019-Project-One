package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.User;

import javax.ejb.Local;

@Local
public interface UserManagerLocal {

    // CRUD
    public void createUser(String username, String password, boolean active);
    public User findUserByName(String username);
    public User getUser(int userId);
    public void updateUser(String username, String password, boolean active);
    public void deleteUser(String username);
}
