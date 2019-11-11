package ch.heigvd.amt.projectone.model;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * This class is used to test the Cinema methods
 */
public class CinemaTest {

    @Test
    public void itShouldBePossibleToBuildACinema() {
        Cinema cinema = Cinema.builder()
                .cinemaId(1)
                .name("Pathe")
                .city("Lausanne")
                .price("$$ - $$$")
                .build();

        assertEquals(1, cinema.getCinemaId());
        assertEquals("Pathe", cinema.getName());
        assertEquals("Lausanne", cinema.getCity());
        assertEquals("$$ - $$$", cinema.getPrice());
    }

    @Test
    public void itShouldBePossibleToCloneACinema() {
        Cinema cinema = Cinema.builder()
                .cinemaId(1)
                .name("cinemaTest")
                .city("Lausanne")
                .price("$$ - $$$")
                .build();

        Cinema cloned = cinema.toBuilder().build();
        assertEquals(cinema, cloned);
        assertFalse(cinema == cloned);
    }
}