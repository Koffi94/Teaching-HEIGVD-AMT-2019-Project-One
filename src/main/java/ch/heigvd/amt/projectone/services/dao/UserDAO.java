package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.User;
import org.mindrot.jbcrypt.BCrypt;

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
public class UserDAO implements IUserDAO {

    @Resource(lookup = "jdbc/myDB")
    private DataSource dataSource;

    @Override
    public User createUser(String username, String password) {
        User user = null;

        if (findUserByName(username) == null) {
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO user(username, password) VALUES (?, ?)");
                pstmt.setString(1, username);
                pstmt.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
                pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                user = getUser(rs.getInt(1));
                connection.close();
            } catch (SQLException e) {
                Logger.getLogger(ScreeningDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return user;
    }

    @Override
    public User findUserByName(String username) {
        User user = null;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                user = User.builder()
                        .userId(rs.getInt("user_id"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .build();
            }
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ScreeningDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return user;
    }

    @Override
    public User getUser(int userId) {
        User user = null;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM user WHERE user_id = ?");
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                user = User.builder()
                        .userId(rs.getInt("user_id"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .build();
            }
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ScreeningDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return user;
    }

    @Override
    public void updateUser(int userId, String username, String password) {
        if(getUser(userId) != null) {
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement("UPDATE user SET username = ?, password = ? WHERE user_id = ?");
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setInt(3, userId);
                pstmt.executeUpdate();
                connection.close();
            } catch (SQLException e) {
                Logger.getLogger(ScreeningDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @Override
    public void deleteUser(int userId) {
        if(getUser(userId) != null) {
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement("DELETE FROM user WHERE user_id = ?");
                pstmt.setInt(1, userId);
                pstmt.executeUpdate();
                connection.close();
            } catch (SQLException e) {
                Logger.getLogger(ScreeningDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @Override
    public boolean checkUser(String username, String password) {
        boolean userExists = false;
        User user = findUserByName(username);
        if (user != null && user.getPassword().equals(password)) {
            userExists = true;
        }
        return userExists;
    }
}
