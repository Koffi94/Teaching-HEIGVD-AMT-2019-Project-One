package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.User;
import ch.heigvd.amt.projectone.services.dao.IScreeningDAO;
import ch.heigvd.amt.projectone.services.dao.IUserDAO;
import ch.heigvd.amt.projectone.services.dao.ScreeningDAO;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet in charge of the login
 */
public class LoginServlet extends javax.servlet.http.HttpServlet {

    @EJB
    /*private*/ IUserDAO userDAO; // private is commented to pass integration tests

    /**
     * This method is called when there is a GET request on /login
     * @param request The request object
     * @param response The response object
     * @throws javax.servlet.ServletException
     * @throws IOException
     */
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("authenticated") == null) {
            request.getRequestDispatcher("./WEB-INF/pages/login.jsp").forward(request, response);
        } else {
            response.sendRedirect("./home");
        }
    }

    /**
     * This method is called when there is a POST request on /login
     * @param request The request object
     * @param response The response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = userDAO.findUserByName(request.getParameter("username"));

        // If the user exists in the DB and password is correct then a session token is created for this user else go back to login page
        if (user != null && BCrypt.checkpw(request.getParameter("password"), user.getPassword())) {
            // Get the old session and invalidate
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            // Generate a new session
            HttpSession newSession = request.getSession(true);

            // Setting session to expiry in 5 mins
            newSession.setMaxInactiveInterval(60*5);

            // Set session attributes
            newSession.setAttribute("user_id", user.getUserId());
            newSession.setAttribute("username", user.getUsername());
            newSession.setAttribute("password", user.getPassword());
            newSession.setAttribute("authenticated", "yes");
            response.sendRedirect("./home");
        } else {
            request.getRequestDispatcher("./WEB-INF/pages/login.jsp").forward(request, response);
        }
    }
}
