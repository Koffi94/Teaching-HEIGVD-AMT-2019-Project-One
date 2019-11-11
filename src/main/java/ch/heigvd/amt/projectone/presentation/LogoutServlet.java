package ch.heigvd.amt.projectone.presentation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet in charge of the logout
 */
public class LogoutServlet extends HttpServlet {

    /**
     * This method is called when there is a GET request on /logout
     * @param request The request object
     * @param response The response object
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        response.sendRedirect("./login");
    }
}
