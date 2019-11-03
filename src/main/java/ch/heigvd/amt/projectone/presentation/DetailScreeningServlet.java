package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.Screening;
import ch.heigvd.amt.projectone.services.dao.ScreeningManager;
import ch.heigvd.amt.projectone.services.dao.ScreeningManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DetailScreeningServlet extends HttpServlet {

    @EJB
    ScreeningManagerLocal screeningManager;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("screening", screeningManager.getScreening(Integer.parseInt(request.getParameter("screening_id"))));
        request.getRequestDispatcher("./WEB-INF/pages/DetailScreening.jsp").forward(request, response);
    }
}
