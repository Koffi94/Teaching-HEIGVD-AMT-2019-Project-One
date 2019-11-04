package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.User;
import ch.heigvd.amt.projectone.services.dao.ScreeningManagerLocal;
import ch.heigvd.amt.projectone.services.dao.UserManagerLocal;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet in charge of the login
 */
public class LoginServlet extends javax.servlet.http.HttpServlet {

    @EJB
    private UserManagerLocal userManager;

    @EJB
    private ScreeningManagerLocal screeningManager;

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException{
        request.getRequestDispatcher("./login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = userManager.findUserByName(request.getParameter("username"));
        if (user != null && BCrypt.checkpw(user.getPassword(), request.getParameter("password"))) {
            //get the old session and invalidate
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            //generate a new session
            HttpSession newSession = request.getSession(true);

            //setting session to expiry in 5 mins
            newSession.setMaxInactiveInterval(5*60);

            Cookie message = new Cookie("token", "Welcome");
            response.addCookie(message);

            request.setAttribute("user", user);
            request.setAttribute("screenings", screeningManager.findScreeningsByOwner(user));
            request.getRequestDispatcher("./WEB-INF/pages/dashboard.jsp").forward(request, response);
        } else {
            //PrintWriter out = response.getWriter();
            //out.println("<script>alert(\"Wrong password !\");</script>");
            request.getRequestDispatcher("./login.jsp").forward(request, response);
        }
    }
}
