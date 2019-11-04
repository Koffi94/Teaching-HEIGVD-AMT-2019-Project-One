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

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        if(userManager.checkUser((String) session.getAttribute("username"), (String) session.getAttribute("password"))) {
            // Setting session to expiry in 5 mins
            session.setMaxInactiveInterval(60*5);

            session.setAttribute("session", "ok");
        } else {
            session.setAttribute("session", "nok");
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
