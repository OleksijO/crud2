package crud14.service.dao;

import java.io.Serializable;
import java.util.Collection;

public interface Dao<T, PrKey extends Serializable> {
    /**
     * Save new entity object in database (DB)
     */
    PrKey create(T transientObject);

    /**
     * Extract object from DB by id
     */
    <K> K retrieve(K transientObject);

    /**
     * Update object in DB
     */
    void update(T transientObject);

    /**
     * Delete object from DB
     */
    void delete(T transientObject);

    /**
     * Get total count of selected class entities
     */
    int getTotalCount(Class entityClass);

    /**
     * Add list of entities to table
     */
    void createList(Collection list);

    /**
     * Delete and create tables
     */
    void recreateTables();
}

