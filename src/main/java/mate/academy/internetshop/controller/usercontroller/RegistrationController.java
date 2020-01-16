package mate.academy.internetshop.controller.usercontroller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class RegistrationController extends HttpServlet {
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
        User createdUser = userService.create(user);
        HttpSession session = req.getSession();
        session.setAttribute("userId", createdUser.getId());
        Cookie cookie = new Cookie("MATE", createdUser.getToken());
        resp.addCookie(cookie);
        resp.sendRedirect("/Login");
    }
}
