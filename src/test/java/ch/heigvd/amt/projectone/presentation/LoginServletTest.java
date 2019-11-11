package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.User;
import ch.heigvd.amt.projectone.services.dao.IUserDAO;
import org.junit.jupiter.api.AfterEach;
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
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import static org.mockito.Mockito.*;


/**
 * This class is used to test the LoginServlet methods
 */
@ExtendWith(MockitoExtension.class)
public class LoginServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session, oldSession, newSession;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    IUserDAO userDAO;

    @Mock
    User user;

    LoginServlet servlet;

    @BeforeEach
    public void setup() {
        servlet = new LoginServlet();
        servlet.userDAO = userDAO;
    }

    @Nested
    class doGetTest {

        @BeforeEach
        public void setup() {
            when(request.getSession()).thenReturn(session);
        }

        @Test
        void itShouldRedirectOnHomePageIfUserIsAuthenticated() throws ServletException, IOException {
            when(session.getAttribute("authenticated")).thenReturn("yes");
            servlet.doGet(request, response);
            verify(response, times(1)).sendRedirect("./home");
        }

        @Test
        void itShouldRedirectOnLoginPageIfUserIsNotAuthenticated() throws ServletException, IOException {
            when(session.getAttribute("authenticated")).thenReturn(null);
            when(request.getRequestDispatcher("./WEB-INF/pages/login.jsp")).thenReturn(requestDispatcher);
            servlet.doGet(request, response);
            verify(request, times(1)).getRequestDispatcher("./WEB-INF/pages/login.jsp");
            verify(requestDispatcher, times(1)).forward(request, response);
        }
    }

    @Nested
    class doPostTest {

        String username = "user";
        String badPswd = "WrongPass";
        String goodPswd = "GoodPass";

        @BeforeEach
        public void setup() {
            when(request.getParameter("username")).thenReturn(username);
        }

        @Nested
        class wrongCredsTest {

            @BeforeEach
            public void setup() {
                when(request.getRequestDispatcher("./WEB-INF/pages/login.jsp")).thenReturn(requestDispatcher);
            }

            @Test
            void itShouldRedirectOnLoginPageIfTheUserDoesntExistInTheDB() throws ServletException, IOException {
                when(userDAO.findUserByName(username)).thenReturn(null);
                servlet.doPost(request, response);
            }

            @Test
            void itShouldRedirectOnLoginPageIfThePasswordIsWrong() throws ServletException, IOException {
                when(userDAO.findUserByName(username)).thenReturn(user);
                when(request.getParameter("password")).thenReturn(badPswd);
                when(user.getPassword()).thenReturn(BCrypt.hashpw(goodPswd, BCrypt.gensalt()));
                servlet.doPost(request, response);
            }

            @AfterEach
            void tearDown() throws ServletException, IOException {
                verify(request, times(1)).getRequestDispatcher("./WEB-INF/pages/login.jsp");
                verify(requestDispatcher, times(1)).forward(request, response);
            }
        }


        @Test
        void itShouldRedirectOnHomePageIfUsernameAndPasswordAreOK() throws ServletException, IOException {
            when(request.getSession(false)).thenReturn(oldSession);
            when(request.getSession(true)).thenReturn(newSession);

            when(userDAO.findUserByName(username)).thenReturn(user);

            when(request.getParameter("password")).thenReturn(goodPswd);
            when(user.getPassword()).thenReturn(BCrypt.hashpw(goodPswd, BCrypt.gensalt()));

            servlet.doPost(request, response);
            verify(newSession, times(1)).setMaxInactiveInterval(60*5);
            verify(newSession, times(1)).setAttribute("user_id", user.getUserId());
            verify(newSession, times(1)).setAttribute("username", user.getUsername());
            verify(newSession, times(1)).setAttribute("password", user.getPassword());
            verify(newSession, times(1)).setAttribute("authenticated", "yes");
            verify(response, times(1)).sendRedirect("./home");
        }
    }

}