package ch.heigvd.amt.projectone.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter

/**
 * This is the domain object class for the User table in the DB
 */
public class User {

    private int userId;
    private String username;
    private String password;
}
