package ch.heigvd.amt.projectone.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter

/**
 * This is the domain object class for the Cinema table in the DB
 */
public class Cinema {

    private int cinemaId;
    private String name;
    private String city;
    private  String price;

}
