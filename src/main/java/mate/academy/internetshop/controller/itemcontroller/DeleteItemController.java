package mate.academy.internetshop.controller.itemcontroller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.service.ItemService;
import org.apache.log4j.Logger;

public class DeleteItemController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AddNewItemController.class);
    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String itemId = req.getParameter("item_id");
        try {
            itemService.deleteById(Long.valueOf(itemId));
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("err_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
        resp.sendRedirect("/admin/allitems");
    }
}
