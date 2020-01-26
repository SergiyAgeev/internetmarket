package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    private static Logger LOGGER = Logger.getLogger(JdbcUserDaoImpl.class);
    private static String DB_NAME = "internet_market";

    public JdbcUserDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users " +
                "(user_name, user_second_name , user_login," +
                " user_password, user_token, user_age)" +
                " VALUES (?, ?, ?, ?, ?, ?);";
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
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            userId = rs.getLong(1);

        } catch (SQLException e) {
            LOGGER.warn("Can't create new user with id=" + user.getId(), e);
        }
        for (Role role : user.getRoles()) {
            Optional<Long> roleId = getRoleIdByRoleName(String.valueOf(role.getRoleName()));
            addRoleToUserByUserId(userId, roleId.get());
        }
        User newUser = new User(userId, user);
        return newUser;
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE user_id=?;";
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long userId = rs.getLong("user_id");
                String name = rs.getString("user_name");
                String surname = rs.getString("user_second_name");
                String login = rs.getString("user_login");
                String password = rs.getString("user_password");
                String token = rs.getString("user_token");
                int age = rs.getInt("user_age");
                user = new User(name, surname, login, password, age);
                user.setId(userId);
                user.setToken(token);

            }
            Set<Role> roles = getAllUsersRoles(id);
            user.setRoles(roles);
        } catch (SQLException e) {
            LOGGER.warn("Can't find user with id = " + id, e);
        }
        return Optional.of(user);
    }


    @Override
    public User update(User user) {
        String query = "UPDATE users SET user_name = ?, user_second_name= ?,"
                + " user_login = ?, user_password = ?, user_age = ? WHERE user_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSecondName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setInt(4, user.getAge());
            statement.setLong(6, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("Can`t update user with id = " + user.getId());
        }
        return user;
    }

    @Override
    public boolean deleteById(Long id) {
        deleteAllUserRolesById(id);
        String deleteUserQuery = "DELETE FROM users WHERE user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(deleteUserQuery)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("Can't delete user with id =" + id, e);
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(User user) {
        Optional<User> byLogin = findByLogin(user.getLogin());
        deleteById(byLogin.get().getId());
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        String query = "SELECT user_id FROM users;";
        List<User> usersList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("user_id");
                User user = this.get(id).get();
                usersList.add(user);
            }
            return usersList;
        } catch (SQLException e) {
            LOGGER.warn("Can't find all users", e);
            return null;
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT user_id FROM users WHERE user_login=?;";
        Optional<User> user = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long userId = rs.getLong("user_id");
                user = get(userId);
            }
        } catch (SQLException e) {
            LOGGER.warn("Can't find user with login = " + login, e);
        }
        return user;
    }

    @Override
    public Optional<User> getByToken(String token) {
        String query = "SELECT user_id FROM users WHERE user_token=?;";
        Optional<User> user = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, token);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user = get(rs.getLong("user_id"));
            }
        } catch (SQLException e) {
            LOGGER.error("Can't find user with token = " + token, e);
        }
        return user;
    }

    private Set<Role> getAllUsersRoles(Long userId) {
        String query = "SELECT role_name FROM roles "
                + "INNER JOIN users_roles "
                + "ON users_roles.role_id=roles.role_id "
                + "INNER JOIN users "
                + "ON  users_roles.users_id=users.user_id "
                + "WHERE users.user_id=?;";
        Set<Role> roles = new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                roles.add((Role.of(rs.getString("name"))));
            }
        } catch (SQLException e) {
            LOGGER.warn("Can't find users roles", e);
        }
        return roles;
    }

    private void addRoleToUserByUserId(Long userId, Long roleId) {
        String addRoleQuery = "INSERT INTO users_roles (users_id, role_id) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(addRoleQuery)) {
            statement.setLong(1, userId);
            statement.setLong(2, roleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("Can't add role for user", e);
        }
    }

    private Optional<Long> getRoleIdByRoleName(String roleName) {
        String query = "SELECT role_id FROM roles WHERE role_name=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, roleName);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                return Optional.of(rs.getLong("role_id"));
            }
        } catch (SQLException e) {
            LOGGER.warn("Can't find role with name = " + roleName, e);
        }
        return Optional.empty();
    }

    private void deleteAllUserRolesById(Long userId) {
        String deleteUserRolesQuery = "DELETE FROM users_roles WHERE users_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(deleteUserRolesQuery)) {
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("Can't delete user roles with id =" + userId, e);
        }
    }

    private void changeRolesExecute(User user, Set<Role> roles, String query) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Role role : roles) {
                statement.setLong(1, user.getId());
                statement.setString(2, role.getRoleName().toString());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.warn("Can`t change role for user = " + user.getId());
        }
    }

    private void deleteRoles(User user, Set<Role> roles) {
        String query = "DELETE FROM user_roles WHERE user_id = ? AND "
                + "role_id = (SELECT role_id FROM roles WHERE role_name = ?);";
        changeRolesExecute(user, roles, query);
    }
}
