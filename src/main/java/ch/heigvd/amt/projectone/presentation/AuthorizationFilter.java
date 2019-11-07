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

        // We do a blacklist because it's easier to manage with the bootstrap template
        boolean isTargetUrlProtected = false;

        // Explicit paths that user cannot access without being authenticated
        if(path.startsWith("/home") || path.startsWith("/manage")) {
            isTargetUrlProtected = true;
        }

        if(session.getAttribute("authenticated") == null && isTargetUrlProtected) {
            request.getRequestDispatcher("./WEB-INF/pages/login.jsp").forward(req, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}
