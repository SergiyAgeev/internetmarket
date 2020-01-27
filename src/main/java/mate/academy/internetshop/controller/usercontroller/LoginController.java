package mate.academy.internetshop.controller.usercontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mate.academy.internetshop.controller.admincontroller.AdminGetAllUsersController;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class LoginController extends HttpServlet {
    private static Logger LOGGER = Logger.getLogger(LoginController.class);
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            User user = userService.login(login, password);
            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getId());
            resp.sendRedirect("/login");
        } catch (AuthenticationException e) {
            req.setAttribute("errorMsg", "The incorrect login or password");
            req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req, resp);
        } catch (DataProcessingException e) {
            LOGGER.error("error", e);
            req.setAttribute("err_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
    }
}
