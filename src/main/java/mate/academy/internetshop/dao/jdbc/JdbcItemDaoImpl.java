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
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Dao
public class JdbcItemDaoImpl extends AbstractDao<Item> implements ItemDao {
    private static final Logger LOGGER = LogManager.getLogger(JdbcItemDaoImpl.class);

    public JdbcItemDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) throws DataProcessingException {
        String query = "INSERT INTO items (item_name, item_price) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                Long itemId = rs.getLong(1);
                item.setId(itemId);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create new item with id  = " + item.getId(), e);
        }
        return item;
    }

    @Override
    public Optional<Item> get(Long id) throws DataProcessingException {
        String query = "SELECT * FROM items WHERE item_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long itemId = rs.getLong("item_id");
                String name = rs.getString("item_name");
                Double price = rs.getDouble("item_price");
                Item item = new Item(name, price);
                item.setId(itemId);
                return Optional.of(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get item with id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item item) throws DataProcessingException {
        String query = "UPDATE items SET item_name=?, item_price=? WHERE item_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.setLong(3, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update item with id = " + item.getId(), e);
        }
        return item;
    }

    @Override
    public boolean deleteById(Long id) throws DataProcessingException {
        String query = "DELETE FROM items WHERE item_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete item with id = " + id, e);
        }
        return true;
    }

    @Override
    public boolean delete(Item item) throws DataProcessingException {
        return deleteById(item.getId());
    }

    @Override
    public List<Item> getAllItems() throws DataProcessingException {
        String query = "SELECT * FROM items;";
        List<Item> items = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query);) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("item_id");
                String name = resultSet.getString("item_name");
                double price = resultSet.getDouble("item_price");
                Item item = new Item(name, price);
                item.setId(id);
                items.add(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error, while trying to get all items list", e);
        }
        return items;
    }
}
