package mate.academy.internetshop.controller.ordercontroller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class CompleteOrderController extends HttpServlet {
    private static Logger LOGGER = Logger.getLogger(CompleteOrderController.class);

    @Inject
    private static BucketService bucketService;
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        try {
            User user = userService.get(userId);
            Bucket bucket = bucketService.getByUser(user);
            orderService.completeOrder(bucket.getItems(), user);
            bucketService.clear(bucket);
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("err_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/");
//        req.getRequestDispatcher("/WEB-INF/views/order.jsp").forward(req, resp);
    }
}
