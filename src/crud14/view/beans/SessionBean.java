package crud14.view.beans;

import crud14.Constants;
import crud14.entities.Category;
import crud14.entities.DomainObject;
import crud14.service.dao.Dao;
import crud14.spring.Context;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped

public class SessionBean {

    @SuppressWarnings("unchecked cast")
    private Dao<DomainObject, Integer> daoService = (Dao<DomainObject, Integer>) Context.getBean("dao-service");
    private Category current =(Category) daoService
            .retrieve((Category) Context.getBean("entity-category-with-id", Constants.ROOT_CATEGORY_ID));

public Integer getId() {
        return current.getId();
    }

    public Category getCurrent() {
        return current;
    }

    public void setCurrent(Category current) {
        this.current = current;
    }
}
