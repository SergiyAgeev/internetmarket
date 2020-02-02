package mate.academy.internetshop.dao;

import java.util.Optional;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.User;

public interface BucketDao extends GenericDao<Bucket, Long> {

    Optional<Bucket> getByUser(User user) throws DataProcessingException;

}
