package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Cinema;
import ch.heigvd.amt.projectone.model.Movie;
import ch.heigvd.amt.projectone.model.Screening;
import ch.heigvd.amt.projectone.model.User;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class ScreeningManager implements ScreeningManagerLocal {

    @Resource(lookup = "jdbc/myDB")
    private DataSource dataSource;

    @EJB
    private UserManagerLocal userManager;

    @EJB
    private MovieManagerLocal movieManager;

    @EJB
    private CinemaManagerLocal cinemaManager;

    @Override
    public void createScreening(String time, String room, String property, User user, Movie movie, Cinema cinema) {
        try {
            Connection connection = dataSource.getConnection();

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO screening(time, room, property, user_id, movie_id, cinema_id) VALUES (?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, time);
            pstmt.setString(2, room);
            pstmt.setString(3, property);
            pstmt.setInt(4, user.getUserId());
            pstmt.setInt(5, movie.getMovieId());
            pstmt.setInt(6, cinema.getCinemaId());
            pstmt.executeUpdate();

            connection.close();
        } catch(SQLException e) {
            Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public Screening getScreening(int screeningId) {
        Screening screening = null;
        try {
            Connection connection = dataSource.getConnection();

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM screening WHERE screening_id = ?");
            pstmt.setInt(1, screeningId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                User user = userManager.getUser(rs.getInt("user_id"));
                Movie movie = movieManager.getMovie(rs.getInt("movie_id"));
                Cinema cinema = cinemaManager.getCinema(rs.getInt("cinema_id"));
                screening = new Screening(rs.getInt("screening_id"), rs.getString("time"), rs.getString("room"), rs.getString("property"), user, movie, cinema);
            }
            connection.close();
        } catch(SQLException e) {
            Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return screening;
    }

    @Override
    public List<Screening> findScreeningsByOwner(User user) {
        List<Screening> screenings = new LinkedList<>();
        try {
            Connection connection = dataSource.getConnection();

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM screening WHERE user_id = ?");
            pstmt.setInt(1, user.getUserId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int screeningId = rs.getInt("screening_id");
                String time = rs.getString("time");
                String room = rs.getString("room");
                String property = rs.getString("property");
                int movieId = rs.getInt("movie_id");
                Movie movie = movieManager.getMovie(movieId);
                int cinemaId = rs.getInt("cinema_id");
                Cinema cinema = cinemaManager.getCinema(cinemaId);
                screenings.add(new Screening(screeningId, time, room, property, user, movie, cinema));
            }
            connection.close();
        } catch(SQLException e) {
            Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return screenings;
    }

    @Override
    public void updateScreening(int screeningId, String time, String room, String property, User user, Movie movie, Cinema cinema) {
        try {
            Connection connection = dataSource.getConnection();

            // Update screening
            PreparedStatement pstmt = connection.prepareStatement("UPDATE screening SET time = ?, room = ?, property = ?, movie_id = ?, cinema_id = ? where screening_id = ?");
            pstmt.setString(1, time);
            pstmt.setString(2, room);
            pstmt.setString(3, property);
            pstmt.setInt(4, movie.getMovieId());
            pstmt.setInt(5, cinema.getCinemaId());
            pstmt.setInt(6, screeningId);
            pstmt.executeUpdate();

            connection.close();
        } catch(SQLException e) {
            Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void deleteScreening(int screeningId) {
        try {
            Connection connection = dataSource.getConnection();

            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM screening WHERE screening_id = ?");
            pstmt.setInt(1, screeningId);
            pstmt.executeUpdate();
            connection.close();
        } catch(SQLException e) {
            Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
