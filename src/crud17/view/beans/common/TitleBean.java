package crud17.view.beans.common;

import crud17.settings.Constants;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class TitleBean implements Serializable{
    private String title= Constants.TITLE;

    private String additionalTitle=Constants.PROGRAM_NAME;
    private String pageTitle=title+additionalTitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public void setAdditionalTitle(String additionalTitle) {
        this.additionalTitle = additionalTitle;
    }

    public String getAdditionalTitle() {
        return additionalTitle;
    }
}
