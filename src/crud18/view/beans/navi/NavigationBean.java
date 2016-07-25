package crud18.view.beans.navi;

import crud18.entities.Category;
import crud18.service.dao.DomainDao;
import crud18.settings.Constants;
import crud18.view.beans.SessionBean;
import org.apache.log4j.Logger;

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
    private static final Logger logger = Logger.getLogger(NavigationBean.class);
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
       renew();
    }

    public DomainDao getDaoService() {
        return daoService;
    }

    public void setDaoService(DomainDao daoService) {
        this.daoService = daoService;
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

    public NavigationBean getInstance() {
        return this;
    }

    public void renew() {
        current.setId(sessionBean.getCurrentId());
        try {
            setCurrent(daoService.retrieve(current));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN, "OOPS!...Something is broken. " + e.getMessage() +
                    "Redirecting to category '" + Constants.ROOT_CATEGORY_NAME + "'", null));
            current.setId(Constants.ROOT_CATEGORY_ID);
            logger.error("Redirected to root, cause of error while retrieving "+current+". ", e);
            try {
                setCurrent(daoService.retrieve(current));
            } catch (Exception e1) {
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "OOPS!...Something is broken. " + e1.getMessage() +
                        "Category '" + Constants.ROOT_CATEGORY_NAME + "' not found. It's very bad....", null));
                logger.error("Retrieving "+Constants.ROOT_CATEGORY_NAME, e1);
            }
        }
        hasSubcategories = current.getSubCategories().size() > 0;
        hasProducts = current.getProducts().size() > 0;
        pathList = current.getPathList();
        notRoot = current.getId() != 0;
        noSubItems = !hasProducts && !hasSubcategories;
        logger.info("Retrieved info of category with id="+ current.getId());

    }


}
