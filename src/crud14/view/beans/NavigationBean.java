package crud14.view.beans;

import crud14.entities.Category;
import crud14.entities.DomainObject;
import crud14.service.dao.Dao;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@RequestScoped
public class NavigationBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty("#{dao}")
    private Dao<DomainObject, Integer> daoService;
    @ManagedProperty("#{category}")
    private Category current;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private boolean hasSubcategories;
    private boolean hasProducts;
    public List<Category> pathList;
    public boolean notRoot;
    public boolean noSubItems;


    public Category getCurrent() {
        return current;
    }

    public void setCurrent(Category current) {
        this.current = current;
    }

    @PostConstruct
    public void init() {
        System.out.println("naviBean.currentId=" + sessionBean.getCurrentId());
        current.setId(sessionBean.getCurrentId());
        setCurrent(daoService.retrieve(current));
        hasSubcategories = current.getSubCategories().size() > 0;
        hasProducts = current.getProducts().size() > 0;
        pathList = current.getPathList();
        notRoot = current.getId() != 0;
        noSubItems = !hasProducts && !hasSubcategories;
    }

    public Dao<DomainObject, Integer> getDaoService() {
        return daoService;
    }

    public void setDaoService(Dao<DomainObject, Integer> daoService) {
        this.daoService = daoService;
    }

    public String doAction(Category category) {

        return null;
    }

    public boolean isHasProducts() {
        return hasProducts;
    }

    public boolean isHasSubcategories() {
        return hasSubcategories;
    }

    public List<Category> getPathList() {
        return pathList;
    }

    public boolean isNoSubItems() {
        return noSubItems;
    }

    public boolean isNotRoot() {
        return notRoot;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public String renew() {
        init();
        return null;
    }

}
