package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;

public interface OrderService {

    Order create(Order order);

    Order get(Long id);

    Order update(Order order);

    boolean deleteById(Long id);

    boolean delete(Order order);

    Order completeOrder(List<Item> items, Long userId);

    List<Order> getUserOrders(Long userId);

    List<Order> getAll();
}
