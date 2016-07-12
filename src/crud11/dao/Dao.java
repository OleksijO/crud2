package crud11.dao;

import crud11.entity.Category;

import java.io.Serializable;
import java.util.List;

public interface Dao<T, PrKey extends Serializable> {
    /**
     * Save newInstance in database (DB)
     */
    PrKey create(T newInstance);

    /**
     * Extract object from DB by id
     */
    T read(PrKey id);

    /**
     * Update object in DB
     */
    void update(T transientObject);

    /**
     * Delete object from DB
     */
    void delete(PrKey id);

}

