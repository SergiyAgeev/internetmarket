package mate.academy.internetshop.controller.ordercontroller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.OrderService;

public class CompleteOrderController extends HttpServlet {
    @Inject
    private static BucketService bucketService;
    @Inject
    private static OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        Order order = new Order(userId);
        try {
            order.setItems(bucketService.getAllItems(bucketService.getByUserId(userId)));
        } catch (DataProcessingException e) {
            e.printStackTrace();
        }
        List<Item> items = order.getItems();
        try {
            orderService.completeOrder(items, userId);
        } catch (DataProcessingException e) {
            e.printStackTrace();
        }
        req.setAttribute("items", items);
        req.getRequestDispatcher("/WEB-INF/views/order.jsp").forward(req, resp);
    }
}
