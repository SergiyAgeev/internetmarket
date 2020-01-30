package mate.academy.internetshop.controller.indexcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class InjectDataController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(InjectDataController.class);

    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User();
        user.setName("user");
        user.setSecondName("user");
        user.addRole(Role.of("USER"));
        user.setLogin("user");
        user.setPassword("user");
        user.setAge(403);
        try {
            userService.create(user);
        } catch (DataProcessingException e) {
            LOGGER.error("Error", e);
            req.setAttribute("err_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }

        User admin = new User();
        admin.setName("admin");
        admin.setSecondName("admin");
        admin.addRole(Role.of("ADMIN"));
        admin.setLogin("admin");
        admin.setPassword("admin");
        user.setAge(404);
        try {
            userService.create(admin);
        } catch (DataProcessingException e) {
            LOGGER.error("Error", e);
            req.setAttribute("err_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
        resp.sendRedirect("/index");
    }
}
