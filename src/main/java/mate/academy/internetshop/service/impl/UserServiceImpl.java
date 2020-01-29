package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.util.HashUtil;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) throws DataProcessingException {
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(user.getPassword(),  user.getSalt()));
        user.setToken(getToken());
        return userDao.create(user);
    }

    private String getToken() throws DataProcessingException {
        return UUID.randomUUID().toString();
    }

    @Override
    public User get(Long id) throws DataProcessingException {
        return userDao.get(id).orElseThrow(() ->
                new NoSuchElementException("Can't find user with id = " + id));
    }

    @Override
    public User update(User user) throws DataProcessingException {
        String hashPassword = HashUtil.hashPassword(user.getPassword(), user.getSalt());
        user.setPassword(hashPassword);
        return userDao.update(user);
    }

    @Override
    public boolean deleteById(Long id) throws DataProcessingException {
        return userDao.deleteById(id);
    }

    @Override
    public boolean delete(User user) throws DataProcessingException {
        return userDao.delete(user);
    }

    @Override
    public List<User> getAllUsers() throws DataProcessingException {
        return userDao.getAllUsers();
    }

    @Override
    public User login(String login, String password)
            throws AuthenticationException, DataProcessingException {
        Optional<User> user = userDao.findByLogin(login);
        if (user.isEmpty() || !user.get()
                .getPassword()
                .equals(HashUtil.hashPassword(password, user.get().getSalt()))) {
            throw new AuthenticationException("wrong username or password");
        }
        return user.get();
    }

    @Override
    public Optional<User> getByToken(String token) throws DataProcessingException {
        return userDao.getByToken(token);
    }
}
