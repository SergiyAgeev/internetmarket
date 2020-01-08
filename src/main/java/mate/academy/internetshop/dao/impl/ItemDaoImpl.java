package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.IdGenerator;
import mate.academy.internetshop.model.Item;

@Dao
public class ItemDaoImpl implements ItemDao {

    @Override
    public Item create(Item item) {
        item.setId(IdGenerator.generateNewItemId());
        Storage.items.add(item);
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        return Storage.items
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    @Override
    public Item update(Item item) {
        Item temp = Storage.items
                .stream()
                .filter(i -> i.getId().equals(item.getId()))
                .findFirst().orElseThrow(() ->
                        new NoSuchElementException("there is no item with id "
                                + item.getId()));
        int index = Storage.items.indexOf(temp);
        return Storage.items.set(index, item);
    }

    @Override
    public boolean deleteById(Long id) {
        Item temp = Storage.items
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst().orElseThrow(() ->
                        new NoSuchElementException("there is no item with id " + id));
        return delete(temp);
    }

    @Override
    public boolean delete(Item item) {
        return Storage.items.remove(item);
    }

    @Override
    public List<Item> getAllItems() {
        return Storage.items;
    }
}
