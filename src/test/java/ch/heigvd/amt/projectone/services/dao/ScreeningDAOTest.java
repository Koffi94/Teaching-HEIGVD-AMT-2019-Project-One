package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Cinema;
import ch.heigvd.amt.projectone.model.Movie;
import ch.heigvd.amt.projectone.model.Screening;
import ch.heigvd.amt.projectone.model.User;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class ScreeningDAOTest {

    @EJB
    IScreeningDAO screeningDAO;

    @EJB
    IUserDAO userDAO;

    @EJB
    IMovieDAO movieDAO;

    @EJB
    ICinemaDAO cinemaDAO;

    // Screening parameters
    private static final String TIME = "25:70";
    private static final String ROOM = "ZZZ";
    private static final String PROPERTY = "IMAX";
    // User parameters
    private static final String USERNAME = "userTest";
    private static final String PASSWD = "testpw";
    // Movie parameters
    private static final String MOVIE_NAME = "movieTest";
    private static final String RELEASE_YEAR = "1000";
    private static final String CATEGORY = "test";
    // Cinema parameters
    private static final String CINEMA_NAME = "cineTest";
    private static final String CITY = "testburg";
    private static final String PRICE = "2$";

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAScreening(){
        User user = userDAO.createUser(USERNAME, PASSWD);
        Movie movie = movieDAO.createMovie(MOVIE_NAME, RELEASE_YEAR, CATEGORY);
        Cinema cinema = cinemaDAO.createCinema(CINEMA_NAME, CITY, PRICE);

        screeningDAO.createScreening(TIME, ROOM, PROPERTY, user, movie, cinema);

        //TODO retrieve screening id
        //int screeningID = 1;

        Assert.assertNotNull(null);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveAScreening(){
        User user = userDAO.createUser(USERNAME, PASSWD);
        Movie movie = movieDAO.createMovie(MOVIE_NAME, RELEASE_YEAR, CATEGORY);
        Cinema cinema = cinemaDAO.createCinema(CINEMA_NAME, CITY, PRICE);

        screeningDAO.createScreening(TIME, ROOM, PROPERTY, user, movie, cinema);

    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateAScreening(){
        User user = userDAO.createUser(USERNAME, PASSWD);
        Movie movie = movieDAO.createMovie(MOVIE_NAME, RELEASE_YEAR, CATEGORY);
        Cinema cinema = cinemaDAO.createCinema(CINEMA_NAME, CITY, PRICE);

        screeningDAO.createScreening(TIME, ROOM, PROPERTY, user, movie, cinema);

    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteAScreening(){
        User user = userDAO.createUser(USERNAME, PASSWD);
        Movie movie = movieDAO.createMovie(MOVIE_NAME, RELEASE_YEAR, CATEGORY);
        Cinema cinema = cinemaDAO.createCinema(CINEMA_NAME, CITY, PRICE);

        screeningDAO.createScreening(TIME, ROOM, PROPERTY, user, movie, cinema);

    }
}
