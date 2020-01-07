package mate.academy.internetshop.dao;

import java.util.Optional;

import mate.academy.internetshop.model.User;

public interface UserDao {

    User create(User user);

    Optional<User> get(Long id);

    User update(User user);

    boolean deleteById(Long id);

    boolean delete(User user);
}
