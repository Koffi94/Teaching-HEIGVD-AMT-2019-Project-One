package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.Cinema;
import ch.heigvd.amt.projectone.services.dao.CinemaManagerLocal;
import ch.heigvd.amt.projectone.services.dao.MovieManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ManageCinemaServlet extends HttpServlet {

    @EJB
    CinemaManagerLocal cinemaManager;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String operation = request.getParameter("operation");
            int cinemaId = Integer.parseInt(request.getParameter("cinema_id"));
            String name = request.getParameter("name");

            switch (operation) {
                case "create" :
                    cinemaManager.createCinema(name);
                    break;
                case "update" :
                    cinemaManager.updateCinema(cinemaId, name);
                    break;
                default:
                    break;
            }
            response.sendRedirect("./login");
        } catch(Exception e) {

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cinemaId = Integer.parseInt(request.getParameter("cinemaId"));
        String operation = request.getParameter("operation");

        switch (operation) {
            case "detail" :
                Cinema cinema = cinemaManager.getCinema(cinemaId);
                request.setAttribute("cinema", cinema);
                request.getRequestDispatcher("./WEB-INF/pages/detailCinema.jsp").forward(request, response);
                break;
            case "delete" :
                cinemaManager.deleteCinema(cinemaId);
                response.sendRedirect("./login");
                break;
            default:
                break;
        }
    }
}
