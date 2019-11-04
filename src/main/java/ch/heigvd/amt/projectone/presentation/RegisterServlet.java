package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.services.dao.UserManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;

public class RegisterServlet extends HttpServlet {

    @EJB
    UserManagerLocal userManager;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("./WEB-INF/pages/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");

        if(checkAccount(username, password, repassword)) {
            userManager.createUser(username, BCrypt.hashpw(password, BCrypt.gensalt()));
        }

        response.sendRedirect("./login");
    }

    private boolean checkAccount(String username, String password, String repassword) {
        boolean passwordOk = false;

        if(userManager.findUserByName(username) == null && password.equals(repassword)) {
            passwordOk = true;
        }
        return passwordOk;
    }
}
