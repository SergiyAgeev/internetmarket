package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.model.User;

public interface UserDao {

    User create(User user) throws DataProcessingException;

    Optional<User> get(Long id) throws DataProcessingException;

    User update(User user) throws DataProcessingException;

    boolean deleteById(Long id) throws DataProcessingException;

    boolean delete(User user) throws DataProcessingException;

    List<User> getAllUsers() throws DataProcessingException;

    Optional<User> findByLogin(String login) throws DataProcessingException;

    Optional<User> getByToken(String token) throws DataProcessingException;
}
