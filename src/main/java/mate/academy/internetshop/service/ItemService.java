package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.model.Item;

public interface ItemService {

    Item create(Item item) throws DataProcessingException;

    Item get(Long id) throws DataProcessingException;

    Item update(Item item) throws DataProcessingException;

    boolean deleteById(Long id) throws DataProcessingException;

    boolean delete(Item item) throws DataProcessingException;

    List<Item> getAllItems() throws DataProcessingException;
}
