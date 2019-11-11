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
    /*private*/IUserDAO userDAO; // private is commented to pass integration tests

    /**
     * This method is called when there is a GET request on /register
     * @param request The request object
     * @param response The response object
     * @throws IOException
     * @throws ServletException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("./WEB-INF/pages/register.jsp").forward(request, response);
    }

    /**
     * This method is called when there is a POST request on /register
     * @param request The request object
     * @param response The response object
     * @throws IOException
     * @throws ServletException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");

        if(checkAccount(username, password, repassword)) {
            userDAO.createUser(username, password);
        }

        response.sendRedirect("./login");
    }

    /**
     * This is method is used to check if an account exists in the DB
     * @param username This is the username of the account
     * @param password This is the password of the account
     * @param repassword This is the password of the account
     * @return
     */
    private boolean checkAccount(String username, String password, String repassword) {
        boolean passwordOk = false;

        if(userDAO.findUserByName(username) == null && password.equals(repassword)) {
            passwordOk = true;
        }
        return passwordOk;
    }
}
