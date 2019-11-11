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
import java.util.ArrayList;
import java.util.Random;

/**
 * This servlet is used to fill the database with random data for test purpose
 */
public class RandomDataServlet extends HttpServlet {

    @Resource(lookup = "jdbc/myDB")
    private DataSource dataSource;

    // Number of data to generate
    private static final int ITERATIONS = 1000;
    // Parameters for generating a random year
    private static final int SEED = 12345;
    private static final int ORIGIN_YEAR = 1900;
    private static final int SPAN = 1021;
    //Parameters for generating a random hour
    private static final int HOURS = 25;
    private static final int MINUTES = 61;
    // Room properties
    private static final String[] roomProperties = {"2D", "3D", "4D"};
    // Room letter
    private static final String[] roomLetter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};
    // Room number limit
    private static final int ROOM_LIMIT = 31;
    // user password base
    private static final String PASSWD = "testpw";
    // Number of movie, user, cinema
    private static final int CREATE = 100;

    private String screeningTime = null;
    private String year = null;
    private String room = null;

    // Table of users
    private ArrayList<String> users = new ArrayList<>();
    // Table of movies
    private ArrayList<String> movies = new ArrayList<>();
    // Table of cinemas
    private ArrayList<String> cinemas = new ArrayList<>();

    private Random rand = new Random(SEED);

    // We use Faker to generate random data
    // https://github.com/DiUS/java-faker
    private Faker faker = new Faker();

    @EJB
    IMovieDAO movieManager;

    @EJB
    ICinemaDAO cinemaManager;

    @EJB
    IUserDAO userManager;

    @EJB
    IScreeningDAO screeningManager;

    /**
     * This method is called when there is a GET request on /generateRandomData
     * @param request The request object
     * @param response The response object
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        generateRandomData();
        response.sendRedirect("./login");
    }

    /**
     * This method is used to generate data and populate the DB
     */
    public void generateRandomData() {
        populateTables(CREATE);

        System.out.println("MOVIES:\n" + movies);

        for (int i = 0; i < ITERATIONS; i++) {
            screeningTime = String.valueOf(rand.nextInt(HOURS)) + ":" + String.valueOf(rand.nextInt(MINUTES));
            year = String.valueOf(ORIGIN_YEAR + rand.nextInt(SPAN));
            room = roomLetter[i % roomLetter.length] + rand.nextInt(ROOM_LIMIT);

            movieManager.createMovie(movies.get(i % movies.size()), year, faker.book().genre());

            userManager.createUser(users.get(i % users.size()), PASSWD + i);

            cinemaManager.createCinema(cinemas.get(i % cinemas.size()), "Lausanne", "$$");

            screeningManager.createScreening(screeningTime, room, roomProperties[i % roomProperties.length],
                    userManager.findUserByName(users.get(i % users.size())),
                    movieManager.findMovieByTitle(movies.get(i % movies.size())),
                    cinemaManager.findCinemaByName(cinemas.get(i % cinemas.size())));

        }
    }

    /**
     * This method is used to populate domain objects
     */
    private void populateTables(int tableSize){
        for(int j = 0; j < tableSize; j++){
            users.add(faker.name().firstName() + j);
            movies.add(faker.book().title() + j);
            cinemas.add(faker.lorem().word() + j);
        }
    }
}

