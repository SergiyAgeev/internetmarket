package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.model.User;

public interface UserDao {

    User create(User user);

    Optional<User> get(Long id);

    User update(User user);

    boolean deleteById(Long id);

    boolean delete(User user);

    List<User> getAllUsers();

    User login(String login, String password) throws AuthenticationException;

    Optional<User> getByToken(String token);
}
