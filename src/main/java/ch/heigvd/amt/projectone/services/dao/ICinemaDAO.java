package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Cinema;

import javax.ejb.Local;

/**
 * This interface is used to manage Cinema entities in the DB
 */
@Local
public interface ICinemaDAO {

    /**
     * This method is used to create a Cinema
     * @param name
     * @param city
     * @param price
     * @return a cinema object
     */
    Cinema createCinema(String name, String city, String price);

    /**
     * This method is used to find a cinema by its name
     * @param name
     * @return a cinema object
     */
    Cinema findCinemaByName(String name);

    /**
     * This method is used to get a cinema by its id
     * @param cinemaId
     * @return a cinema object
     */
    Cinema getCinema(int cinemaId);

    /**
     * This method is used to update a cinema by its properties
     * @param cinemaId
     * @param name
     * @param city
     * @param price
     */
    void updateCinema(int cinemaId, String name, String city, String price);

    /**
     * This method is used to delete a cinema by its id
     * @param cinemaId
     */
    void deleteCinema(int cinemaId);
}
