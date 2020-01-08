package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.model.Item;

public interface ItemService {

    Item create(Item item);

    Optional<Item> get(Long id);

    Item update(Item item);

    boolean deleteById(Long id);

    boolean delete(Item item);

    List<Item> getAllItems();
}
