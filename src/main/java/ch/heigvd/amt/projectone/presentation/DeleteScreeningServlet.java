package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.services.dao.ScreeningManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteScreeningServlet extends HttpServlet {

    @EJB
    private ScreeningManagerLocal screeningManager;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int screeningId = Integer.parseInt(request.getParameter("screeningId"));
        screeningManager.deleteScreening(screeningId);
        request.getRequestDispatcher("./WEB-INF/pages/dashboard.jsp").forward(request, response);
    }
}
