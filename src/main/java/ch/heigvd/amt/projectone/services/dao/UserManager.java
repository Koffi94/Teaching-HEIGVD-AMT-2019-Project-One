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

    }

    @Override
    public User getUser(String username, String password) {
        User user = null;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            // rs.next() returns false if no user exists
            if(rs.next() == false) {
                return user;
            }
            user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getBoolean("active"));
        } catch (SQLException e) {
            Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return user;
    }

    @Override
    public void updateUser(String username, String password, boolean active) {

    }

    @Override
    public void deleteUser(String username, String password) {

    }
}
