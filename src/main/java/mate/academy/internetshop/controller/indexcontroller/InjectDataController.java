package mate.academy.internetshop.controller.indexcontroller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class InjectDataController extends HttpServlet {
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
        user.setPassword("1");
        userService.create(user);

        User admin = new User();
        admin.setName("admin");
        admin.setSecondName("admin");
        admin.addRole(Role.of("ADMIN"));
        admin.setLogin("admin");
        admin.setPassword("1");
        userService.create(admin);
        resp.sendRedirect("/index");
    }
}
