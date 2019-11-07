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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ManageScreeningServlet extends HttpServlet {

    final int PAGE_SIZE = 10;

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
            response.sendRedirect("./home");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String operation = request.getParameter("operation_get");
        User user = userManager.getUser((Integer) session.getAttribute("user_id"));
        int lastPage = (int) Math.ceil(screeningManager.getScreeningsQuantity(user) / (double)PAGE_SIZE);

        if(operation == null) {
            List<Screening> screenings = screeningManager.getScreeningsPage(user, PAGE_SIZE, 0);
            request.setAttribute("screenings", screenings);
            request.setAttribute("user", user);
            request.setAttribute("current_page", 1);
            request.setAttribute("last_page", lastPage);
            request.getRequestDispatcher("./WEB-INF/pages/dashboard.jsp").forward(request, response);
        } else {
            int screeningId = 0;
            switch (operation) {
                case "detail":
                    screeningId = Integer.parseInt(request.getParameter("screening_id"));
                    Screening screening = screeningManager.getScreening(screeningId);
                    System.out.println(screening.getMovie().getTitle());
                    request.setAttribute("screening", screening);
                    request.getRequestDispatcher("./WEB-INF/pages/detailScreening.jsp").forward(request, response);
                    break;
                case "delete":
                    screeningId = Integer.parseInt(request.getParameter("screening_id"));
                    screeningManager.deleteScreening(screeningId);
                    response.sendRedirect("./home");
                    break;
                case "page":
                    int currentPage = Integer.parseInt(request.getParameter("current_page"));
                    List<Screening> screenings = screeningManager.getScreeningsPage(user, PAGE_SIZE, (currentPage-1)*PAGE_SIZE);
                    request.setAttribute("screenings", screenings);
                    request.setAttribute("user", user);
                    request.setAttribute("current_page", currentPage);
                    request.setAttribute("last_page", lastPage);
                    request.getRequestDispatcher("./WEB-INF/pages/dashboard.jsp").forward(request, response);
                    break;
                default:
                    break;
            }
        }
    }
}
