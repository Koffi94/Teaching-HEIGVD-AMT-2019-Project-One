package ch.heigvd.amt.projectone.model;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * This class is used to test the User methods
 */
public class UserTest {

    @Test
    public void itShouldBePossibleToBuildAUser() {
        User user = User.builder()
                .userId(1)
                .username("admin")
                .password("admin")
                .build();

        assertEquals(1, user.getUserId());
        assertEquals("admin", user.getUsername());
        assertEquals("admin", user.getPassword());
    }

    @Test
    public void itShouldBePossibleToCloneAUser() {
        User user = User.builder()
                .userId(1)
                .username("admin")
                .password("admin")
                .build();

        User cloned = user.toBuilder().build();
        assertEquals(user, cloned);
        assertFalse(user == cloned);
    }
}