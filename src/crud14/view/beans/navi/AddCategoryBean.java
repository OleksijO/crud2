package crud14.view.beans.navi;

import crud14.Constants;
import crud14.entities.Category;
import crud14.service.dao.DomainDao;
import crud14.spring.Context;
import crud14.view.beans.SessionBean;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
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

    public void add() {
        int id = -1;
        category.setParent(current);
        try {
            id = daoService.create(category);
        } catch (Exception e) {
            closeDialogAndShowMessageAndResetCategory(FacesMessage.SEVERITY_ERROR, "Can't add category. " + e.getMessage(), null);
            return;
        }
        closeDialogAndShowMessageAndResetCategory(FacesMessage.SEVERITY_INFO, "Category with id = " + id + " added to database", null);
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

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    private void closeDialogAndShowMessageAndResetCategory(FacesMessage.Severity severity, String summaryMessage, String detailMessage) {
        RequestContext.getCurrentInstance().execute("PF('add-category-dialog').hide();");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summaryMessage, detailMessage));
        if (current.getSubCategories().size() == 0) {
            try {
                Context.getExternalContext().redirect(Constants.SITE_CHAPTER_ADDRESS+"/navi.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        category = Context.getBean("category");
    }


}
