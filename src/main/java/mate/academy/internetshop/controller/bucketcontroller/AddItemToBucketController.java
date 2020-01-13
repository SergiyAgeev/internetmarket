package mate.academy.internetshop.controller.bucketcontroller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.UserService;

public class AddItemToBucketController extends HttpServlet {
    private static final long USER_ID = 1L;
    @Inject
    private static BucketService bucketService;
    @Inject
    private static ItemService itemService;
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String itemId = req.getParameter("item_id");
        Item item = itemService.get(Long.valueOf(itemId));
        Bucket bucket = bucketService.getAllBuckets()
                .stream()
                .filter(b -> b.getUserId().equals(USER_ID))
                .findFirst()
                .orElse(bucketService.create(new Bucket(USER_ID)));
        bucketService.addItem(bucket, item);
        resp.sendRedirect("/allItems");
    }
}
