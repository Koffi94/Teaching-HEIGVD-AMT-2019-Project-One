package ch.heigvd.amt.projectone.model;

import java.sql.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;

//@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter

public class Movie {

    private int movieId;
    private String title;
    private String releaseYear;
    private String category;

    public Movie(int movieId, String title, String releaseYear, String category) {
        this.movieId = movieId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.category = category;
    }
}
