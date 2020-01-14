package mate.academy.internetshop.dao;

import java.util.List;

import mate.academy.internetshop.model.Order;

public interface OrderDao {

    Order create(Order order);

    Order get(Long id);

    Order update(Order order);

    boolean deleteById(Long id);

    boolean delete(Order order);

    List<Order> getAll();
}
