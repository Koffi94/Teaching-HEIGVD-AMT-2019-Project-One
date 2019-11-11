package ch.heigvd.amt.projectone.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter

/**
 * This is the domain object class for the Screening table in the DB
 */
public class Screening {

    private int screeningId;
    private String time;
    private String room;
    private String property;
    private User user;
    private Movie movie;
    private  Cinema cinema;

}
