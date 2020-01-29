package mate.academy.internetshop.controller.usercontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class RegistrationController extends HttpServlet {
    private static Logger LOGGER = Logger.getLogger(RegistrationController.class);
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User();
        user.setName(req.getParameter("name"));
        user.setSecondName(req.getParameter("secondName"));
        user.setAge(Integer.parseInt(req.getParameter("age")));
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.addRole(Role.of("USER"));
        User createdUser = null;
        try {
            createdUser = userService.create(user);
            HttpSession session = req.getSession();
            session.setAttribute("userId", createdUser.getId());

        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("err_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
        resp.sendRedirect("/Login");
    }
}
