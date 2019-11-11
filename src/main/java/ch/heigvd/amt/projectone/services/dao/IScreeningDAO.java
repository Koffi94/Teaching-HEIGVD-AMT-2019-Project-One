package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Cinema;
import ch.heigvd.amt.projectone.model.Movie;
import ch.heigvd.amt.projectone.model.Screening;
import ch.heigvd.amt.projectone.model.User;
import javax.ejb.Local;
import java.util.List;

/**
 * This interface is used to manage Screening entities in the DB
 */
@Local
public interface IScreeningDAO {

    /**
     * This method is used to create a Screening
     * @param time
     * @param room
     * @param property
     * @param user
     * @param movie
     * @param cinema
     * @return a screening object
     */
    Screening createScreening(String time, String room, String property, User user, Movie movie, Cinema cinema);

    /**
     * This method is used to get a Screening by his id
     * @param screeningId
     * @return a screening object
     */
    Screening getScreening(int screeningId);

    /**
     * This method is used to find screenings by a user (owner)
     * @param user
     * @return a list of screening object
     */
    List<Screening> findScreeningsByOwner(User user);

    /**
     * This method is used to find a page of screenings by a user (owner) and it's size
     * @param user
     * @param pageSize
     * @param offset
     * @return a list of screening object
     */
    List<Screening> getScreeningsPage(User user, int pageSize, int offset);

    /**
     * This method is used to find the number of screenings attached to a user
     * @param user
     * @return an integer as the quantity of screenings for a specific user
     */
    Integer getScreeningsQuantity(User user);

    /**
     * This method is used to update a Screening by its properties
     * @param screeningId
     * @param time
     * @param room
     * @param property
     * @param user
     * @param movie
     * @param cinema
     */
    void updateScreening(int screeningId, String time, String room, String property, User user, Movie movie, Cinema cinema);

    /**
     * This method is used to delete a Screening by its id
     * @param screeningId
     */
    void deleteScreening(int screeningId);

}
