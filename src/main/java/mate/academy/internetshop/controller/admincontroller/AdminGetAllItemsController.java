package mate.academy.internetshop.controller.admincontroller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.ItemService;

public class AdminGetAllItemsController extends HttpServlet {
    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Item> items = null;
        items = itemService.getAllItems();
        req.setAttribute("allItems", items);
        req.getRequestDispatcher("/WEB-INF/views/adminAllItems.jsp").forward(req, resp);
    }
}