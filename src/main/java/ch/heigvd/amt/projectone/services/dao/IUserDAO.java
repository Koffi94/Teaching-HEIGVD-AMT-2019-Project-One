package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.User;
import javax.ejb.Local;

/**
 * This interface is used to manage User entities in the DB
 */
@Local
public interface IUserDAO {

    /**
     * This method is used to create a user
     * @param username
     * @param password
     * @return a user object
     */
    User createUser(String username, String password);

    /**
     * This method is used to find a user by its name
     * @param username
     * @return a user object
     */
    User findUserByName(String username);

    /**
     * This method is used to get a user by its id
     * @param userId
     * @return a user object
     */
    User getUser(int userId);

    /**
     * This method is used to update a user by its properties
     * @param userId
     * @param username
     * @param password
     */
    void updateUser(int userId, String username, String password);

    /**
     * This method is used to delete a user by its id
     * @param userId
     */
    void deleteUser(int userId);

    /**
     * This method is used to check if a account exists in the DB
     * @param username
     * @param password
     * @return true if it exists and correct password else false
     */
    boolean checkUser(String username, String password);
}
