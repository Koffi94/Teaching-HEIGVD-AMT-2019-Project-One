package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.Movie;
import ch.heigvd.amt.projectone.model.Screening;
import ch.heigvd.amt.projectone.model.User;
import ch.heigvd.amt.projectone.services.dao.MovieManagerLocal;
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

public class ManageScreeningServlet extends HttpServlet {

    @EJB
    ScreeningManagerLocal screeningManager;

    @EJB
    UserManagerLocal userManager;

    @EJB
    MovieManagerLocal movieManager;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String operation = request.getParameter("operation");
            int screeningId = Integer.parseInt(request.getParameter("screening_id"));
            String screeningTime = request.getParameter("screening_time");
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
            Date parsedDate = dateFormat.parse(screeningTime);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            String roomName = request.getParameter("room_name");
            String roomProperty = request.getParameter("room_property");
            String userId = request.getParameter("user_id");
            User user = userManager.getUser(Integer.parseInt(userId));
            String movieId = request.getParameter("movie_id");
            Movie movie = movieManager.getMovie(Integer.parseInt(movieId));

            switch (operation) {
                case "create" :
                    screeningManager.createScreening(timestamp, roomName, roomProperty, movie, user);
                    break;
                case "update" :
                    screeningManager.updateScreening(screeningId, timestamp, roomName, roomProperty, movie, user);
                    break;
                default:
                    break;
            }
            request.getRequestDispatcher("./WEB-INF/pages/dashboard.jsp").forward(request, response);
        } catch(Exception e) {

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int screeningId = Integer.parseInt(request.getParameter("screeningId"));
        String operation = request.getParameter("operation");

        switch (operation) {
            case "detail" :
                Screening screening = screeningManager.getScreening(screeningId);
                request.setAttribute("screening", screening);
                request.getRequestDispatcher("./WEB-INF/pages/dashboard.jsp").forward(request, response);
                break;
            case "delete" :
                screeningManager.deleteScreening(screeningId);
                request.getRequestDispatcher("./WEB-INF/pages/dashboard.jsp").forward(request, response);
                break;
            default:
                break;
        }
    }
}
