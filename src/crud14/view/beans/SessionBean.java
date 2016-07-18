package crud14.view.beans;

import crud14.Constants;
import crud14.entities.Category;
import crud14.entities.DomainObject;
import crud14.service.dao.Dao;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {
    private static final long serialVersionUID = 1L;
    //@SuppressWarnings("unchecked cast")
    @ManagedProperty("#{dao}")
    private Dao<DomainObject, Integer> daoService;
    @ManagedProperty("#{category}")
    private Category current;// = (Category) daoService
    // .retrieve((Category) Context.getBean("entity-category-with-id", Constants.ROOT_CATEGORY_ID));

    private boolean hasSubcategories;

    private boolean hasProducts;

    public Category getCurrent() {
        return current;
    }

    public void setCurrent(Category current) {
        this.current = current;
    }

    public List<Category> pathList;

    public boolean notRoot;
    public boolean noSubItems;

    @PostConstruct
    public void init() {
        //daoService =  Context.getBean("dao-service");
        //current = Context.getBean("entity-category");
        current.setId(Constants.ROOT_CATEGORY_ID);
        doAction(current);
    }

    public Dao<DomainObject, Integer> getDaoService() {
        return daoService;
    }

    public void setDaoService(Dao<DomainObject, Integer> daoService) {
        this.daoService = daoService;
    }

    public String doAction(Category category) {
        System.out.println(current.getName() + " --> " + category.getName());
        setCurrent(daoService.retrieve(category));
        renew();
        return "navi";
    }
    public String doAction(String dest) {
        System.out.println(dest);
        return dest;
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

    private void renew() {
        hasSubcategories = (current.getSubCategories().size() > 0);
        hasProducts = current.getProducts().size() > 0;
        pathList = current.getPathList();
        notRoot=current.getId()!=Constants.ROOT_CATEGORY_ID;
        noSubItems=(!hasProducts)&&(!hasSubcategories);

    }

    public boolean isNoSubItems() {
        return noSubItems;
    }

    public boolean isNotRoot() {
        return notRoot;
    }
}
