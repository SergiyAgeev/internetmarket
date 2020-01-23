package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
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
        String query = String.format("INSERT INTO "
                        + "Items(item_name, item_price)"
                        + "VALUES ('%s', %s);",
                item.getName(), item.getPrice());

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            LOGGER.warn("Can't create item = " + item.toString(), e);
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
        Optional<Item> item = get(id);
        if (item.isPresent()) {
            String query = (String.format("DELETE FROM items WHERE item_id=%d;", id));
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                LOGGER.warn("Can't delete item with id = " + id, e);
            }
            return true;
        }
        return false;
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
        List<Item> itemsList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s.ITEMS", DB_NAME);
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                long itemId = rs.getLong("item_id");
                String name = rs.getString("item_name");
                double price = rs.getDouble("item_rice");
                Item item = new Item();
                item.setName(name);
                item.setPrice(price);
                item.setId(itemId);
                itemsList.add(item);
            }
            return itemsList;
        } catch (SQLException e) {
            LOGGER.warn("Can't get all items from table", e);
        }
        return itemsList;
    }
}
