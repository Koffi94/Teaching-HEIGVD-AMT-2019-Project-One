package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Cinema;
import ch.heigvd.amt.projectone.model.Movie;
import ch.heigvd.amt.projectone.model.Screening;
import ch.heigvd.amt.projectone.model.User;

import javax.ejb.Local;
import java.sql.Timestamp;
import java.util.List;

@Local
public interface IScreeningDAO {

    // CRUD
    public void createScreening(String time, String room, String property, User user, Movie movie, Cinema cinema);
    public Screening getScreening(int screeningId);
    public List<Screening> findScreeningsByOwner(User user);
    public List<Screening> getScreeningsPage(User user, int pageSize, int offset);
    public  Integer getScreeningsQuantity(User user);
    public void updateScreening(int screeningId, String time, String room, String property, User user, Movie movie, Cinema cinema);
    public void deleteScreening(int screeningId);

}
