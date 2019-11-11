package ch.heigvd.amt.projectone.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import static org.mockito.Mockito.*;

/**
 * This class is used to test the LogoutServlet methods
 */
@ExtendWith(MockitoExtension.class)
public class LogoutServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    LogoutServlet servlet;

    @BeforeEach
    public void setUp() {
        servlet = new LogoutServlet();
    }

    @Test
    public void itShouldRedirectOnLoginPage() throws IOException {
        when(request.getSession(false)).thenReturn(session);
        servlet.doGet(request, response);
        verify(response, times(1)).sendRedirect("./login");
    }
}