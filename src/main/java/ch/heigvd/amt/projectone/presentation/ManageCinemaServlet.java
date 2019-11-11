package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.Cinema;
import ch.heigvd.amt.projectone.services.dao.ICinemaDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet in charge of the management of Cinemas
 */
public class ManageCinemaServlet extends HttpServlet {

    @EJB
    ICinemaDAO cinemaManager;

    /**
     * This method is called when there is a POST request on /manageCinema
     * @param request The request object
     * @param response The response object
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String operation = request.getParameter("operation_post");
        String name = request.getParameter("name");
        String city = request.getParameter("city");
        String price = request.getParameter("price");

        switch (operation) {
            case "create" :
                cinemaManager.createCinema(name, city, price);
                break;
            case "update" :
                int cinemaId = Integer.parseInt(request.getParameter("cinema_id"));
                cinemaManager.updateCinema(cinemaId, name, city, price);
                break;
            default:
                break;
        }
        response.sendRedirect("./home");
    }

    /**
     * This method is called when there is a GET request on /manageCinema
     * @param request The request object
     * @param response The response object
     * @throws IOException
     * @throws ServletException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cinemaId = Integer.parseInt(request.getParameter("cinema_id"));
        String operation = request.getParameter("operation_get");

        switch (operation) {
            case "detail" :
                Cinema cinema = cinemaManager.getCinema(cinemaId);
                request.setAttribute("cinema", cinema);
                request.getRequestDispatcher("./WEB-INF/pages/detailCinema.jsp").forward(request, response);
                break;
            case "delete" :
                cinemaManager.deleteCinema(cinemaId);
                response.sendRedirect("./home");
                break;
            default:
                break;
        }
    }
}
