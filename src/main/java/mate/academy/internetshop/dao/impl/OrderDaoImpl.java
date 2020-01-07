package mate.academy.internetshop.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        Storage.orders.add(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Storage.orders
                .stream()
                .filter(o -> o.getId() == id)
                .findFirst();
    }

    @Override
    public Order update(Order order) {
        Order old = Storage.orders
                .stream()
                .filter(o -> o.getId() == order.getId())
                .findFirst().orElseThrow(() ->
                        new NoSuchElementException("there is no order with id "
                                + order.getId()));
        int index = Storage.orders.indexOf(old);
        return Storage.orders.set(index, order);
    }

    @Override
    public boolean deleteById(Long id) {
        Order temp = Storage.orders
                .stream()
                .filter(o -> o.getId() == id)
                .findFirst().orElseThrow(() ->
                        new NoSuchElementException("there is no order with id " + id));
        return delete(temp);
    }

    @Override
    public boolean delete(Order order) {
        return Storage.orders.remove(order);
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return Storage.orders
                .stream()
                .filter(o -> o.getId() == user.getId())
                .collect(Collectors.toList());
    }

    @Override
    public Order completeOrder(List<Item> items, User user) {
        Order order = new Order(user);
        List<Item> orderItems = new ArrayList<>(items);
        order.setItems(orderItems);
        return create(order);
    }
}
