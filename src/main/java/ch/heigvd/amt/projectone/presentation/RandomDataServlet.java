package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.services.dao.*;
import com.github.javafaker.Faker;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Fill the database with random data for test purpose
 */
public class RandomDataServlet extends HttpServlet {

    @Resource(lookup = "jdbc/myDB")
    private DataSource dataSource;

    // NUmber of data to generate
    private static final int ITERATIONS = 10;

    private String timestamp = null;
    private String sqlTimestamp = null;
    private String username = null;
    private String movieTitle = null;
    private String cinemaName = null;

    // We use Faker to generate random data
    // https://github.com/DiUS/java-faker
    private Faker faker = new Faker();

    @EJB
    MovieManagerLocal movieManager;

    @EJB
    CinemaManagerLocal cinemaManager;

    @EJB
    UserManagerLocal userManager;

    @EJB
    ScreeningManagerLocal screeningManager;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        generateRandomData();
        response.sendRedirect("./login");
    }

    public void generateRandomData() {

        for (int i = 0; i < ITERATIONS; i++) {
            timestamp = new SimpleDateFormat("HH.mm.ss").format(new java.util.Date());
            sqlTimestamp = new SimpleDateFormat("HH:mm").format(new java.util.Date());


            movieTitle = faker.book().title() + i;

            movieManager.createMovie(movieTitle,
                    randomYear(1900, 2020),
                    faker.book().genre());

            username = faker.name().firstName() + timestamp;

            userManager.createUser(username, "testpw" + i);

            cinemaName = faker.lorem().word() + i;

            cinemaManager.createCinema(cinemaName);

            screeningManager.createScreening(sqlTimestamp, faker.aviation().aircraft(), faker.stock().nyseSymbol(),
                    userManager.findUserByName(username), movieManager.findMovieByTitle(movieTitle),
                    cinemaManager.findCinemaByName(cinemaName));

        }
    }

    /**
     *
     * @param min Origin year
     * @param max Bound year
     * @return random year between min and max
     */
    private String randomYear(int min, int max) {
        long year = ThreadLocalRandom.current().nextInt(min, max);

        Date date = new Date(year);

        String randomYear = new SimpleDateFormat("yyyy").format(date);

        return randomYear;
    }
}

