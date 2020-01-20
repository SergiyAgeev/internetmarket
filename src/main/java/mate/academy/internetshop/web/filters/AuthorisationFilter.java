package mate.academy.internetshop.web.filters;

import static mate.academy.internetshop.model.Role.RoleName.ADMIN;
import static mate.academy.internetshop.model.Role.RoleName.USER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class AuthorisationFilter implements Filter {
    @Inject
    private static UserService userService;

    private Map<String, Role.RoleName> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/admin/showallusers", ADMIN);
        protectedUrls.put("/admin/addnewitem", ADMIN);
        protectedUrls.put("/admin/deleteItem", ADMIN);
        protectedUrls.put("/admin/deelteUser", ADMIN);
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

        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            processUnAuthenticated(req, resp);
            return;
        }

        String requestedUrl = req.getServletPath();
        Role.RoleName roleName = protectedUrls.get(requestedUrl);
        if (roleName == null) {
            processAuthenticated(chain, req, resp);
            return;
        }

        String token = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("MATE")) {
                token = cookie.getValue();
                break;
            }
        }

        if (token == null) {
            processUnAuthenticated(req, resp);
        } else {
            Optional<User> user = userService.getByToken(token);
            if (user.isPresent()) {
                if (verifyRole(user.get(), roleName)) {
                    processAuthenticated(chain, req, resp);
                } else {
                    processDenied(req, resp);
                }
            } else {
                processUnAuthenticated(req, resp);
            }
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
        return user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(roleName));
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
