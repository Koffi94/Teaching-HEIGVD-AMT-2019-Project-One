package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Movie;
import javax.ejb.Local;

/**
 * This interface is used to manage Movie entities in the DB
 */
@Local
public interface IMovieDAO {

    /**
     * This method is used to create a Movie
     * @param title
     * @param releaseYear
     * @param category
     * @return a movie object
     */
    Movie createMovie(String title, String releaseYear, String category);

    /**
     * This method is used to find a Movie by its title
     * @param title
     * @return a movie object
     */
    Movie findMovieByTitle(String title);

    /**
     * This method is used to get a Movie by its id
     * @param movieId
     * @return a movie object
     */
    Movie getMovie(int movieId);

    /**
     * This method is used to update a Movie by its properties
     * @param movieId
     * @param title
     * @param releaseYear
     * @param category
     */
    void updateMovie(int movieId, String title, String releaseYear, String category);

    /**
     * This method is used to delete a Movie by its id
     * @param movieId
     */
    void deleteMovie(int movieId);
}
