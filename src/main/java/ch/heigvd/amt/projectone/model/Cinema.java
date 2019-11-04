package ch.heigvd.amt.projectone.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

//@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter

public class Cinema {

    private int cinemaId;
    private String name;

    public Cinema(int cinemaId, String name) {
        this.cinemaId = cinemaId;
        this.name = name;
    }
}
