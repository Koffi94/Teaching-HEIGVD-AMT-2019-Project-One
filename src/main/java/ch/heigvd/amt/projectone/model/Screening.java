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
    private User owner;
    private Movie movie;

    public Screening(int screeningId, Timestamp screeningTime, String roomName, String roomProperty, User owner, Movie movie) {
        this.screeningId = screeningId;
        this.screeningTime = screeningTime;
        this.roomName = roomName;
        this.roomProperty = roomProperty;
        this.owner = owner;
        this.movie = movie;
    }
}
