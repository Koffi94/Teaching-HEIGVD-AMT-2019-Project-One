package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Movie;

import javax.ejb.Local;
import java.sql.Date;

@Local
public interface MovieManagerLocal {

    // CRUD
    public void createMovie(int movieId, String title, Date releaseYear, String category);
    public Movie findMovieByTitle(String title);
    public Movie getMovie(int movieId);
    public void updateMovie(int movieId, String title, Date releaseYear, String category);
    public void deleteMovie(String title);
}
