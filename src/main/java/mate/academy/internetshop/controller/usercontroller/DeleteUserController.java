package mate.academy.internetshop.controller.usercontroller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.service.UserService;

public class DeleteUserController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        try {
            userService.deleteById(Long.valueOf(userId));
        } catch (DataProcessingException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/admin/showallusers");
    }
}
