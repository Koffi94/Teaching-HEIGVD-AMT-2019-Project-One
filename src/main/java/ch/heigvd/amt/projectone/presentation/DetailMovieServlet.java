package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.services.dao.MovieManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DetailMovieServlet extends HttpServlet {

    @EJB
    MovieManagerLocal movieManager;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("screening", movieManager.getMovie(Integer.parseInt(request.getParameter("movie_id"))));
        request.getRequestDispatcher("./WEB-INF/pages/DetailMovie.jsp").forward(request, response);
    }
}
