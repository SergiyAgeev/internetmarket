package mate.academy.internetshop.dao;

import java.util.Optional;

import mate.academy.internetshop.model.Bucket;

public interface BucketDao {

    Bucket create(Bucket bucket);

    Optional<Bucket> get(Long id);

    Bucket update(Bucket bucket);

    boolean deleteById(Long id);

    boolean delete(Bucket bucket);
}
