package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mate.academy.internetshop.lib.IdGenerator;

public class Bucket {
    private Long id;
    private Long userId;
    private List<Item> items;

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
        if (items.contains(item)) {
            return;
        }
        items.add(item);
    }

    public void addItemsToBucket(List<Item> items) {
        this.items.removeAll(items);
        this.items.addAll(items);
    }
    public void clear() {
        items.clear();
    }

    public void deleteItem(Item item) {
        items.remove(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bucket bucket = (Bucket) o;
        return id.equals(bucket.id)
                && userId.equals(bucket.userId)
                && items.equals(bucket.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, items);
    }

    @Override
    public String toString() {
        return "Bucket{" + "id=" + id
                + ", userId=" + userId
                + ", items=" + items + '}';
    }
}
