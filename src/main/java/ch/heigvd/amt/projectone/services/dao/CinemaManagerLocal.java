package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Cinema;

import javax.ejb.Local;

@Local
public interface CinemaManagerLocal {

    // CRUD
    public Cinema createCinema(String name, String city, String price);
    public Cinema findCinemaByName(String name);
    public Cinema getCinema(int cinemaId);
    public void updateCinema(int cinemaId, String name, String city, String price);
    public void deleteCinema(int cinemaId);
}
