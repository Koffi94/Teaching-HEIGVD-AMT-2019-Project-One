package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.services.dao.IUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import static org.mockito.Mockito.*;

/**
 * This class is used to test the RegisterServlet methods
 */
@ExtendWith(MockitoExtension.class)
public class RegisterServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    IUserDAO userDAO;

    RegisterServlet servlet;

    @BeforeEach
    public void setup() {
        servlet = new RegisterServlet();
        servlet.userDAO = userDAO;
    }

    @Nested
    class doGetTest {
        @Test
        void itShouldRedirectOnRegisterPage() throws ServletException, IOException {
            when(request.getRequestDispatcher("./WEB-INF/pages/register.jsp")).thenReturn(requestDispatcher);
            servlet.doGet(request, response);
            verify(request, times(1)).getRequestDispatcher("./WEB-INF/pages/register.jsp");
            verify(requestDispatcher, times(1)).forward(request, response);
        }
    }

    @Nested
    class doPostTest {

        String username = "user";
        String password = "password";

        @Test
        void itShouldRedirectOnLoginPage() throws IOException {
            when(request.getParameter("username")).thenReturn(username);
            when(request.getParameter("password")).thenReturn(password);
            when(request.getParameter("repassword")).thenReturn(password);
            servlet.doPost(request, response);
            verify(response, times(1)).sendRedirect("./login");
        }
    }
}