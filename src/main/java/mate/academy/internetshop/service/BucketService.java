package mate.academy.internetshop.service;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;

public interface BucketService extends GenericService<Bucket, Long> {

    Bucket getByUser(User user) throws DataProcessingException;

    void addItem(Bucket bucket, Item item) throws DataProcessingException;

    void deleteItem(Bucket bucket, Item item) throws DataProcessingException;

    void clear(Bucket bucket) throws DataProcessingException;
}
