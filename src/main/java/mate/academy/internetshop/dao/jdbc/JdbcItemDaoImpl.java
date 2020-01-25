package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Dao
public class JdbcItemDaoImpl extends AbstractDao<Item> implements ItemDao {
    private static final Logger LOGGER = LogManager.getLogger(JdbcItemDaoImpl.class);
    private static String DB_NAME = "internet_market";

    public JdbcItemDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) {
        String query = "INSERT INTO items (item_name, item_price) VALUE (?,?);";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setDouble(2, item.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("Can`t create item with id  = " + item.getId(), e);
            return null;
        }
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        String query = String.format("SELECT * FROM %s WHERE item_id=%d", DB_NAME, id);

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String itemName = rs.getString("item_name");
                double itemPrice = rs.getDouble("item_price");
                long itemId = rs.getLong("item_id");
                Item item = new Item();
                item.setName(itemName);
                item.setPrice(itemPrice);
                item.setId(itemId);
                return Optional.of(item);
            }
        } catch (SQLException e) {
            LOGGER.warn("Can't get item with id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item item) {
        String query = String.format("UPDATE items"
                        + " SET item_name='%s', item_price=%f"
                        + " WHERE item_id=%d",
                item.getName(), item.getPrice(), item.getId());
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            LOGGER.warn("Can't update this item = " + item.toString(), e);
        }
        return item;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM items WHERE item_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.warn("Can't delete item with id = " + id, e);
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Item item) {
        Optional<Item> newItem = get(item.getId());
        if (newItem.isPresent()) {
            String query = (String.format("DELETE FROM items WHERE item_id=%d;", item.getId()));
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                LOGGER.warn("Can't delete item with id = " + item.getId(), e);
            }
            return true;
        }
        return false;
    }

    @Override
    public List<Item> getAllItems() {
        String query = "SELECT * FROM items;";
        List<Item> items = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query);) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("item_id");
                String name = resultSet.getString("item_name");
                double price = resultSet.getDouble("item_price");
                Item item = new Item(id, name, price);
                items.add(item);
            }
        } catch (SQLException e) {
            LOGGER.warn("Error, while trying to get all items list", e);
        }
        return items;
    }
}

