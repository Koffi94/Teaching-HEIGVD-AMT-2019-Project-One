package ch.heigvd.amt.projectone.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

//@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter

public class User {

    private int id;
    private String username;
    private String password;
    private boolean active;

    public User(int id, String username, String password, boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active = active;
    }
}
