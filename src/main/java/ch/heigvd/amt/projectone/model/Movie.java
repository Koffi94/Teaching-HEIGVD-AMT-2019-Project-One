package ch.heigvd.amt.projectone.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter

public class Movie {

    private int movieId;
    private String title;
    private String releaseYear;
    private String category;

}
