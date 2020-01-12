package mate.academy.internetshop.controller.ordercontroller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class GetOrderByUserIdController extends HttpServlet {
    public static final long USER_ID = 1L;
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Order> orders = orderService.getAll().stream()
                .filter(o -> o.getUserId().equals(USER_ID))
                .collect(Collectors.toList());

        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WEB-INF/views/orderListbyUser.jsp").forward(req, resp);
    }

}

