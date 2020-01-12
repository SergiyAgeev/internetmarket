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

public class AddItemToBucketController extends HttpServlet {
    public static final long USER_ID = 1L;
    @Inject
    private static BucketService bucketService;
    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String itemId = req.getParameter("item_id");
        Item item = itemService.get(Long.valueOf(itemId));
        if (bucketService.getAllBuckets().isEmpty()) {
            Bucket bucket = new Bucket(USER_ID);
            bucketService.create(bucket);
        }
        Bucket bucket = bucketService.getByUserId(USER_ID);
        bucketService.addItem(bucket, item);
        resp.sendRedirect("/allItems");
    }
}
