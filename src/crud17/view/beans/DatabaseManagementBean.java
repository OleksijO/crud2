package crud17.view.beans;

import crud17.entities.Category;
import crud17.entities.Product;
import crud17.service.TablesRefiller;
import crud17.service.dao.DomainDao;
import crud17.spring.Context;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class DatabaseManagementBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty("#{dao}")
    private DomainDao daoService;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private Integer countCategory;
    private Integer countProduct;
    private Integer depth = 3;
    private Integer itemsPerCategory = 25;

    @PostConstruct
    public void init() {
        countCategory = daoService.getTotalCount(Category.class)-1;
        countProduct = daoService.getTotalCount(Product.class);
    }

    public void clear() {
        try {
            daoService.recreateTables();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN, "OOPS!...Something is broken. " + e.getMessage(), null));
            return;
        }
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "DB successfully cleared.", null));
    }

    public void refill() {
        //RequestContext.getCurrentInstance().execute("PF('refill-database-dialog').hide(); PF('please-wait-dialog').show();");
        long timeStamp=System.currentTimeMillis();
        try {

            TablesRefiller refiller = Context.getBean("refiller");
            refiller.setDaoService(daoService);
            refiller.refillDatabase(depth, itemsPerCategory);

        } catch (Exception e) {
            e.printStackTrace();
            RequestContext.getCurrentInstance().execute("PF('please-wait-dialog').hide();");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN, "OOPS!...Something is broken. " + e.getMessage(), null));
            return;
        }
        init();
        RequestContext.getCurrentInstance().execute("PF('please-wait-dialog').hide();");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Database refilled with "+countCategory +" categories and "+
                countProduct + " products in "+(1.0*((System.currentTimeMillis()-timeStamp)/100)/10) + " seconds" , null));
    }

    public DomainDao getDaoService() {
        return daoService;
    }

    public void setDaoService(DomainDao daoService) {
        this.daoService = daoService;
    }

    public Integer getCountCategory() {
        return countCategory;
    }

    public Integer getCountProduct() {
        return countProduct;
    }

    public Integer getDepth() {
        return depth;
    }

    public Integer getItemsPerCategory() {
        return itemsPerCategory;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public void setItemsPerCategory(Integer itemsPerCategory) {
        this.itemsPerCategory = itemsPerCategory;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
}
