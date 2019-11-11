package ch.heigvd.amt.projectone.model;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * This class is used to test the Movie methods
 */
public class MovieTest {

    @Test
    public void itShouldBePossibleToBuildAMovie() {
        Movie movie = Movie.builder()
                .movieId(1)
                .title("MIB")
                .releaseYear("2010")
                .category("Action")
                .build();

        assertEquals(1, movie.getMovieId());
        assertEquals("MIB", movie.getTitle());
        assertEquals("2010", movie.getReleaseYear());
        assertEquals("Action", movie.getCategory());
    }

    @Test
    public void itShouldBePossibleToCloneAMovie() {
        Movie movie = Movie.builder()
                .movieId(1)
                .title("MIB")
                .releaseYear("2010")
                .category("Action")
                .build();

        Movie cloned = movie.toBuilder().build();
        assertEquals(movie, cloned);
        assertFalse(movie == cloned);
    }
}