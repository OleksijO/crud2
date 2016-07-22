package crud17.view.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

import static crud17.settings.Constants.ROOT_CATEGORY_ID;

@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private int currentId = ROOT_CATEGORY_ID;
    private String loggedUserName ="Guest";
    private boolean logged=false;

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
        return logged;
    }

    public void setLoggedUserName(String loggedUserName) {
        this.loggedUserName = loggedUserName;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}
