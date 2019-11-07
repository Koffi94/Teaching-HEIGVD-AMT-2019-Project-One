package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.services.dao.CinemaManagerLocal;
import ch.heigvd.amt.projectone.services.dao.UserManagerLocal;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.RequestWrapper;
import java.io.IOException;

public class AuthorizationFilter implements Filter {

    @EJB
    UserManagerLocal userManager;


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        String path = request.getRequestURI().substring(request.getContextPath().length());
        boolean isTargetUrlProtected = false;

        // login page can be accessed without being authenticated

        if(session.getAttribute("authenticated") == null && isTargetUrlProtected) {
            chain.doFilter(req, resp);
            //request.getRequestDispatcher("./WEB-INF/pages/login.jsp").forward(req, resp);
        } else {
            session.setMaxInactiveInterval(60*5);
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}
