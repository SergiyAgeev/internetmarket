package mate.academy.internetshop.controller.admincontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.ItemService;
import org.apache.log4j.Logger;

public class AdminGetAllItemsController extends HttpServlet {
    private static Logger LOGGER = Logger.getLogger(AdminGetAllUsersController.class);

    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Item> items = new ArrayList();
        try {
            items.addAll(itemService.getAllItems());
        } catch (DataProcessingException e) {
            LOGGER.error("Error", e);
            req.setAttribute("err_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
        req.setAttribute("allItems", items);
        req.getRequestDispatcher("/WEB-INF/views/adminAllItems.jsp").forward(req, resp);
    }
}
