package crud14.view.beans;

import crud14.Logger.Logger;
import crud14.Logger.Message;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

import static crud14.Constants.ROOT_CATEGORY_ID;

@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private int currentId = ROOT_CATEGORY_ID;
    private Message message = null;


    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
        System.out.println("sessionBean.currentId=" + currentId);
    }


    public String goToCategoryWithId(int id) {
        currentId = id;
        return "navi";
    }

    public SessionBean getInstance() {
        return this;
    }

    public Message getMessage() {
        Message outMessage = message;
        message = null;
        return outMessage;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public boolean hasMessage() {
        return message.getMessage().length() > 0;
    }

    public void saveMessage(Message message) {
        Logger.log(message);
        setMessage(message);
    }

}
