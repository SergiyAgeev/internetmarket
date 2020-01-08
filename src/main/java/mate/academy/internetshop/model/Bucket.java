package mate.academy.internetshop.model;

import mate.academy.internetshop.lib.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private Long id;
    private Long userId;
    private List<Item> items;

    public Bucket() {
    }

    public Bucket(User user) {
        id = IdGenerator.generateNewBucketId();
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
