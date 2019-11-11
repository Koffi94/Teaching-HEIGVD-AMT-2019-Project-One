package ch.heigvd.amt.projectone.model;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * This class is used to test the Screening methods
 */
public class ScreeningTest {

    @Test
    public void itShouldBePossibleToBuildAScreening() {
        User user = User.builder()
                .userId(1)
                .username("admin")
                .password("admin")
                .build();

        Movie movie = Movie.builder()
                .movieId(1)
                .title("MIB")
                .releaseYear("2010")
                .category("Action")
                .build();

        Cinema cinema = Cinema.builder()
                .cinemaId(1)
                .name("Pathe")
                .city("Lausanne")
                .price("$$ - $$$")
                .build();

        Screening screening = Screening.builder()
                .screeningId(1)
                .time("14:30")
                .room("R01")
                .property("3D")
                .user(user)
                .movie(movie)
                .cinema(cinema)
                .build();

        assertEquals(1, screening.getScreeningId());
        assertEquals("14:30", screening.getTime());
        assertEquals("R01", screening.getRoom());
        assertEquals("3D", screening.getProperty());
        assertEquals(user, screening.getUser());
        assertEquals(movie, screening.getMovie());
        assertEquals(cinema, screening.getCinema());
    }

    @Test
    public void itShouldBePossibleToCloneAScreening() {
        User user = User.builder()
                .userId(1)
                .username("admin")
                .password("admin")
                .build();

        Movie movie = Movie.builder()
                .movieId(1)
                .title("MIB")
                .releaseYear("2010")
                .category("Action")
                .build();

        Cinema cinema = Cinema.builder()
                .cinemaId(1)
                .name("Pathe")
                .city("Lausanne")
                .price("$$ - $$$")
                .build();

        Screening screening = Screening.builder()
                .screeningId(1)
                .time("14:30")
                .room("R01")
                .property("3D")
                .user(user)
                .movie(movie)
                .cinema(cinema)
                .build();

        Screening cloned = screening.toBuilder().build();
        assertEquals(screening, cloned);
        assertFalse(screening == cloned);
    }

}