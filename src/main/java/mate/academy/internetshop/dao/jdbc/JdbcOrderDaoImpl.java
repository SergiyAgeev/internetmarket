package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
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
    public Order create(Order order) throws DataProcessingException {
        String query = "INSERT INTO orders (user_id) VALUES (?);";

        try (PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                Long orderId = rs.getLong(1);
                order.setId(orderId);
            }
            addItems(order, order.getItems());
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create order with id = " + order.getId(), e);
        }

        return order;
    }


    @Override
    public Order get(Long id) throws DataProcessingException {
        String query = "SELECT * FROM orders WHERE order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long orderId = rs.getLong("order_id");
                Long userId = rs.getLong("user_id");
                Order order = new Order(userId);
                order.setId(orderId);
                order.setItems(getItemsFromOrder(order.getId()));
                return Optional.of(order).get();
            }
        } catch (SQLException | DataProcessingException e) {
            throw new DataProcessingException("Can`t find order with id = " + id, e);
        }
        return null;
    }

    @Override
    public Order update(Order order) throws DataProcessingException {
        String query = "UPDATE orders SET user_id = ? WHERE order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getUserId());
            statement.setLong(2, order.getId());
            statement.executeUpdate();

            List<Item> items = getItemsFromOrder(order.getId());
            List<Item> newItems = order.getItems();
            List<Item> preparedForDelete = new ArrayList<>(items);

            preparedForDelete.removeAll(newItems);
            deleteItems(order, preparedForDelete);
            List<Item> itemsToAdd = new ArrayList<>(newItems);
            itemsToAdd.removeAll(items);
            addItems(order, itemsToAdd);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update order with id = " + order.getId(), e);
        }
        return order;
    }

    @Override
    public boolean deleteById(Long id) throws DataProcessingException {
        return delete(get(id));
    }

    @Override
    public boolean delete(Order order) throws DataProcessingException {
        String query = String.format(
                "delete from %s where order_id =?", "orders");
        deleteItems(order, order.getItems());
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete order with id = " + order.getId(), e);
        }
        return true;
    }

    @Override
    public List<Order> getAll() throws DataProcessingException {
        List<Order> orders = new ArrayList<>();
        String query = String.format("select order_id from %s;", "orders");
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Order order = get(rs.getLong("order_id"));
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all Orders", e);
        }
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

    private void addItems(Order order, List<Item> items) throws DataProcessingException {
        String query = "INSERT INTO order_items (order_id, item_id) VALUES (?, ?);";
        addToOrder(order, items, query);
    }

    private void deleteItems(Order order, List<Item> items) throws DataProcessingException {
        String query = "DELETE FROM order_items WHERE order_id = ? AND item_id = ?;";
        addToOrder(order, items, query);
    }

    private void addToOrder(Order order, List<Item> items, String query)
            throws DataProcessingException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Item item : items) {
                statement.setLong(1, order.getId());
                statement.setLong(2, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add item to order with id = " + order.getId(), e);
        }
    }

//    private void addItemToOrder(Long orderId, Long itemId) {
//        String insertOrderItemQuery = "INSERT INTO orders_items "
//                + "(orders_id, item_id) VALUES (?, ?);";
//        try (PreparedStatement statement = connection.prepareStatement(insertOrderItemQuery)) {
//            statement.setLong(1, orderId);
//            statement.setLong(2, itemId);
//            statement.execute();
//        } catch (SQLException e) {
//            LOGGER.warn("Can't add item to order with id = " + orderId, e);
//        }
//    }

    private List<Item> getItemsFromOrder(Long orderId) throws DataProcessingException {
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
