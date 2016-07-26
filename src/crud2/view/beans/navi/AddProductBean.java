package crud2.view.beans.navi;

import crud2.settings.Constants;
import crud2.entities.Category;
import crud2.entities.Product;
import crud2.service.dao.DomainDao;
import crud2.utils.Context;
import org.apache.log4j.Logger;
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
public class AddProductBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AddProductBean.class);
    @ManagedProperty("#{navigationBean.current}")
    private Category current;
    @ManagedProperty("#{dao}")
    private DomainDao daoService;
    @ManagedProperty("#{productEntity}")
    private Product product;
    private Double price=0.0;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void add() {
        int id = -1;
        product.setParent(current);
        product.setPrice((long)(double) (price*100));
        try {
            id = daoService.create(product);
        } catch (Exception e) {
            logger.error("Error adding "+ product,e);
            closeDialogAndShowMessageAndResetProduct(FacesMessage.SEVERITY_ERROR, "Can't add product. " + e.getMessage(), null);
            return;
        }
        logger.info("Added: "+ product);
        closeDialogAndShowMessageAndResetProduct(FacesMessage.SEVERITY_INFO, "Product with id = " + id + " added to database", null);
    }

    private void closeDialogAndShowMessageAndResetProduct(FacesMessage.Severity severity, String summaryMessage, String detailMessage) {
        RequestContext.getCurrentInstance().execute("PF('add-product-dialog').hide();");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summaryMessage, detailMessage));
        if (current.getProducts().size() == 0) {
            try {
                Context.getExternalContext().redirect(Constants.SITE_CHAPTER_ADDRESS + "/navi.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        product = Context.getBean("productEntity");
        price=0.0;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
