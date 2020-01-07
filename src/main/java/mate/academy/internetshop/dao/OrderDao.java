package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public interface OrderDao {

    Order create(Order order);

    Optional<Order> get(Long id);

    Order update(Order order);

    boolean deleteById(Long id);

    boolean delete(Order order);

    List<Order> getUserOrders(User user);

    Order completeOrder(List<Item> items, User user);
}
