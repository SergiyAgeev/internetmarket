package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import org.apache.log4j.Logger;

@Service
public class BucketServiceImpl implements BucketService {

    @Inject
    private static BucketDao bucketDao;

    @Override
    public Bucket create(Bucket bucket) throws DataProcessingException {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long id) throws DataProcessingException {
        return bucketDao.get(id)
                .orElseThrow(() -> new NoSuchElementException("Bucket doesn't exist"));
    }

    @Override
    public List<Bucket> getAllBuckets() throws DataProcessingException {
        return bucketDao.getAllBuckets();
    }

    @Override
    public Bucket update(Bucket bucket) throws DataProcessingException {
        return bucketDao.update(bucket);
    }

    @Override
    public boolean deleteById(Long id) throws DataProcessingException {
        return bucketDao.deleteById(id);
    }

    @Override
    public boolean delete(Bucket bucket) throws DataProcessingException {
        return bucketDao.delete(bucket);
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) throws DataProcessingException {
        return bucket.getItems();
    }

    @Override
    public Bucket getByUserId(Long userId) throws DataProcessingException {
        return bucketDao.get(userId)
                .orElseThrow(() -> new NoSuchElementException("Bucket doesn't exist"));
    }

    @Override
    public Bucket getByUser(User user) throws DataProcessingException {
        Optional<Bucket> bucket = getAllBuckets()
                .stream()
                .filter(b -> b.getUserId().equals(user.getId()))
                .findFirst();
        if (bucket.isPresent()) {
            return bucket.get();
        }
        return create(new Bucket(user));
    }


    @Override
    public void addItem(Bucket bucket, Item item) throws DataProcessingException {
        bucket.addItemToBucket(item);
        update(bucket);
    }

    @Override
    public void clear(Bucket bucket) throws DataProcessingException {
        bucket.getItems().clear();
        update(bucket);
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) throws DataProcessingException {
        bucket.getItems().remove(item);
        update(bucket);
    }
}
