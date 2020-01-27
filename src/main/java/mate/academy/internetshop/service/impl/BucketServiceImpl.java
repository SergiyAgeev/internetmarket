package mate.academy.internetshop.service.impl;

import java.util.List;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;
import org.apache.log4j.Logger;

@Service
public class BucketServiceImpl implements BucketService {
    private static Logger LOGGER = Logger.getLogger(BucketServiceImpl.class);
    @Inject
    private static BucketDao bucketDao;

    @Override
    public Bucket create(Bucket bucket) throws DataProcessingException {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long id) throws DataProcessingException {
        return bucketDao.get(id)
                .stream()
                .filter(b -> b.getUserId().equals(id))
                .findFirst()
                .orElse(bucketDao.create(new Bucket(id)));
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
        return bucketDao.getAllBuckets().stream()
                .filter(b -> b.getUserId().equals(userId))
                .findFirst()
                .orElse(create(new Bucket(userId)));
    }

    @Override
    public void addItem(Bucket bucket, Item item) throws DataProcessingException {
        get(bucket.getId());
        bucket.getItems().add(item);
    }

    @Override
    public void clear(Bucket bucket) throws DataProcessingException {
        bucket.getItems().clear();
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) throws DataProcessingException {
        bucket.getItems().remove(item);
        update(bucket);
    }
}
