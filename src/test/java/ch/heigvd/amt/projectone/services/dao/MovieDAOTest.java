package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Movie;

import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.DuplicateKeyException;
import javax.ejb.EJB;
import java.sql.SQLException;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class MovieDAOTest {

    @EJB
    IMovieDAO movieManager;

    // Movie parameters
    private static final String MOVIE_NAME = "movieTest";
    private static final String RELEASE_YEAR = "1000";
    private static final String CATEGORY = "test";

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAMovie(){
        Movie movie = movieManager.createMovie(MOVIE_NAME, RELEASE_YEAR, CATEGORY);

        Assert.assertNotNull(movie);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveAMovie(){
        Movie movieCreated = movieManager.createMovie(MOVIE_NAME, RELEASE_YEAR, CATEGORY);

        Movie movieRetrieved = movieManager.findMovieByTitle(MOVIE_NAME);

        Assert.assertEquals(movieCreated, movieRetrieved);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateAMovie(){
        Movie movie = movieManager.createMovie(MOVIE_NAME, RELEASE_YEAR, CATEGORY);

        int movieId = movie.getMovieId();

        String newTitle = "newTitle";

        String newReleaseYear = "2000";

        String newCategory = "nouvelle Vague";

        movieManager.updateMovie(movieId, newTitle, newReleaseYear, newCategory);

        //TODO Find why movie is not updated

        Assert.assertEquals(newTitle, movie.getTitle());

        Assert.assertEquals(newReleaseYear, movie.getReleaseYear());

        Assert.assertEquals(newCategory, movie.getCategory());

    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteAMovie() throws DuplicateKeyException, SQLException {
        Movie movie = movieManager.createMovie(MOVIE_NAME, RELEASE_YEAR, CATEGORY);

        movieManager.deleteMovie(movie.getMovieId());

        Movie movieSearch = movieManager.getMovie(movie.getMovieId());

        Assert.assertNull(movieSearch);

    }

}