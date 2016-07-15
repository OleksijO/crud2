package crud14.service.dao;

import crud14.entities.DomainObject;
import org.springframework.transaction.annotation.Transactional;

public interface DomainDao extends Dao<DomainObject, Integer> {

}
