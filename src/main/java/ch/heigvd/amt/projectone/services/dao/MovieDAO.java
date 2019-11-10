package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Movie;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class MovieDAO implements IMovieDAO {

    @Resource(lookup = "jdbc/myDB")
    private DataSource dataSource;

    @Override
    public Movie createMovie(String title, String releaseYear, String category) {
        Movie movie = null;

        if (findMovieByTitle(title) == null) {
            try {
                Connection connection = dataSource.getConnection();

                // Check if the movie doesn't exist yet
                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO movie(title, release_year, category) VALUES (?, ?, ?)");
                pstmt.setString(1, title);
                pstmt.setString(2, releaseYear);
                pstmt.setString(3, category);
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                movie = getMovie(rs.getInt(1));

                connection.close();
            } catch (SQLException e) {
                Logger.getLogger(ScreeningDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return  movie;
    }

    @Override
    public Movie findMovieByTitle(String title) {
        Movie movie = null;

        try {
            Connection connection = dataSource.getConnection();

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM movie WHERE title = ?");
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                movie = Movie.builder()
                        .movieId(rs.getInt("movie_id"))
                        .title(rs.getString("title"))
                        .releaseYear(rs.getString("release_year"))
                        .category(rs.getString("category"))
                        .build();
            }

            connection.close();
        } catch(SQLException e) {
            Logger.getLogger(ScreeningDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return movie;
    }

    @Override
    public Movie getMovie(int movieId) {
        Movie movie = null;

        try {
            Connection connection = dataSource.getConnection();

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM movie WHERE movie_id = ?");
            pstmt.setInt(1, movieId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                movie = Movie.builder()
                        .movieId(rs.getInt("movie_id"))
                        .title(rs.getString("title"))
                        .releaseYear(rs.getString("release_year"))
                        .category(rs.getString("category"))
                        .build();
            }

            connection.close();
        } catch(SQLException e) {
            Logger.getLogger(ScreeningDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return movie;
    }

    @Override
    public void updateMovie(int movieId, String title, String releaseYear, String category) {
        if(getMovie(movieId) != null) {
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement("UPDATE movie SET title = ?, release_year = ?, category = ? WHERE movie_id = ?");
                pstmt.setString(1, title);
                pstmt.setString(2, releaseYear);
                pstmt.setString(3, category);
                pstmt.setInt(4, movieId);
                pstmt.executeUpdate();
                connection.close();
            } catch(SQLException e) {
                Logger.getLogger(ScreeningDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @Override
    public void deleteMovie(int movieId) {
        if(getMovie(movieId) != null) {
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement("DELETE FROM movie WHERE movie_id = ?");
                pstmt.setInt(1, movieId);
                pstmt.executeUpdate();
                connection.close();
            } catch(SQLException e) {
                Logger.getLogger(ScreeningDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
