package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private Long id;
    private List<Item> items;

    public Bucket() {
    }

    public Bucket(User user) {
        items = new ArrayList<>();
        id = user.getId();
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

    @Override
    public String toString() {
        return "Bucket{" + "id=" + id
                + ", items=" + items + '}';
    }
}
