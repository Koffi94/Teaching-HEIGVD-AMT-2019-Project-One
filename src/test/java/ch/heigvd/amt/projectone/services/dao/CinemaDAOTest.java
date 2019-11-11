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
import javax.ejb.EJB;

/**
 * This class is used to test the CinemaDAO methods
 */
@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class CinemaDAOTest {

    @EJB
    ICinemaDAO cinemaDAO;

    // Cinema parameters
    private static final String CINEMA_NAME = "cineTest";
    private static final String CITY = "testburg";
    private static final String PRICE = "2$";

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateACinema() {
        Cinema cinema = cinemaDAO.createCinema(CINEMA_NAME, CITY, PRICE);

        Assert.assertNotNull(cinema);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveACinema() {
        Cinema cinemaCreated = cinemaDAO.createCinema(CINEMA_NAME, CITY, PRICE);

        Cinema cinemaRetrieved = cinemaDAO.getCinema(cinemaCreated.getCinemaId());

        Assert.assertEquals(cinemaCreated, cinemaRetrieved);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateACinema() {
        Cinema cinema = cinemaDAO.createCinema(CINEMA_NAME, CITY, PRICE);

        int cinemaId = cinema.getCinemaId();

        String newCinemaName = "neoCineTest";

        String newCity = "newTestburg";

        String newPrice = "1$";

        cinemaDAO.updateCinema(cinemaId, newCinemaName, newCity, newPrice);

        Cinema updatedCinema = cinemaDAO.getCinema(cinemaId);

        Assert.assertEquals(newCinemaName, updatedCinema.getName());

        Assert.assertEquals(newCity, updatedCinema.getCity());

        Assert.assertEquals(newPrice, updatedCinema.getPrice());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteACinema() {
        Cinema cinema = cinemaDAO.createCinema(CINEMA_NAME, CITY, PRICE);

        cinemaDAO.deleteCinema(cinema.getCinemaId());

        Cinema cinemaSearched = cinemaDAO.getCinema(cinema.getCinemaId());

        Assert.assertNull(cinemaSearched);
    }
}
