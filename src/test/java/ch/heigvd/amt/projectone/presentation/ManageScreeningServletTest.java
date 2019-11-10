package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.Cinema;
import ch.heigvd.amt.projectone.model.Movie;
import ch.heigvd.amt.projectone.model.Screening;
import ch.heigvd.amt.projectone.model.User;
import ch.heigvd.amt.projectone.services.dao.ICinemaDAO;
import ch.heigvd.amt.projectone.services.dao.IMovieDAO;
import ch.heigvd.amt.projectone.services.dao.IScreeningDAO;
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

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManageScreeningServletTest {

    @Mock
    IScreeningDAO screeningDAO;

    @Mock
    IUserDAO userDAO;

    @Mock
    IMovieDAO movieDAO;

    @Mock
    ICinemaDAO cinemaDAO;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    User user;

    @Mock
    Movie movie;

    @Mock
    Cinema cinema;

    @Mock
    Screening screening;

    @Mock
    List<Screening> screenings;

    ManageScreeningServlet servlet;
    int screeningId = 1;


    @BeforeEach
    public void setup() {
        servlet = new ManageScreeningServlet();
        servlet.screeningDAO = screeningDAO;
        servlet.userDAO = userDAO;
        servlet.movieDAO = movieDAO;
        servlet.cinemaDAO = cinemaDAO;
    }

    @Nested
    class doGetTest {
        final int PAGE_SIZE = 10;
        int userId = 1;
        int currentPage = 2;
        int lastPage = 10;

        @BeforeEach
        public void setup() {
            when(request.getSession()).thenReturn(session);
            when(session.getAttribute("user_id")).thenReturn(userId);
            when(userDAO.getUser(userId)).thenReturn(user);
            when(screeningDAO.getScreeningsQuantity(user)).thenReturn(100);
        }

        @Test
        void itShouldRedirectOnHomePageIfOperationParameterIsNull() throws ServletException, IOException {
            when(request.getParameter("operation_get")).thenReturn(null);
            when(screeningDAO.getScreeningsPage(user, PAGE_SIZE, 0)).thenReturn(screenings);
            when(request.getRequestDispatcher("./WEB-INF/pages/dashboard.jsp")).thenReturn(requestDispatcher);
            servlet.doGet(request, response);
            verify(request, times(1)).setAttribute("screenings", screenings);
            verify(request, times(1)).setAttribute("user", user);
            verify(request, times(1)).setAttribute("current_page", 1);
            verify(request, times(1)).setAttribute("last_page", lastPage);
            verify(requestDispatcher, times(1)).forward(request, response);
        }

        @Test
        void itShouldRedirectOnDetailScreeningPageIfOperationParameterIsDetail() throws ServletException, IOException {
            when(request.getParameter("operation_get")).thenReturn("detail");
            when(request.getParameter("screening_id")).thenReturn(String.valueOf(screeningId));
            when(screeningDAO.getScreening(screeningId)).thenReturn(screening);
            when(screening.getMovie()).thenReturn(movie);
            when(movie.getTitle()).thenReturn("test");
            when(request.getRequestDispatcher("./WEB-INF/pages/detailScreening.jsp")).thenReturn(requestDispatcher);
            servlet.doGet(request, response);
            verify(request, times(1)).setAttribute("screening", screening);
            verify(requestDispatcher, times(1)).forward(request, response);
        }

        @Test
        void itShouldRedirectOnHomePageIfOperationParameterIsDelete() throws ServletException, IOException {
            when(request.getParameter("operation_get")).thenReturn("delete");
            when(request.getParameter("screening_id")).thenReturn(String.valueOf(screeningId));
            servlet.doGet(request, response);
            verify(screeningDAO).deleteScreening(screeningId);
            verify(response, times(1)).sendRedirect("./home");
        }

        @Test
        void itShouldRedirectOnHomePageIfOperationParameterIsPage() throws ServletException, IOException {
            when(request.getParameter("operation_get")).thenReturn("page");
            when(request.getParameter("current_page")).thenReturn(String.valueOf(currentPage));
            when(screeningDAO.getScreeningsPage(user, PAGE_SIZE, (currentPage-1)*PAGE_SIZE)).thenReturn(screenings);
            when(request.getRequestDispatcher("./WEB-INF/pages/dashboard.jsp")).thenReturn(requestDispatcher);
            servlet.doGet(request, response);
            verify(request, times(1)).setAttribute("screenings", screenings);
            verify(request, times(1)).setAttribute("user", user);
            verify(request, times(1)).setAttribute("current_page", currentPage);
            verify(request, times(1)).setAttribute("last_page", lastPage);
            verify(requestDispatcher, times(1)).forward(request, response);
        }
    }

    @Nested
    class doPostTest {
        String time = "12:00";
        String room = "R01";
        String property = "3D";
        String userId = "1";
        String movieTitle = "movieTest";
        String cinemaName = "cineTest";
        int screeningId = 1;

        @BeforeEach
        public void setup() {
            when(request.getParameter("screening_time")).thenReturn(time);
            when(request.getParameter("room")).thenReturn(room);
            when(request.getParameter("property")).thenReturn(property);
            when(request.getParameter("user_id")).thenReturn(userId);
            when(userDAO.getUser(Integer.parseInt(userId))).thenReturn(user);
            when(request.getParameter("movie_title")).thenReturn(movieTitle);
            when(movieDAO.findMovieByTitle(movieTitle)).thenReturn(movie);
            when(request.getParameter("cinema_name")).thenReturn(cinemaName);
            when(cinemaDAO.findCinemaByName(cinemaName)).thenReturn(cinema);
        }

        @Test
        void itShouldRedirectOnHomePageIfOperationParameterIsCreate() throws IOException {
            when(request.getParameter("operation_post")).thenReturn("create");
            servlet.doPost(request,response);
            verify(screeningDAO, times(1)).createScreening(time, room, property, user, movie, cinema);
        }
        @Test
        void itShouldRedirectOnHomePageIfOperationParameterIsUpdate() throws IOException {
            when(request.getParameter("operation_post")).thenReturn("update");
            when(request.getParameter("screening_id")).thenReturn(String.valueOf(screeningId));
            servlet.doPost(request,response);
            verify(screeningDAO, times(1)).updateScreening(screeningId, time, room, property, user, movie, cinema);
        }

        @AfterEach
        public void tearDown() throws IOException {
            verify(response, times(1)).sendRedirect("./home");
        }
    }
}