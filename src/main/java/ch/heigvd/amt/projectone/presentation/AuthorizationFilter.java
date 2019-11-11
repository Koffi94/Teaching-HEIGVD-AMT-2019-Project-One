package ch.heigvd.amt.projectone.presentation;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This Filter class is used to manage authorizations on the app
 */
public class AuthorizationFilter implements Filter {

    /**
     * This method is called for each request or response on the server
     *
     * @param req The request object
     * @param resp The response object
     * @param chain The object to chain Filters if there is
     * @throws ServletException
     * @throws IOException
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        String path = request.getRequestURI().substring(request.getContextPath().length());

        // We do a blacklist because it's easier to manage with the bootstrap template dependencies
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

    public void init(FilterConfig config) {
    }

    public void destroy() {
    }
}
