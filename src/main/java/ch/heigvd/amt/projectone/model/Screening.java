package ch.heigvd.amt.projectone.model;

import java.sql.Timestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;

//@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter
public class Screening {

    private int screeningId;
    private String time;
    private String room;
    private String property;
    private User user;
    private Movie movie;
    private  Cinema cinema;

    public Screening(int screeningId, String time, String room, String property, User user, Movie movie, Cinema cinema) {
        this.screeningId = screeningId;
        this.time = time;
        this.room = room;
        this.property = property;
        this.user = user;
        this.movie = movie;
        this.cinema = cinema;
    }
}
