package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.services.dao.IUserDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    @EJB
    /*private*/IUserDAO userDAO;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("./WEB-INF/pages/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");

        if(checkAccount(username, password, repassword)) {
            userDAO.createUser(username, password);
        }

        response.sendRedirect("./login");
    }

    private boolean checkAccount(String username, String password, String repassword) {
        boolean passwordOk = false;

        if(userDAO.findUserByName(username) == null && password.equals(repassword)) {
            passwordOk = true;
        }
        return passwordOk;
    }
}
