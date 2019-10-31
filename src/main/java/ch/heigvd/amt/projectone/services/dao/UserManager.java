package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.User;

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
public class UserManager implements UserManagerLocal {

    @Resource(lookup = "jdbc/myDB")
    private DataSource dataSource;

    @Override
    public void createUser(String username, String password, boolean active) {

        if(getUser(username) == null) {
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO user VALUES (?, ?, ?)");
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setBoolean(3, active);
                pstmt.executeUpdate();
                connection.close();
            } catch (SQLException e) {
                Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @Override
    public User getUser(String username) {
        User user = null;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            // rs.next() returns false if no user exists
            if(rs.next() == false) {
                return user;
            }
            user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getBoolean("active"));
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return user;
    }

    @Override
    public void updateUser(String username, String password, boolean active) {
        if(getUser(username) != null) {
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement("UPDATE user SET username = ?, password = ?, active = ?");
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setBoolean(3, active);
                pstmt.executeUpdate();
                connection.close();
            } catch (SQLException e) {
                Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @Override
    public void deleteUser(String username) {
        if(getUser(username) == null) {
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement("DELETE FROM user WHERE username = ?");
                pstmt.setString(1, username);
                pstmt.executeUpdate();
                connection.close();
            } catch (SQLException e) {
                Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
