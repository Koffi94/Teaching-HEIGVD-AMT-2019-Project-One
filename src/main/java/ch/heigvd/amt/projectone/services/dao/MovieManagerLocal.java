package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Movie;

import javax.ejb.Local;
import java.sql.Date;

@Local
public interface MovieManagerLocal {

    // CRUD
    public void createMovie(String title, String releaseYear, String category);
    public Movie findMovieByTitle(String title);
    public Movie getMovie(int movieId);
    public void updateMovie(int movieId, String title, String releaseYear, String category);
    public void deleteMovie(int movieId);
}
