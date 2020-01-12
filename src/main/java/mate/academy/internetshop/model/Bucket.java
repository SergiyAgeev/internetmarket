package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.lib.IdGenerator;

public class Bucket {
    private Long id;
    private Long userId;
    private List<Item> items;

    public Bucket() {
        id = IdGenerator.generateNewBucketId();
    }

    public Bucket(Long userId) {
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

    public Long getUserId() {
        return userId;
    }

    public Bucket setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Bucket setItems(List<Item> items) {
        this.items = items;
        return this;
    }

    @Override
    public String toString() {
        return "Bucket{" + "id=" + id
                + ", userId=" + userId
                + ", items=" + items + '}';
    }
}
