package mate.academy.internetshop.service.impl;

import java.util.List;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;

@Service
public class BucketServiceImpl implements BucketService {
    @Inject
    private static BucketDao bucketDao;

    @Override
    public Bucket create(Bucket bucket) {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long id) {
        return bucketDao.get(id).get();
    }

    @Override
    public List<Bucket> getAllBuckets() {
        return bucketDao.getAllBuckets();
    }

    @Override
    public Bucket update(Bucket bucket) {
        return bucketDao.update(bucket);
    }

    @Override
    public boolean deleteById(Long id) {
        return bucketDao.deleteById(id);
    }

    @Override
    public boolean delete(Bucket bucket) {
        return bucketDao.delete(bucket);
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) {
        return bucket.getItems();
    }

    @Override
    public Bucket getByUserId(Long userId) {
        return bucketDao.getByUserId(userId);
    }

    @Override
    public void addItem(Bucket bucket, Item item) {
        bucketDao.get(bucket.getId());
        bucket.getItems().add(item);
    }

    @Override
    public void clear(Bucket bucket) {
        bucket.getItems().clear();
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) {
        bucket.getItems().remove(item);
        update(bucket);
    }
}
