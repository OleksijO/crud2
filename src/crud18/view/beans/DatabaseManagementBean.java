package crud18.view.beans;

import crud18.entities.Category;
import crud18.entities.Product;
import crud18.service.TablesRefiller;
import crud18.service.dao.DomainDao;
import crud18.utils.Context;
import org.apache.log4j.Logger;
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
    private static final Logger logger = Logger.getLogger(DatabaseManagementBean.class);
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
            logger.error("Can't recreate tables. "+e);
            return;
        }
        init();
        logger.info("Database has been cleared.");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "DB successfully cleared.", null));
    }

    public void refill() {
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
            logger.error("Refilling database failed. ", e);
            return;
        }
        init();
        RequestContext.getCurrentInstance().execute("PF('please-wait-dialog').hide();");
        String mes="Database refilled with "+countCategory +" categories and "+
                countProduct + " products in "+(1.0*((System.currentTimeMillis()-timeStamp)/100)/10) + " seconds";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, mes , null));
        logger.info(mes);
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
