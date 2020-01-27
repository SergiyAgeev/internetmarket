package mate.academy.internetshop.dao;

import java.util.List;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.model.Order;

public interface OrderDao {

    Order create(Order order) throws DataProcessingException;

    Order get(Long id) throws DataProcessingException;

    Order update(Order order) throws DataProcessingException;

    boolean deleteById(Long id) throws DataProcessingException;

    boolean delete(Order order) throws DataProcessingException;

    List<Order> getAll() throws DataProcessingException;

    List<Order> getUserOrders(Long userId) throws DataProcessingException;
}
