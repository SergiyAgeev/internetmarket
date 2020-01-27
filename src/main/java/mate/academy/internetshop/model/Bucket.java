package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.lib.IdGenerator;

public class Bucket {
    private Long id;
    private Long userId;
    private List<Item> items;

    public Bucket() {
    }

    public Bucket(Long userId) {
        items = new ArrayList<>();
        this.userId = userId;
    }

    public Bucket(User user) {
        items = new ArrayList<>();
        userId = user.getId();
    }

    public Bucket(List<Item> items, Long userId) {
        this.userId = userId;
        this.items = items;
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

    public Long getUserId() {
        return userId;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Bucket setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void addItemToBucket(Item item) {
        items.add(item);
    }

    public Bucket addItemsToBucket(List<Item> items) {
        this.items.removeAll(items);
        this.items.addAll(items);
        return this;
    }

    public void deleteItem(Item item) {
        items.remove(item);
    }

    public void clearBucket() {
        items.clear();
    }

    @Override
    public String toString() {
        return "Bucket{" + "id=" + id
                + ", userId=" + userId
                + ", items=" + items + '}';
    }
}
