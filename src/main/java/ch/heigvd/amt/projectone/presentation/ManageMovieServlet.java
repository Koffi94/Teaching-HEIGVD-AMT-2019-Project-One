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
            String operation = request.getParameter("operation_post");
            String title = request.getParameter("title");
            String releaseYear = request.getParameter("release_year");
            String category = request.getParameter("category");
            switch (operation) {
                case "create" :
                    movieManager.createMovie(title, releaseYear, category);
                    break;
                case "update" :
                    int movieId = Integer.parseInt(request.getParameter("movie_id"));
                    movieManager.updateMovie(movieId, title, releaseYear, category);
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
        int movieId = Integer.parseInt(request.getParameter("movie_id"));
        String operation = request.getParameter("operation_get");

        switch (operation) {
            case "detail" :
                Movie movie = movieManager.getMovie(movieId);
                request.setAttribute("movie", movie);
                request.getRequestDispatcher("./WEB-INF/pages/detailMovie.jsp").forward(request, response);
                break;
            case "delete" :
                movieManager.deleteMovie(movieId);
                response.sendRedirect("./home");
                break;
            default:
                break;
        }
    }
}
