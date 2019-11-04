package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.Movie;
import ch.heigvd.amt.projectone.model.Screening;
import ch.heigvd.amt.projectone.model.User;
import ch.heigvd.amt.projectone.services.dao.MovieManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class ManageMovieServlet extends HttpServlet {
    @EJB
    MovieManagerLocal movieManager;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String operation = request.getParameter("operation");
            int movieId = Integer.parseInt(request.getParameter("movie_id"));
            String movieTitle = request.getParameter("movie_title");
            String movieReleaseYear = request.getParameter("movie_release_year");
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY");
            Date parsedDate = (Date) dateFormat.parse(movieReleaseYear);
            Date timestamp = new Date(parsedDate.getTime());
            String category = request.getParameter("category");

            switch (operation) {
                case "create" :
                    movieManager.createMovie(movieTitle, timestamp, category);
                    break;
                case "update" :
                    movieManager.updateMovie(movieId, movieTitle, timestamp, category);
                    break;
                default:
                    break;
            }
            request.getRequestDispatcher("./WEB-INF/pages/dashboard.jsp").forward(request, response);
        } catch(Exception e) {

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int movieId = Integer.parseInt(request.getParameter("movieId"));
        String operation = request.getParameter("operation");

        switch (operation) {
            case "detail" :
                Movie movie = movieManager.getMovie(movieId);
                request.setAttribute("movie", movie);
                request.getRequestDispatcher("./WEB-INF/pages/detailMovie.jsp").forward(request, response);
                break;
            case "delete" :
                movieManager.deleteMovie(movieId);
                request.getRequestDispatcher("./WEB-INF/pages/dashboard.jsp").forward(request, response);
                break;
            default:
                break;
        }
    }
}
