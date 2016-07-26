package crud2.view.beans.navi;

import crud2.entities.Category;
import crud2.service.dao.DomainDao;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class EditCategoryBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(EditCategoryBean.class);
    @ManagedProperty("#{dao}")
    private DomainDao daoService;
    @ManagedProperty("#{category}")
    private Category category;
    @ManagedProperty("#{navigationBean.current}")
    private Category current;



    public void update() {

        category.setParent(current);
        try {
            daoService.update(category);
        } catch (Exception e) {
            logger.error("Updating: "+category);
            closeDialogAndShowMessageAndResetCategory(FacesMessage.SEVERITY_ERROR, "Can't update category with id = " + category.getId() + ". " + e.getMessage(), null);
            return;
        }
        logger.info("Added: "+ category);
        closeDialogAndShowMessageAndResetCategory(FacesMessage.SEVERITY_INFO, "Category with id = " + category.getId() + " updated to database", null);
    }


    public DomainDao getDaoService() {
        return daoService;
    }

    public void setDaoService(DomainDao daoService) {
        this.daoService = daoService;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    private void closeDialogAndShowMessageAndResetCategory(FacesMessage.Severity severity, String summaryMessage, String detailMessage) {
        RequestContext.getCurrentInstance().execute("PF('edit-category-dialog').hide();");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summaryMessage, detailMessage));
    }

    public void showDialog() {
        RequestContext.getCurrentInstance().execute("PF('edit-category-dialog').show();");
    }

    public Category getCurrent() {
        return current;
    }

    public void setCurrent(Category current) {
        this.current = current;
    }
}
