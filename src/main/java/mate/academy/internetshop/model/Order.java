package mate.academy.internetshop.model;

import mate.academy.internetshop.lib.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private Long userId;
    private List<Item> items;

    public Order(User user) {
        items = new ArrayList<>();
        userId = user.getId();
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
