package ch.heigvd.amt.projectone.model;

import java.sql.Date;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

//@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter

public class Movie {

    private int id;
    private String name;
    private Date releaseDate;
    private String category;

    public Movie(int id, String name, Date releaseDate, String category) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.category = category;
    }
}
