package mate.academy.internetshop.controller.bucketcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class AddItemToBucketController extends HttpServlet {
    private static Logger LOGGER = Logger.getLogger(AddItemToBucketController.class);

    @Inject
    private static BucketService bucketService;
    @Inject
    private static ItemService itemService;
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        String itemId = req.getParameter("item_id");

        try {
            Item item = itemService.get(Long.valueOf(itemId));
            User user = userService.get(userId);
            Bucket bucket = bucketService.getByUser(user);
            bucketService.addItem(bucket, item);
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("err_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
        resp.sendRedirect("/user/allItems");
    }
}
