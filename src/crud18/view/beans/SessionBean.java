package crud18.view.beans;

import crud18.entities.Category;
import org.apache.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

import static crud18.settings.Constants.ROOT_CATEGORY_ID;

@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(SessionBean.class);
    private int currentId = ROOT_CATEGORY_ID;
    private String loggedUserName ="Guest";
    private int userId=-1;
    private Category naviCashCurrent;

    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

    public String getLoggedUserName() {
        return loggedUserName;
    }

    public boolean isLogged() {
        return userId>-1;
    }

    public void setLoggedUserName(String loggedUserName) {
        this.loggedUserName = loggedUserName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Category getNaviCashCurrent() {
        return naviCashCurrent;
    }

    public void setNaviCashCurrent(Category naviCashCurrent) {
        this.naviCashCurrent = naviCashCurrent;
    }
}
