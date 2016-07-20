package crud14.view.beans.navi;

import crud14.Logger.Message;
import crud14.entities.Category;
import crud14.service.dao.DomainDao;
import crud14.view.beans.SessionBean;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class AddCategoryBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty("#{navigationBean.current}")
    private Category current;
    @ManagedProperty("#{sessionBean.instance}")
    private SessionBean sessionBean;
    @ManagedProperty("#{dao}")
    private DomainDao daoService;
    @ManagedProperty("#{category}")
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String add() {
        int id = -1;
        category.setParent(current);
        try {
            id = daoService.create(category);
        } catch (Exception e) {
            sessionBean.saveMessage(new Message(FacesMessage.SEVERITY_ERROR, "Can't add category.",e.getMessage() ));
            return null;

        }
        RequestContext.getCurrentInstance().execute("PF('add-category-dialog').hide();");
        try {
            Thread.currentThread();
            Thread.sleep(350);
        } catch (InterruptedException ignored) {
        }
        sessionBean.saveMessage(new Message(FacesMessage.SEVERITY_INFO, "Info ", "Category with id = " + id + " added to database"));
        return "navi";
    }


    public DomainDao getDaoService() {
        return daoService;
    }

    public Category getCurrent() {
        return current;
    }

    public void setCurrent(Category current) {
        this.current = current;
    }

    public void setDaoService(DomainDao daoService) {
        this.daoService = daoService;
    }

    public void reset() {
        RequestContext.getCurrentInstance().reset("form");
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
}
