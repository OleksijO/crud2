package crud17.view.beans.common;

import crud17.utils.InfoHolder;
import crud17.view.beans.SessionBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class TechInfoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    private Integer memory;  // kb
    private Integer operationTime;
    private Integer numberOfActiveSessions;

    @PostConstruct
    public void init(){
        InfoHolder.addSession(sessionBean);
        memory=(int)(Runtime.getRuntime().freeMemory()/1024);
        numberOfActiveSessions= InfoHolder.getNumberOfSessions();
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public Integer getMemory() {
        return memory;
    }

    public Integer getOperationTime() {
        return operationTime;
    }

    public Integer getNumberOfActiveSessions() {
        return numberOfActiveSessions;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public void setOperationTime(Integer operationTime) {
        this.operationTime = operationTime;
    }

    public void setNumberOfActiveSessions(Integer numberOfActiveSessions) {
        this.numberOfActiveSessions = numberOfActiveSessions;
    }
}
