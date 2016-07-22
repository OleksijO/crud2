package crud17.spring;

import crud17.Logger.Logger;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class Context {
    private static WebApplicationContext context;

    private Context() {
    }

    static {
        context = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        Logger.log("SPRING:\tcontext (re)loaded.");
    }

    public static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static <T> T getBean(String beanId) {
        T obj = null;
        try {
            obj = (T) context.getBean(beanId);
        } catch (BeansException e) {
            Logger.log("SPRING:\tERROR\t Can't load bean with id = '" + beanId + "'");
        }
        return obj;
    }
/*
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        FacesContext context = FacesContext.getCurrentInstance();
        T obj=(T)context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
        Logger.log("SPRING:\tloaded bean '"+beanName+"' ["+obj.toString()+"]\tOK.");
        return obj;
    }
    */
/*
    public static Object getBean(String beanId, Object... args){
        return context.getBean(beanId, args);
    }
    */
}
