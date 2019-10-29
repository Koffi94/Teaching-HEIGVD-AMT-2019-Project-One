package ch.heigvd.amt.projectone.model;

import java.sql.Timestamp;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

//@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter
public class Session {

    private int id;
    private Timestamp time;
    private String roomName;
    private String roomProperty;
    private Movie movie;
    private User owner;

    public Session(int id, Timestamp time, String roomName, String roomProperty, Movie movie, User owner) {
        this.id = id;
        this.time = time;
        this.roomName = roomName;
        this.roomProperty = roomProperty;
        this.movie = movie;
        this.owner = owner;
    }
}
