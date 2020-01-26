package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
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
        String query = "INSERT INTO orders (user_id) VALUES (?);";
        Long orderId = 0L;
        try (PreparedStatement statement = connection.prepareStatement(query,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            orderId = rs.getLong(1);
        } catch (SQLException e) {
            LOGGER.warn("Can't create new order", e);
        }
        for (Item item : order.getItems()) {
            addItemToOrder(orderId, item.getId());
        }
        return new Order(orderId, order.getUserId(), order.getItems());
    }

    @Override
    public Order get(Long id) {
        String query = "SELECT user_id FROM orders WHERE order_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            long userId = 0L;
            while (resultSet.next()) {
                userId = resultSet.getLong("user_id");
            }
            List<Item> items = getItemsFromOrder(id);
            User user = userDao.get(userId).get();
            return new Order(id, user.getId(), items);
        } catch (SQLException e) {
            LOGGER.warn("Can't get order with id = " + id, e);
            return null;
        }
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

    private void addItemToOrder(Long orderId, Long itemId) {
        String insertOrderItemQuery = "INSERT INTO orders_items "
                + "(orders_id, item_id) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertOrderItemQuery)) {
            statement.setLong(1, orderId);
            statement.setLong(2, itemId);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.warn("Can't add item to order with id = " + orderId, e);
        }
    }

    private List<Item> getItemsFromOrder(Long orderId) {
        String query = "SELECT item_id FROM orders_items WHERE orders_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                items.add(itemDao.get(resultSet.getLong("item_id")).get());
            }
            return items;
        } catch (SQLException e) {
            LOGGER.warn("Can't get items from order with id = " + orderId, e);
        }
        return Collections.emptyList();
    }
}
