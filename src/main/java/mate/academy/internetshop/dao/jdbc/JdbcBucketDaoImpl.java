package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Dao
public class JdbcBucketDaoImpl extends AbstractDao<Bucket> implements BucketDao {
    private static final Logger LOGGER = LogManager.getLogger(JdbcBucketDaoImpl.class);

    public JdbcBucketDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Bucket create(Bucket bucket) {
        String query = "INSERT INTO bucket (user_id) VALUES (?);";
        try (PreparedStatement statement = connection.prepareStatement(query,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, bucket.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            long bucketId = 0L;
            while (resultSet.next()) {
                bucketId = resultSet.getLong("bucket_id");
            }
            for (Item item : bucket.getItems()) {
                addItem(bucketId, item.getId());
            }
            bucket = new Bucket(bucket.getItems(), bucket.getUserId());
        } catch (SQLException e) {
            LOGGER.error("Can't add new bucket with id = " + bucket.getId(), e);
        }
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long id) {
        String query = "SELECT user_id FROM bucket WHERE id=?;";
        long userId = 0L;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userId = resultSet.getLong("user_id");
            }
            List<Item> items = get(id).get().getItems();
            return Optional.of(new Bucket(items, userId));
        } catch (SQLException e) {
            LOGGER.error("Can't get user id by bucket id=" + id, e);
            return Optional.empty();
        }
    }

    @Override
    public Bucket update(Bucket bucket) {
        String query = "UPDATE bucket SET user_id = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, bucket.getUserId());
            preparedStatement.setLong(2, bucket.getId());
            preparedStatement.executeUpdate();
            for (Item item : bucket.getItems()) {
                addItem(bucket.getId(), item.getId());
            }
        } catch (SQLException e) {
            LOGGER.error("Can`t update bucket with id = " + bucket.getId(), e);
        }
        return bucket;
    }

    @Override
    public boolean deleteById(Long id) {
        deleteItems(id);
        String query = String.format("DELETE FROM buckets WHERE id=?;", id);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't delete bucket with id = " + id, e);
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Bucket bucket) {
        deleteById(bucket.getId());
        return true;
    }

    @Override
    public List<Bucket> getAllBuckets() {
        List<Bucket> buckets = new ArrayList<>();
        String query = String.format("SELECT bucket_id, user_id FROM %s",
                "bucket");
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Long bucketId = resultSet.getLong(1);
                Long userId = resultSet.getLong(2);
                Bucket bucket = new Bucket(userId);
                bucket.setId(bucketId);
                buckets.add(bucket);
            }
        } catch (SQLException e) {
            LOGGER.warn("Can`t get all buckets",e);
        }
        List<Bucket> newBuckets = new ArrayList<>();
        for (Bucket bucket : buckets) {
            buckets.add(get(bucket.getId()).get());
        }
        return newBuckets;
    }

    @Override
    public Bucket getByUserId(Long userId) {
        long bucketId = 0L;
        String query = "SELECT id FROM bucket WHERE user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();
            bucketId = rs.getLong(1);
        } catch (SQLException e) {
            LOGGER.warn("Can`t get bucket with user id = " + userId, e);
        }
        return get(bucketId).get();
    }

    private void addItem(Long bucketId, Long itemId) {
        String query = "INSERT INTO bucket (id, item_id) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucketId);
            statement.setLong(2, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("Can't add item to bucket with id = " + bucketId, e);
        }
    }

    private void deleteItems(Long id) {
        String query = "DELETE FROM bucket WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't delete items from bucket with id = " + id, e);
        }
    }
}
