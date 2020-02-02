package mate.academy.internetshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.HashUtil;

@Dao
public class JdbcUserDaoImpl extends AbstractDao<User> implements UserDao {

    @Inject
    private static UserDao userDao;

    public JdbcUserDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) throws DataProcessingException {

        String query = "INSERT INTO users "
                + "(user_name, user_second_name , user_login,"
                + " user_password,user_salt, user_token, user_age)"
                + " VALUES (?, ?, ?, ?, ?, ?,?);";
        try (PreparedStatement statement = connection.prepareStatement(query,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSecondName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setBytes(5, user.getSalt());
            statement.setString(6, user.getToken());
            statement.setInt(7, user.getAge());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                user.setId(rs.getLong(1));
            }
            for (Role role : user.getRoles()) {
                Optional<Long> roleId = getRoleIdByRoleName(String.valueOf(role.getRoleName()));
                addRoleToUserByUserId(user.getId(), roleId.get());
            }
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new user with id = " + user.getId(), e);
        }
    }

    @Override
    public Optional<User> get(Long id) throws DataProcessingException {
        String query = "SELECT * FROM users WHERE user_id=?;";
        User user;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                long userId = rs.getLong("user_id");
                String name = rs.getString("user_name");
                String surname = rs.getString("user_second_name");
                String login = rs.getString("user_login");
                String password = rs.getString("user_password");
                int age = rs.getInt("user_age");
                user = new User(name, surname, login, password, age);
                byte[] salt = rs.getBytes("user_salt");
                String token = rs.getString("user_token");
                user.setSalt(salt);
                user.setId(userId);
                user.setToken(token);
                Set<Role> roles = getAllUsersRoles(id);
                user.setRoles(roles);
                return Optional.of(user);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't find user with id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public User update(User user) throws DataProcessingException {
        String query = "UPDATE users SET user_name = ?, user_second_name= ?,"
                + " user_login = ?, user_password = ?, user_age = ? WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSecondName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setInt(5, user.getAge());
            statement.setLong(6, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update user with id = " + user.getId(), e);
        }
        return user;
    }

    @Override
    public boolean deleteById(Long id) throws DataProcessingException {
        deleteAllUserRolesById(id);
        String query = "DELETE FROM users WHERE user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user with id = " + id, e);
        }
    }

    @Override
    public boolean delete(User user) throws DataProcessingException {
        return deleteById(user.getId());
    }

    @Override
    public List<User> getAll() throws DataProcessingException {
        String query = "SELECT user_id FROM users;";
        List<User> usersList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("user_id");
                User user = this.get(id).get();
                usersList.add(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all users", e);
        }
        return usersList;
    }

    @Override
    public Optional<User> findByLogin(String login) throws DataProcessingException {
        String query = "SELECT user_id FROM users WHERE user_login=?;";
        return getUserByParam(login, query);
    }

    @Override
    public User login(String login, String password) throws AuthenticationException,
            DataProcessingException {
        Optional<User> user = userDao.findByLogin(login);
        if (user.isEmpty() || !user.get()
                .getPassword()
                .equals(HashUtil.hashPassword(password, user.get().getSalt()))) {
            throw new AuthenticationException("wrong username or password");
        }
        return user.get();
    }

    private Optional<User> getUserByParam(String value, String query)
            throws DataProcessingException {
        Optional<User> user = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, value);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user = get(rs.getLong("user_id"));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find user with param = " + value, e);
        }
        return user;
    }

    private Set<Role> getAllUsersRoles(Long userId) throws DataProcessingException {
        Set<Role> roles = new HashSet<>();

        String query = "SELECT roles.role_name FROM users "
                + "INNER JOIN users_roles ON users.user_id = users_roles.users_id "
                + "INNER JOIN roles ON users_roles.role_id = roles.role_id "
                + "WHERE users.user_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet1 = statement.executeQuery();
            if (resultSet1.next()) {
                String roleName = resultSet1.getString("role_name");
                roles.add(Role.of(roleName));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find users roles", e);

        }
        return roles;
    }

    private void addRoleToUserByUserId(Long userId, Long roleId) throws DataProcessingException {
        String addRoleQuery = "INSERT INTO users_roles (users_id, role_id) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(addRoleQuery)) {
            statement.setLong(1, userId);
            statement.setLong(2, roleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add role for user", e);
        }
    }

    private Optional<Long> getRoleIdByRoleName(String roleName) throws DataProcessingException {
        String query = "SELECT role_id FROM roles WHERE role_name=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, roleName);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return Optional.of(rs.getLong("role_id"));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find role with name = " + roleName, e);
        }
        return Optional.empty();
    }

    private void deleteAllUserRolesById(Long userId) throws DataProcessingException {
        String deleteUserRolesQuery = "DELETE FROM users_roles WHERE users_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(deleteUserRolesQuery)) {
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user roles with id =" + userId, e);
        }
    }
}
