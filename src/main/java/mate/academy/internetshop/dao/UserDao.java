package mate.academy.internetshop.dao;

import java.util.Optional;

import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.model.User;

public interface UserDao extends GenericDao<User, Long> {

    Optional<User> findByLogin(String login) throws DataProcessingException;

    User login(String login, String password)
            throws AuthenticationException, DataProcessingException;
}
