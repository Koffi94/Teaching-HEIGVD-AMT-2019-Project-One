package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Cinema;

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
public class CinemaDAO implements ICinemaDAO {

    @Resource(lookup = "jdbc/myDB")
    private DataSource dataSource;

    @Override
    public Cinema createCinema(String name, String city, String price) {
        Cinema cinema = null;

        if (findCinemaByName(name) == null) {
            try {
                Connection connection = dataSource.getConnection();

                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO cinema(name, city, price) VALUES (?, ?, ?)");
                pstmt.setString(1, name);
                pstmt.setString(2, city);
                pstmt.setString(3, price);
                pstmt.executeUpdate();
                connection.close();

                cinema = findCinemaByName(name);
            } catch (SQLException e) {
                Logger.getLogger(ScreeningDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return cinema;
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
                cinema = Cinema.builder()
                        .cinemaId(rs.getInt("cinema_id"))
                        .name(rs.getString("name"))
                        .city(rs.getString("city"))
                        .price(rs.getString("price"))
                        .build();
            }

            connection.close();
        } catch(SQLException e) {
            Logger.getLogger(ScreeningDAO.class.getName()).log(Level.SEVERE, null, e);
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
                cinema = Cinema.builder()
                        .cinemaId(rs.getInt("cinema_id"))
                        .name(rs.getString("name"))
                        .city(rs.getString("city"))
                        .price(rs.getString("price"))
                        .build();
            }

            connection.close();
        } catch(SQLException e) {
            Logger.getLogger(ScreeningDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return cinema;
    }

    @Override
    public void updateCinema(int cinemaId, String name, String city, String price) {
        if(getCinema(cinemaId) != null) {
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement("UPDATE cinema SET name = ?, city = ?, price = ? WHERE cinema_id = ?");
                pstmt.setString(1, name);
                pstmt.setString(2, city);
                pstmt.setString(3, price);
                pstmt.setInt(4, cinemaId);
                pstmt.executeUpdate();
                connection.close();
            } catch(SQLException e) {
                Logger.getLogger(ScreeningDAO.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ScreeningDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
