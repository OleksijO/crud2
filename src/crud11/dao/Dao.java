package crud11.dao;

import java.io.Serializable;
import java.util.Collection;

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

    int getTotalCount(Class entityClass);

    void createList(Collection list);

    void recreateTables();
}

