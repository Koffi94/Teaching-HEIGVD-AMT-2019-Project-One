package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Movie;
import ch.heigvd.amt.projectone.model.Screening;
import ch.heigvd.amt.projectone.model.User;

import javax.annotation.Resource;
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

    @Override
    public void createScreening(Timestamp screeningTime, String roomName, String roomProperty, Movie movie, User owner) {
        try {
            Connection connection = dataSource.getConnection();

            // Check if the movie already exists. Create it if needed
            PreparedStatement pstmt = connection.prepareStatement("SELECT title FROM movie WHERE title = ?");
            pstmt.setString(1, movie.getTitle());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                pstmt = connection.prepareStatement("INSERT INTO movie VALUES (?, ?, ?)");
                pstmt.setString(1, movie.getTitle());
                pstmt.setDate(2, movie.getReleaseYear());
                pstmt.setString(3, movie.getCategory());
                pstmt.executeUpdate();
            }

            do {
                pstmt = connection.prepareStatement("INSERT INTO screening VALUES (?, ?, ?, ?, ?)");
                pstmt.setTimestamp(1, screeningTime);
                pstmt.setString(2, roomName);
                pstmt.setString(3, roomProperty);
                pstmt.setInt(4, movie.getMovieId());
                pstmt.setString(5, owner.getUsername());
                pstmt.executeUpdate();
            } while (rs.next());

            connection.close();
        } catch(SQLException e) {
            Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public List<Screening> getAllScreenings(User user) {
        List<Screening> screenings = new LinkedList<>();
        try {
            Connection connection = dataSource.getConnection();

            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT screening.screening_id, screening.screening_time, screening.room_name, screening.room_property, movie.movie_id, movie.title, movie.release_year, movie.category FROM screening " +
                            "INNER JOIN user ON screening.user_id = user.user_id " +
                            "INNER JOIN movie ON screening.movie_id = movie.movie_id " +
                            "WHERE user.user_id = ?");
            pstmt.setInt(1, user.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int screeningId = rs.getInt("screening.screening_id");
                Timestamp screeningTime = rs.getTimestamp("screening.screening_time");
                String roomName = rs.getString("screening.room_name");
                String roomProperty = rs.getString("screening.room_property");
                int movieId = rs.getInt("movie.movie_id");
                String movieTitle = rs.getString("movie.title");
                Date movieReleaseYear = rs.getDate("movie.release_year");
                String movieCategory = rs.getString("movie.category");
                screenings.add(new Screening(screeningId, screeningTime, roomName, roomProperty, new Movie(movieId, movieTitle, movieReleaseYear, movieCategory), user));
            }
            System.out.println("SCREENINGS: " + screenings.get(0).getMovie().getReleaseYear());
            connection.close();
        } catch(SQLException e) {
            Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return screenings;
    }

    @Override
    public void updateScreening(int screeningId, Timestamp screeningTime, String roomName, String roomProperty, Movie movie, User owner) {
        try {
            Connection connection = dataSource.getConnection();

            // Update screening then movie
            PreparedStatement pstmt = connection.prepareStatement("UPDATE screening SET screeningTime = ?, roomName = ?, roomProperty = ?, where screening_id = ?");
            pstmt.setTimestamp(1, screeningTime);
            pstmt.setString(2, roomName);
            pstmt.setString(3, roomProperty);
            pstmt.setInt(4, screeningId);
            pstmt.executeUpdate();

            pstmt = connection.prepareStatement("UPDATE movie SET title = ?, releaseDate = ?, category = ?, where movie_id = ?");
            pstmt.setString(1, movie.getTitle());
            pstmt.setDate(2, movie.getReleaseYear());
            pstmt.setString(3, movie.getCategory());
            pstmt.setInt(4, movie.getMovieId());
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

            // Check if the movie already exists. Create it if needed
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM screening WHERE screening_id = ?");
            pstmt.setInt(1, screeningId);
            pstmt.executeUpdate();
            connection.close();
        } catch(SQLException e) {
            Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
