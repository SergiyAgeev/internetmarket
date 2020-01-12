package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.lib.IdGenerator;

public class Order {
    private Long id;
    private Long userId;
    private List<Item> items;

    public Order() {
        id = IdGenerator.generateNewOrderId();
    }

    public Order(Long userId) {
        this();
        items = new ArrayList<>();
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id
                + ", userId=" + userId
                + ", items=" + items + '}';
    }
}
