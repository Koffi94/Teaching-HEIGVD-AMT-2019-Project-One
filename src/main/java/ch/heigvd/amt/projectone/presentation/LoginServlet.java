package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.services.dao.UserManager;
import ch.heigvd.amt.projectone.services.dao.UserManagerLocal;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet in charge of the login
 */
public class LoginServlet extends javax.servlet.http.HttpServlet {

    @EJB
    private UserManagerLocal userManager;

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException{
        request.getRequestDispatcher("./WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");


        if (userManager.checkIfUserExists(username, password)) {
            //get the old session and invalidate
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            //generate a new session
            HttpSession newSession = request.getSession(true);

            //setting session to expiry in 5 mins
            newSession.setMaxInactiveInterval(5*60);

            Cookie message = new Cookie("message", "Welcome");
            response.addCookie(message);
            request.getRequestDispatcher("./WEB-INF/pages/dashboard.jsp").forward(request, response);
        } else {
            PrintWriter out = response.getWriter();
            out.println("<font color=red>Either username or password is wrong.</font>");
            request.getRequestDispatcher("./WEB-INF/pages/login.jsp").forward(request, response);
        }
    }
}
