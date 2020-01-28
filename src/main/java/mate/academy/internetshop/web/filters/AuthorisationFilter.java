package mate.academy.internetshop.web.filters;

import static mate.academy.internetshop.model.Role.RoleName.ADMIN;
import static mate.academy.internetshop.model.Role.RoleName.USER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class AuthorisationFilter implements Filter {
    private static Logger LOGGER = Logger.getLogger(AuthorisationFilter.class);

    @Inject
    private static UserService userService;

    private Map<String, Role.RoleName> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/admin/showallusers", ADMIN);
        protectedUrls.put("/admin/addnewitem", ADMIN);
        protectedUrls.put("/admin/deleteItem", ADMIN);
        protectedUrls.put("/admin/deleteUser", ADMIN);
        protectedUrls.put("/admin/allItems", ADMIN);
        protectedUrls.put("/admin/adminpage", ADMIN);

        protectedUrls.put("/user/addItemToBucket", USER);
        protectedUrls.put("/user/deleteUserOrder", USER);
        protectedUrls.put("/user/bucket", USER);
        protectedUrls.put("/user/orders", USER);
        protectedUrls.put("/user/completeOrder", USER);
        protectedUrls.put("/user/deleteFromBucket", USER);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String requestedUrl = req.getRequestURI();
        Role.RoleName roleName = protectedUrls.get(requestedUrl);

        if (roleName == null) {
            processAuthorized(req, resp, chain);
            return;
        }

        Long userId = (Long) req.getSession().getAttribute("userId");
        User user = null;

        try {
            user = userService.get(userId);
        } catch (DataProcessingException e) {
            LOGGER.error("err_msg",e);
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }

        if (verifyRole(user, roleName)) {
            processAuthorized(req, resp, chain);
        } else {
            processDenied(req, resp);
        }
    }

    @Override
    public void destroy() {

    }

    private void processDenied(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/accesDenied.jsp").forward(req, resp);
    }

    private boolean verifyRole(User user, Role.RoleName roleName) {
        return user.getRoles()
                .stream()
                .anyMatch(role -> role.getRoleName().equals(roleName));
    }

    private void processAuthorized(HttpServletRequest req, HttpServletResponse resp,
                                   FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(req, resp);
    }

    private void processUnAuthenticated(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.sendRedirect("/login");
    }

    private void processAuthenticated(
            FilterChain chain, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        chain.doFilter(req, resp);
    }
}
