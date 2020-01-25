package mate.academy.internetshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private static OrderDao orderDao;

    @Override
    public Order create(Order order) {
        return orderDao.create(order);
    }

    @Override
    public Order get(Long id) {
        return orderDao.get(id);
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public boolean deleteById(Long id) {
        return orderDao.deleteById(id);
    }

    @Override
    public boolean delete(Order order) {
        return orderDao.delete(order);
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        orderDao.getUserOrders(userId);
        return Storage.orders
                .stream()
                .filter(o -> o.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Order completeOrder(List<Item> items, Long userId) {
        Order order = new Order(userId);
        List<Item> orderItems = new ArrayList<>(items);
        order.setItems(orderItems);
        return create(order);
    }
}
