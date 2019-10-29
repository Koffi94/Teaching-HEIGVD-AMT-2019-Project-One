package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Movie;
import ch.heigvd.amt.projectone.model.Session;
import ch.heigvd.amt.projectone.model.User;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class SessionManager implements  SessionManagerLocal {

    @Resource(lookup = "jdbc/myDB")
    private DataSource dataSource;

    @Override
    public void createSession(Timestamp sessionTime, String roomName, String roomProperty, Movie movie, User owner) {
        try {
            Connection connection = dataSource.getConnection();

            // Check if the movie already exists. Create it if needed
            PreparedStatement pstmt = connection.prepareStatement("SELECT name FROM movie WHERE name = ?");
            pstmt.setString(1, movie.getName());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                pstmt = connection.prepareStatement("INSERT INTO movie VALUES (?, ?, ?)");
                pstmt.setString(1, movie.getName());
                pstmt.setDate(2, movie.getReleaseDate());
                pstmt.setString(3, movie.getCategory());
                pstmt.executeUpdate();
            }

            do {
                pstmt = connection.prepareStatement("INSERT INTO session VALUES (?, ?, ?, ?, ?)");
                pstmt.setTimestamp(1, sessionTime);
                pstmt.setString(2, roomName);
                pstmt.setString(3, roomProperty);
                pstmt.setInt(4, movie.getId());
                pstmt.setString(5, owner.getUsername());
                pstmt.executeUpdate();
            } while (rs.next());

            connection.close();
        } catch(SQLException e) {
            Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public LinkedList<Session> findAllSessions(User owner) {
        LinkedList<Session> sessions = new LinkedList<>();
        try {
            Connection connection = dataSource.getConnection();

            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT session.id, session.time, session.roomName, session.roomProperty, movie.id, movie.name, movie.releaseDate, movie.category FROM session " +
                            "INNER JOIN user ON session.owner = user.username " +
                            "INNER JOIN movie ON session.movie = movie.id " +
                            "WHERE user.username = ?");
            pstmt.setString(1, owner.getUsername());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int sessionId = rs.getInt("session.id");
                Timestamp sessionTime = rs.getTimestamp("session.time");
                String roomName = rs.getString("session.roomName");
                String roomProperty = rs.getString("session.roomProperty");
                int movieId = rs.getInt("movie.id");
                String movieName = rs.getString("movie.name");
                Date movieReleaseDate = rs.getDate("movie.releaseDate");
                String movieCategory = rs.getString("movie.category");
                sessions.add(new Session(sessionId, sessionTime, roomName, roomProperty, new Movie(movieId, movieName, movieReleaseDate, movieCategory), owner));
            }
            connection.close();
        } catch(SQLException e) {
            Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return sessions;
    }

    @Override
    public void updateSession(int sessionId, Timestamp sessionTime, String roomName, String roomProperty, Movie movie, User owner) {
        try {
            Connection connection = dataSource.getConnection();

            // Update session then movie
            PreparedStatement pstmt = connection.prepareStatement("UPDATE session SET sessionTime = ?, roomName = ?, roomProperty = ?, where id = ?");
            pstmt.setTimestamp(1, sessionTime);
            pstmt.setString(2, roomName);
            pstmt.setString(3, roomProperty);
            pstmt.setInt(4, sessionId);
            pstmt.executeUpdate();

            pstmt = connection.prepareStatement("UPDATE movie SET name = ?, releaseDate = ?, category = ?, where id = ?");
            pstmt.setString(1, movie.getName());
            pstmt.setDate(2, movie.getReleaseDate());
            pstmt.setString(3, movie.getCategory());
            pstmt.setInt(4, movie.getId());
            pstmt.executeUpdate();
        } catch(SQLException e) {
            Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void deleteSession(int sessionId) {
        try {
            Connection connection = dataSource.getConnection();

            // Check if the movie already exists. Create it if needed
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM session WHERE id = ?");
            pstmt.setInt(1, sessionId);
            pstmt.executeUpdate();
        } catch(SQLException e) {
            Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
