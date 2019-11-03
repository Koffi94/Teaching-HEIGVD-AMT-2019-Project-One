package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Movie;
import ch.heigvd.amt.projectone.model.Screening;
import ch.heigvd.amt.projectone.model.User;

import javax.ejb.Local;
import java.sql.Timestamp;
import java.util.List;

@Local
public interface ScreeningManagerLocal {

    // CRUD
    public void createScreening(Timestamp screeningTime, String roomName, String roomProperty, Movie movie, User owner);
    public Screening getScreening(int screeningId);
    public List<Screening> findScreeningsByOwner(User owner);
    public void updateScreening(int screeningId, Timestamp screeningTime, String roomName, String roomProperty, Movie movie, User owner);
    public void deleteScreening(int screeningId);

}
