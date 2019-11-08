package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Cinema;
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
public class CinemaDAOTest {

    @EJB
    CinemaManagerLocal cinemaManager;

    // Cinema parameters
    private static final String CINEMA_NAME = "cineTest";
    private static final String CITY = "testburg";
    private static final String PRICE = "2$";

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateACinema() throws DuplicateKeyException, SQLException {
        Cinema cinema = cinemaManager.createCinema(CINEMA_NAME, CITY, PRICE);

        Assert.assertNotNull(cinema);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveACinema() throws DuplicateKeyException, SQLException {
        Cinema cinemaCreated = cinemaManager.createCinema(CINEMA_NAME, CITY, PRICE);

        Cinema cinemaRetrieved = cinemaManager.getCinema(cinemaCreated.getCinemaId());

        Assert.assertEquals(cinemaCreated, cinemaRetrieved);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateACinema() throws DuplicateKeyException, SQLException {
        Cinema cinema = cinemaManager.createCinema(CINEMA_NAME, CITY, PRICE);

        int cinemaId = cinema.getCinemaId();

        String newCinemaName = "neoCineTest";

        String newCity = "newTestburg";

        String newPrice = "1$";

        cinemaManager.updateCinema(cinemaId, newCinemaName, newCity, newPrice);

        Assert.assertEquals(newCinemaName, cinema.getName());

        Assert.assertEquals(newCity, cinema.getCity());

        Assert.assertEquals(newPrice, cinema.getPrice());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteACinema() throws DuplicateKeyException, SQLException {
        Cinema cinema = cinemaManager.createCinema(CINEMA_NAME, CITY, PRICE);

        cinemaManager.deleteCinema(cinema.getCinemaId());

        Cinema cinemaSearched = cinemaManager.getCinema(cinema.getCinemaId());

        Assert.assertNull(cinemaSearched);
    }
}
