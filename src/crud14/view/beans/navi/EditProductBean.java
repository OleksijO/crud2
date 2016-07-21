package crud14.view.beans.navi;

import crud14.entities.Category;
import crud14.entities.Product;
import crud14.service.dao.DomainDao;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class EditProductBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty("#{dao}")
    private DomainDao daoService;
    @ManagedProperty("#{productEntity}")
    private Product product;
    @ManagedProperty("#{navigationBean.current}")
    private Category current;
    private Double price=0.0;
    public void update() {
        product.setParent(current);
        product.setPrice((long)(double) (price*100));
        try {
            daoService.update(product);
        } catch (Exception e) {
            closeDialogAndShowMessageAndResetProduct(FacesMessage.SEVERITY_ERROR, "Can't update product with id = " + product.getId() + ". " + e.getMessage(), null);
            return;
        }
        closeDialogAndShowMessageAndResetProduct(FacesMessage.SEVERITY_INFO, "product with id = " + product.getId() + " updated to database", null);
    }


    public DomainDao getDaoService() {
        return daoService;
    }

    public void setDaoService(DomainDao daoService) {
        this.daoService = daoService;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    private void closeDialogAndShowMessageAndResetProduct(FacesMessage.Severity severity, String summaryMessage, String detailMessage) {
        RequestContext.getCurrentInstance().execute("PF('edit-product-dialog').hide();");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summaryMessage, detailMessage));
    }

    public void showDialog() {
        price=0.01*product.getPrice();
        RequestContext.getCurrentInstance().execute("PF('edit-product-dialog').show();");
    }

    public Category getCurrent() {
        return current;
    }

    public void setCurrent(Category current) {
        this.current = current;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
