package crud14.view.beans.navi;

import crud14.Logger.Message;
import crud14.entities.Category;
import crud14.service.dao.DomainDao;
import crud14.view.beans.SessionBean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@RequestScoped
public class NavigationBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty("#{dao}")
    private DomainDao daoService;
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
        current.setId(sessionBean.getCurrentId());
        setCurrent(daoService.retrieve(current));
        hasSubcategories = current.getSubCategories().size() > 0;
        hasProducts = current.getProducts().size() > 0;
        pathList = current.getPathList();
        notRoot = current.getId() != 0;
        noSubItems = !hasProducts && !hasSubcategories;


    }

    public DomainDao getDaoService() {
        return daoService;
    }

    public void setDaoService(DomainDao daoService) {
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
    public void mes(){
        Message mes = sessionBean.getMessage();
        System.out.println(mes);
        System.out.println(sessionBean.getMessage());
        mes=new Message(FacesMessage.SEVERITY_ERROR, "TEST","test message");
        System.out.println(mes);
        if (mes != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mes.getSeverity(), mes.getSummary(), mes.getMessage()));
        }
    }

}
