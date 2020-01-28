package mate.academy.internetshop.web.filters;

import java.io.IOException;
import java.util.NoSuchElementException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class AuthenticationFilter implements Filter {
    private static Logger LOGGER = LogManager.getLogger(AuthenticationFilter.class);
    @Inject
    private static UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);

        if (session == null) {
            processUnAuthenticated(req, resp);
            return;
        }

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            processUnAuthenticated(req, resp);
            return;
        }
        try {
            userService.get(userId);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (NoSuchElementException e) {
            LOGGER.error("There is no session with this user" + e);
            resp.sendRedirect( "/logout");
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
    }

    private void processUnAuthenticated(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.sendRedirect("/login");
    }

    @Override
    public void destroy() {

    }
}
