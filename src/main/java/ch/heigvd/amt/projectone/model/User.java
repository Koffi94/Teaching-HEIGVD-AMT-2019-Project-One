package ch.heigvd.amt.projectone.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter

public class User {

    private String username;
    private String password;
    private boolean active;

}
