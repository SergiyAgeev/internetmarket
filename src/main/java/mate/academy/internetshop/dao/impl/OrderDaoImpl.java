package mate.academy.internetshop.dao.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;

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
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }

    @Override
    public Order update(Order order) {
        Order old = Storage.orders
                .stream()
                .filter(o -> o.getId().equals(order.getId()))
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
                .filter(o -> o.getId().equals(id))
                .findFirst().orElseThrow(() ->
                        new NoSuchElementException("there is no order with id " + id));
        return delete(temp);
    }

    @Override
    public boolean delete(Order order) {
        return Storage.orders.remove(order);
    }

}
