package crud14.view.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

import static crud14.Constants.ROOT_CATEGORY_ID;

@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private int currentId = ROOT_CATEGORY_ID;

    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

    public SessionBean getInstance() {
        return this;
    }


}
