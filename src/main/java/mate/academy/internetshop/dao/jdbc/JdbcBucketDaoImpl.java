package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

@Dao
public class JdbcBucketDaoImpl extends AbstractDao<Bucket> implements BucketDao {

    public JdbcBucketDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Bucket create(Bucket bucket) throws DataProcessingException {
        String query = "INSERT INTO bucket (user_id) VALUE (?);";
        try (PreparedStatement statement = connection.prepareStatement(
                query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, bucket.getUserId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                Long bucketId = rs.getLong(1);
                bucket.setId(bucketId);
            }
            addItemsToBucket(bucket, bucket.getItems());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create bucket " + e);
        }
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long id) throws DataProcessingException {
        String query = "SELECT * FROM bucket WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long bucketId = rs.getLong("id");
                Long userId = rs.getLong("user_id");
                Bucket bucket = new Bucket(userId);
                bucket.setId(bucketId);
                bucket.addItemsToBucket(getItems(bucket));
                return Optional.of(bucket);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get bucket with id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Bucket update(Bucket bucket) throws DataProcessingException {
        String query = "UPDATE bucket SET user_id = ? WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucket.getUserId());
            statement.setLong(2, bucket.getId());
            statement.executeUpdate();

            List<Item> oldItems = getItems(bucket);
            List<Item> newItems = bucket.getItems();

            List<Item> itemsToDelete = new ArrayList<>(oldItems);
            itemsToDelete.removeAll(newItems);
            deleteItemsFromBucket(bucket, itemsToDelete);

            List<Item> itemsToAdd = new ArrayList<>(newItems);
            itemsToAdd.removeAll(oldItems);
            addItemsToBucket(bucket, itemsToAdd);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update bucket with id = " + bucket.getId(), e);
        }
        return bucket;
    }

    @Override
    public boolean deleteById(Long id) throws DataProcessingException {
        Bucket bucket = get(id).orElseThrow(NoSuchElementException::new);
        return delete(bucket);
    }

    @Override
    public boolean delete(Bucket bucket) throws DataProcessingException {
        deleteItemsFromBucket(bucket, bucket.getItems());
        String query = "DELETE FROM bucket WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucket.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete bucket with id =  " + bucket.getId(), e);
        }
    }

    @Override
    public List<Bucket> getAllBuckets() throws DataProcessingException {
        List<Bucket> buckets = new ArrayList<>();
        String query = "SELECT * FROM bucket;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long bucketId = rs.getLong("id");
                Long userId = rs.getLong("user_id");
                Bucket bucket = new Bucket(userId);
                bucket.setId(bucketId);
                bucket.addItemsToBucket(getItems(bucket));
                buckets.add(bucket);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all buckets" + e);
        }
        return buckets;
    }

    private void addItemsToBucket(Bucket bucket, List<Item> items) throws DataProcessingException {
        String query = "INSERT INTO bucket_items (bucket_id, item_id) VALUES (?, ?);";
        updateItemsInBucket(bucket, items, query);
    }

    private void deleteItemsFromBucket(Bucket bucket, List<Item> items) throws DataProcessingException {
        String query = "DELETE FROM bucket_items WHERE bucket_id = ? AND item_id = ?;";
        updateItemsInBucket(bucket, items, query);
    }

    private void updateItemsInBucket(Bucket bucket, List<Item> items, String query)
            throws DataProcessingException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Item item : items) {
                statement.setLong(1, bucket.getId());
                statement.setLong(2, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update items in bucket with id = " + bucket.getId(), e);
        }
    }

    private List<Item> getItems(Bucket bucket) throws DataProcessingException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT i.item_id, item_name, item_price FROM items i JOIN bucket_items bi"
                + " ON i.item_id = bi.item_id AND bucket_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucket.getId());
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
            throw new DataProcessingException("Can`t get all items from bucket with id = " + bucket.getId(), e);
        }
        return items;
    }
}
