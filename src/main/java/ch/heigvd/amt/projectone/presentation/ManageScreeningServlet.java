package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.Cinema;
import ch.heigvd.amt.projectone.model.Movie;
import ch.heigvd.amt.projectone.model.Screening;
import ch.heigvd.amt.projectone.model.User;
import ch.heigvd.amt.projectone.services.dao.CinemaManagerLocal;
import ch.heigvd.amt.projectone.services.dao.MovieManagerLocal;
import ch.heigvd.amt.projectone.services.dao.ScreeningManagerLocal;
import ch.heigvd.amt.projectone.services.dao.UserManagerLocal;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ManageScreeningServlet extends HttpServlet {

    @EJB
    ScreeningManagerLocal screeningManager;

    @EJB
    UserManagerLocal userManager;

    @EJB
    MovieManagerLocal movieManager;

    @EJB
    CinemaManagerLocal cinemaManager;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String operation = request.getParameter("operation_post");
            String time = request.getParameter("screening_time");
            String room = request.getParameter("room");
            String property = request.getParameter("property");
            String userId = request.getParameter("user_id");
            User user = userManager.getUser(Integer.parseInt(userId));
            String movieTitle = request.getParameter("movie_title");
            Movie movie = movieManager.findMovieByTitle(movieTitle);
            String cinemaName = request.getParameter("cinema_name");
            Cinema cinema = cinemaManager.findCinemaByName(cinemaName);

            switch (operation) {
                case "create" :
                    screeningManager.createScreening(time, room, property, user, movie, cinema);
                    break;
                case "update" :
                    int screeningId = Integer.parseInt(request.getParameter("screening_id"));
                    screeningManager.updateScreening(screeningId, time, room, property, user, movie, cinema);
                    break;
                default:
                    break;
            }
            response.sendRedirect("./login");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int screeningId = Integer.parseInt(request.getParameter("screening_id"));
        String operation = request.getParameter("operation_get");

        switch (operation) {
            case "detail" :
                Screening screening = screeningManager.getScreening(screeningId);
                System.out.println(screening.getMovie().getTitle());
                request.setAttribute("screening", screening);
                request.getRequestDispatcher("./WEB-INF/pages/detailScreening.jsp").forward(request, response);
                break;
            case "delete" :
                screeningManager.deleteScreening(screeningId);
                response.sendRedirect("./login");
                break;
            default:
                break;
        }
    }
}
