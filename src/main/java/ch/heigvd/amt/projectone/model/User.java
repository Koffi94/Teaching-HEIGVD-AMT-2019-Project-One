package ch.heigvd.amt.projectone.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

//@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter

public class User {

    private int userId;
    private String username;
    private String password;
    private boolean active;

    public User(int userId, String username, String password, boolean active) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.active = active;
    }
}
