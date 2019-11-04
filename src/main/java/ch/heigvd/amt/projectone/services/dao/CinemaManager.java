package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Cinema;
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
public class CinemaManager implements CinemaManagerLocal {

    @Resource(lookup = "jdbc/myDB")
    private DataSource dataSource;

    @Override
    public void createCinema(String name) {
        if (findCinemaByName(name) == null) {
            try {
                Connection connection = dataSource.getConnection();

                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO cinema(name) VALUES (?)");
                pstmt.setString(1, name);
                pstmt.executeUpdate();

                connection.close();
            } catch (SQLException e) {
                Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @Override
    public Cinema findCinemaByName(String name) {
        Cinema cinema = null;

        try {
            Connection connection = dataSource.getConnection();

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM cinema WHERE name = ?");
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cinema = new Cinema(rs.getInt("cinema_id"), rs.getString("name"));
            }

            connection.close();
        } catch(SQLException e) {
            Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
        }

        return cinema;
    }

    @Override
    public Cinema getCinema(int cinemaId) {
        Cinema cinema = null;

        try {
            Connection connection = dataSource.getConnection();

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM cinema WHERE cinema_id = ?");
            pstmt.setInt(1, cinemaId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                cinema = new Cinema(rs.getInt("cinema_id"), rs.getString("name"));
            }

            connection.close();
        } catch(SQLException e) {
            Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
        }

        return cinema;
    }

    @Override
    public void updateCinema(int cinemaId, String name) {
        if(getCinema(cinemaId) != null) {
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement("UPDATE cinema SET name = ? WHERE cinema_id = ?");
                pstmt.setString(1, name);
                pstmt.setInt(2, cinemaId);
                pstmt.executeUpdate();
                connection.close();
            } catch(SQLException e) {
                Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @Override
    public void deleteCinema(int cinemaId) {
        if(getCinema(cinemaId) != null) {
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement("DELETE FROM cinema WHERE cinema_id = ?");
                pstmt.setInt(1, cinemaId);
                pstmt.executeUpdate();
                connection.close();
            } catch(SQLException e) {
                Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
