package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;

@Dao
public class JdbcOrderDaoImpl extends AbstractDao<Order> implements OrderDao {
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
    public Optional<Order> get(Long id) throws DataProcessingException {
        String query = "SELECT * FROM orders WHERE order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long orderId = rs.getLong("order_id");
                Long userId = rs.getLong("user_id");
                Order order = new Order(userId);
                order.setId(orderId);
                order.setItems(getItems(order));
                return Optional.of(order);
            }
        } catch (SQLException | DataProcessingException e) {
            throw new DataProcessingException("Can`t find order with id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Order update(Order order) throws DataProcessingException {
        String query = "UPDATE orders SET user_id = ? WHERE order_id= ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getUserId());
            statement.setLong(2, order.getId());
            statement.executeUpdate();

            List<Item> items = getItems(order);
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
        Order order = get(id).orElseThrow(NoSuchElementException::new);
        return delete(order);
    }

    @Override
    public boolean delete(Order order) throws DataProcessingException {
        String query = String.format(
                "delete from %s where order_id =?", "orders");
        deleteItems(order, order.getItems());
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete order with id = " + order.getId(), e);
        }
    }

    @Override
    public List<Order> getAll() throws DataProcessingException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long bucketId = rs.getLong("order_id");
                Long userId = rs.getLong("user_id");
                Order order = new Order(userId);
                order.setId(bucketId);
                order.setItems(getItems(order));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all Orders", e);
        }
        return orders;
    }

    private void addItems(Order order, List<Item> items) throws DataProcessingException {
        String query = "INSERT INTO orders_items (orders_id, item_id) VALUES (?, ?);";
        addToOrder(order, items, query);
    }

    private void deleteItems(Order order, List<Item> items) throws DataProcessingException {
        String query = "DELETE FROM orders_items WHERE orders_id = ? AND item_id = ?;";
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
            throw new DataProcessingException("Can't add item to order = " + order.toString(), e);
        }
    }

    private List<Item> getItems(Order order) throws DataProcessingException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT i.item_id, item_name, item_price FROM items i JOIN orders_items oi"
                + " ON i.item_id = oi.item_id AND orders_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long itemId = rs.getLong("i.item_id");
                String itemName = rs.getString("item_name");
                Double itemPrice = rs.getDouble("item_price");
                Item item = new Item(itemName, itemPrice);
                item.setId(itemId);
                items.add(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get items from order: " + e);
        }
        return items;
    }
}

