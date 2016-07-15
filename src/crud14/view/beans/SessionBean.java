package crud14.view.beans;

import crud14.Constants;
import crud14.entities.Category;
import crud14.entities.DomainObject;
import crud14.service.dao.Dao;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped

public class SessionBean {

    //@SuppressWarnings("unchecked cast")
    @ManagedProperty("#{dao}")
    private Dao<DomainObject, Integer> daoService;
    @ManagedProperty("#{category}")
    private Category current;// = (Category) daoService
    // .retrieve((Category) Context.getBean("entity-category-with-id", Constants.ROOT_CATEGORY_ID));

    public Category getCurrent() {
        return current;
    }

    public void setCurrent(Category current) {
        this.current = current;
    }

    @PostConstruct
    public void init() {
        //daoService =  Context.getBean("dao-service");
        //current = Context.getBean("entity-category");
        current.setId(Constants.ROOT_CATEGORY_ID);
        current = daoService.retrieve(current);

    }

    public Dao<DomainObject, Integer> getDaoService() {
        return daoService;
    }

    public void setDaoService(Dao<DomainObject, Integer> daoService) {
        this.daoService = daoService;
    }


}
