package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.Movie;
import ch.heigvd.amt.projectone.model.User;
import ch.heigvd.amt.projectone.services.dao.MovieManagerLocal;
import ch.heigvd.amt.projectone.services.dao.ScreeningManager;
import ch.heigvd.amt.projectone.services.dao.ScreeningManagerLocal;
import ch.heigvd.amt.projectone.services.dao.UserManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateScreeningServlet extends HttpServlet {

    @EJB
    private UserManagerLocal userManager;

    @EJB
    private MovieManagerLocal movieManager;

    @EJB
    private ScreeningManagerLocal screeningManager;



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Transform the timestamp
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
            Date parsedDate = dateFormat.parse(request.getParameter("screening_time"));
            Timestamp screeningTime = new java.sql.Timestamp(parsedDate.getTime());

            // Get the movie
            Movie movie = movieManager.findMovieByTitle(request.getParameter("movie_name"));

            // Get the user
            User user = userManager.findUserByName(request.getParameter("user_id"));

            screeningManager.createScreening(screeningTime, request.getParameter("room_name"), request.getParameter("room_property"), movie, user);

            request.getRequestDispatcher("./WEB-INF/pages/dashboard.jsp").forward(request, response);
        } catch(Exception e) { //this generic but you can control another types of exception
            Logger.getLogger(ScreeningManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
