package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

@Dao
public class JdbcUserDaoImpl extends AbstractDao<User> implements UserDao {
    @Inject
    private static OrderDao orderDao;
    @Inject
    private static ItemDao itemDao;
    @Inject
    private static BucketDao bucketDao;

    private static Logger LOGGER = Logger.getLogger(JdbcUserDaoImpl.class);

    public JdbcUserDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) {
        String query = String.format("INSERT INTO users " +
                "(user_name, user_second_name , user_login," +
                " user_password, user_token, user_age)" +
                " VALUES (?, ?, ?, ?, ?, ?);");
        long userId = 0L;
        try (PreparedStatement statement = connection.prepareStatement(query,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSecondName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getToken());
            statement.setInt(6, user.getAge());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            userId = resultSet.getLong(1);

        } catch (SQLException e) {
            LOGGER.warn("Can't add new user with id=" + user.getId(), e);
        }
//        User newUser = new User(user.getId(), user);
//        return newUser;
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE user_id=?;";
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long userId = resultSet.getLong("user_id");
                String name = resultSet.getString("user_name");
                String surname = resultSet.getString("user_second_name");
                String login = resultSet.getString("user_login");
                String password = resultSet.getString("user_password");
                String token = resultSet.getString("user_token");
                Integer age = resultSet.getInt("user_age");

                user = new User(name, surname, login, password, age);
                user.setId(userId);
                user.setToken(token);

            }

        } catch (SQLException e) {
            LOGGER.error("Can't get user by id=" + id, e);
        }
        return Optional.of(user);
    }


    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT user_id FROM users WHERE user_login=?;";
        Optional<User> user = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long userId = resultSet.getLong("user_id");
                user = get(userId);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get user by login=" + login, e);
        }
        return user;
    }

    @Override
    public Optional<User> getByToken(String token) {
        return Optional.empty();
    }
}
