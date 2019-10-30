package ch.heigvd.amt.projectone.model;

import java.sql.Timestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;

//@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter
public class Screening {

    private int screeningId;
    private Timestamp screeningTime;
    private String roomName;
    private String roomProperty;
    private Movie movie;
    private User owner;

    public Screening(int screeningId, Timestamp screeningTime, String roomName, String roomProperty, Movie movie, User owner) {
        this.screeningId = screeningId;
        this.screeningTime = screeningTime;
        this.roomName = roomName;
        this.roomProperty = roomProperty;
        this.movie = movie;
        this.owner = owner;
    }
}
