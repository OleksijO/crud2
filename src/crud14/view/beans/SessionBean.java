package crud14.view.beans;

import crud10.Constants;
import crud14.entities.Category;
import crud14.entities.DomainObject;
import crud14.service.dao.Dao;
import crud14.spring.Context;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped

public class SessionBean {
    private List<Category> path = new ArrayList<>();
    @SuppressWarnings("unchecked cast")
    private Dao<DomainObject, Integer> daoService = (Dao<DomainObject, Integer>) Context.getBean("dao-service");
    private Category current =(Category) daoService
            .retrieve((Category) Context.getBean("entity-category-with-id", Constants.ROOT_CATEGORY_ID));


    @PostConstruct
    public void init() {
        path.add(current);
    }

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
