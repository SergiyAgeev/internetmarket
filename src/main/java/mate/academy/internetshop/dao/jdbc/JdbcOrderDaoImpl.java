package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import org.apache.log4j.Logger;

@Dao
public class JdbcOrderDaoImpl extends AbstractDao<Order> implements OrderDao {
    private static Logger LOGGER = Logger.getLogger(JdbcOrderDaoImpl.class);
    @Inject
    private static ItemDao itemDao;
    @Inject
    private static UserDao userDao;
    public JdbcOrderDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Order create(Order order) {
        Statement stmt = null;
        String query = "INSERT INTO 'orders' ('user_id') VALUES ('" + order.getUserId() + "');";
        Long orderId = null; //call one the methods from statement
        String insertOrderItemQuery = "INSERT INTO 'orders_items' ('order_id','item_id') " +
                "VALUES ('%s', ' + '%s')";
        for (Item item : order.getItems()) {
            try {
                stmt.execute(String.format(insertOrderItemQuery, orderId, item.getId()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new Order(orderId, order.getUserId(), order.getItems());
    }

    @Override
    public Order get(Long id) {
        return null;
    }

    @Override
    public Order update(Order order) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean delete(Order order) {
        return false;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        String getOrdersQuery = "SELECT orders.order_id, orders.user_id, items.name, items.price"
                + "FROM orders" +
                "INNER JOIN orders_items" +
                "ON orders.order_id = orders_items.order_id" +
                "INNER JOIN items" +
                "ON orders_items.item_id = items.item_id" +
                "WHERE orders.user_id = 1" +
                "ORDER BY orders.order_id;";
        String getUserQuery = "SELECT * FROM users WHERE users.user_id = <SOME_VALUE>";
        //сформувати список ордерів і повернути його
        return null;
    }
}
