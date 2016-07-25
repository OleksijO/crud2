package crud19.view.beans.navi;

import crud19.entities.DomainObject;
import crud19.service.dao.Dao;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class DeleteBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AddProductBean.class);
    @ManagedProperty("#{dao}")
    private Dao<DomainObject, Integer> daoService;

    private DomainObject itemToDelete=null;


    public void deleteItem(){
        logger.info("Deleting "+itemToDelete);
        try {
            daoService.delete(itemToDelete);
        } catch (Exception e) {
            e.printStackTrace();
            showMessage(FacesMessage.SEVERITY_ERROR, "Can't delete "+itemToDelete.getClass().getSimpleName().toLowerCase()+
                    " with id = "+itemToDelete.getId()+". " + e.getMessage(), null);
            return;
        }
        showMessage(FacesMessage.SEVERITY_INFO, itemToDelete.getClass().getSimpleName()+" with id = " + itemToDelete.getId() + " deleted from database.", null);
    }

    public Dao<DomainObject, Integer> getDaoService() {
        return daoService;
    }

    public void setDaoService(Dao<DomainObject, Integer> daoService) {
        this.daoService = daoService;
    }

    private void showMessage(FacesMessage.Severity severity, String summaryMessage, String detailMessage) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summaryMessage, detailMessage));
    }

    public DomainObject getItemToDelete() {
        return itemToDelete;
    }

    public void setItemToDelete(DomainObject itemToDelete) {
        this.itemToDelete = itemToDelete;
    }
}
