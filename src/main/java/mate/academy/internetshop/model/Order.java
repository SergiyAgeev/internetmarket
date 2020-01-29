package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private Long id;
    private Long userId;
    private List<Item> items;

    public Order(Long userId) {
        items = new ArrayList<>();
        this.userId = userId;
    }

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return id.equals(order.id)
                && userId.equals(order.userId)
                && Objects.equals(items, order.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, items);
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id
                + ", userId=" + userId
                + ", items=" + items + '}';
    }
}
