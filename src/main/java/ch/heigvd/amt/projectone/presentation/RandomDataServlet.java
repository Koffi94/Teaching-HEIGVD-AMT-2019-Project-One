package ch.heigvd.amt.projectone.presentation;

import com.github.javafaker.Faker;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Fill the database with random data for test purpose
 */
public class RandomDataServlet extends HttpServlet {

    @Resource(lookup = "jdbc/myDB")
    private DataSource dataSource;

    // NUmber of data to generate
    private static final int ITERATIONS = 10;

    private String timestamp = null;
    private Timestamp sqlTimestamp = null;
    private int movieInserted = 0;
    private int userInserted = 0;
    private Random rand = new Random();
    private String username = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.printf("fjsiofjidsiofjsdofjosfjisdojsdi");
        generateRandomData();
        response.sendRedirect("./login");
    }

    public void generateRandomData(){
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement insertMovie = connection.prepareStatement("INSERT INTO movie VALUES (?,?,?);");
            PreparedStatement insertScreening = connection.prepareStatement("INSERT  INTO  screening VALUES (?, ?, ?, ?, ?)");
            PreparedStatement insertUser = connection.prepareStatement("INSERT INTO user VALUES (?, ?, ?)");

            // We use Faker to generate random data
            Faker faker = new Faker();

            // We disable autocommmit before inserting all the data
            connection.setAutoCommit(false);

            for(int i = 0; i < ITERATIONS; i++){
                timestamp = new SimpleDateFormat("HH.mm.ss").format(new java.util.Date());
                sqlTimestamp = new Timestamp(System.currentTimeMillis());

                insertMovie.setString(1, faker.book().title() + i);
                insertMovie.setDate(2, Date.valueOf(faker.business().creditCardExpiry()));
                insertMovie.setString(3, faker.book().genre());

                if(insertMovie.executeUpdate() > 0){
                    movieInserted++;
                }

                username = faker.name().firstName() + timestamp;
                insertUser.setString(1, username + i);
                insertUser.setString(2, "testpw" + i);
                insertUser.setBoolean(3, true);

                if(insertUser.executeUpdate() > 0){
                    userInserted++;
                }

                insertScreening.setTimestamp(1, sqlTimestamp);
                insertScreening.setString(2, faker.aviation().aircraft());
                insertScreening.setString(3, faker.stock().nyseSymbol());
                // To avoid obtaining a user id which value is 0
                insertScreening.setInt(4, rand.nextInt(movieInserted -1)+1);
                insertScreening.setString(5, username);

                insertScreening.executeUpdate();
            }

            // We enable autocommit once finished
            connection.setAutoCommit(true);

            connection.close();

        }catch(SQLException e){
            Logger.getLogger(RandomDataServlet.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}

